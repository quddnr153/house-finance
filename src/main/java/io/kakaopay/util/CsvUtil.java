package io.kakaopay.util;

import io.kakaopay.house.finance.model.Bank;
import io.kakaopay.house.finance.model.CreditGuarantee;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Byungwook lee on 2019-08-09
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@Slf4j
@Component
public class CsvUtil {
	private static final String COMMA_SEPARATOR = ",";
	private static final int NUMBER_OF_DATE_COLUMN = 2;
	private static final String UNNECESSARY_TAIL = "(억원)";

	public List<Bank> parseBanks(final Path target) {
		try (final Stream<String> lines = Files.lines(target)) {
			final String firstLine = new LinkedList<>(lines.collect(Collectors.toList())).getFirst();

			final String[] headers = StringUtils.split(StringUtils.deleteWhitespace(firstLine), COMMA_SEPARATOR);

			final AtomicInteger csvIndex = new AtomicInteger();
			return Arrays.stream(headers).skip(NUMBER_OF_DATE_COLUMN)
				.map(header -> {
					final String bankName = StringUtils.removeEnd(header.strip(), UNNECESSARY_TAIL);

					return Bank.builder()
						.name(bankName)
						.csvIndex(csvIndex.getAndIncrement())
						.build();
				}).collect(Collectors.toList());
		} catch (final Exception exception) {
			log.error("csv 파일의 header parse 중 에러, path = {}", target, exception);
			throw new IllegalStateException("csv 파일의 header parse 중 에러, path = " + target.toString(), exception);
		}
	}

	public List<CreditGuarantee> parseCreditGuarantees(final Path target, final List<Bank> banks) {
		try (final Reader in = new FileReader(target.toString())) {
			final Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

			return StreamSupport.stream(records.spliterator(), false)
				.skip(1)
				.flatMap(record -> {
					final Integer year = Integer.valueOf(record.get(0));
					final Integer month = Integer.valueOf(record.get(1));

					return IntStream.range(0, banks.size())
						.mapToObj(index -> {
							final String amountWithoutComma = StringUtils.remove(record.get(index + NUMBER_OF_DATE_COLUMN), COMMA_SEPARATOR);
							final Integer amount = Integer.valueOf(amountWithoutComma);

							return CreditGuarantee.builder()
								.bank(banks.get(index))
								.year(year)
								.month(month)
								.amount(amount)
								.build();
						});
				})
				.collect(Collectors.toList());
		} catch (Exception exception) {
			log.error("csv 파일 parse 중 에러, path = {}", target, exception);
			throw new IllegalStateException("csv 파일 parse 중 에러, path = " + target.toString(), exception);
		}
	}
}
