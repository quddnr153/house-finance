package io.kakaopay.house.finance.service;

import edu.princeton.cs.algorithms.LinearRegression;
import io.kakaopay.house.finance.model.Bank;
import io.kakaopay.house.finance.model.CreditGuarantee;
import io.kakaopay.house.finance.model.dto.BankDto;
import io.kakaopay.house.finance.model.dto.CurrentStateOfSupply;
import io.kakaopay.house.finance.model.dto.MinMaxOfAverageDto;
import io.kakaopay.house.finance.model.dto.MostSupportBankAboutYearDto;
import io.kakaopay.house.finance.model.dto.PredictionDto;
import io.kakaopay.house.finance.model.dto.SupportAmount;
import io.kakaopay.house.finance.model.dto.YearAmount;
import io.kakaopay.house.finance.repository.BankRepository;
import io.kakaopay.house.finance.repository.CreditGuaranteeRepository;
import io.kakaopay.util.CsvUtil;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Byungwook lee on 2019-08-10
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@RequiredArgsConstructor
@Service
public class HouseFinanceService {
	private static String HOME = System.getProperty("user.home");

	private final CsvUtil csvUtil;
	private final BankRepository banks;
	private final CreditGuaranteeRepository creditGuarantees;

	public void parseCsvAndCreateModels(final MultipartFile csvFile) throws IOException {
		final Path tempPath = Path.of(HOME, csvFile.getOriginalFilename());
		csvFile.transferTo(tempPath);

		final List<Bank> parsedBanks = csvUtil.parseBanks(tempPath);

		final List<Bank> savedBanks = this.banks.saveAll(parsedBanks)
			.stream()
			.sorted(Comparator.comparingInt(Bank::getCsvIndex))
			.collect(Collectors.toList());

		final List<CreditGuarantee> parsedCreditGuarantees = csvUtil.parseCreditGuarantees(tempPath, savedBanks);

		creditGuarantees.saveAll(parsedCreditGuarantees);

		Files.delete(tempPath);
	}

	public List<BankDto> fetchBanks() {
		return banks.findAll()
			.stream()
			.map(BankDto::new)
			.collect(Collectors.toList());
	}

	List<CreditGuarantee> fetchCreditGuarantee(final Integer from, final Integer to) {
		if (Objects.isNull(from) && Objects.isNull(to)) {
			return creditGuarantees.findAll();
		}

		if (!Objects.isNull(from) && Objects.isNull(to)) {
			return creditGuarantees.findAllByYearGreaterThanEqual(from);
		}

		if (Objects.isNull(from)) {
			return creditGuarantees.findAllByYearLessThanEqual(to);
		}

		return creditGuarantees.findAllByYearGreaterThanEqualAndYearLessThanEqual(from, to);
	}

	public List<CurrentStateOfSupply> fetchCurrentStatesOfSupplyAboutHouseFinance(
		final Integer from,
		final Integer to) {
		final Map<Integer, List<CreditGuarantee>> yearGroup = fetchCreditGuarantee(from, to)
			.stream()
			.collect(Collectors.groupingBy(CreditGuarantee::getYear));

		return yearGroup.keySet()
			.stream()
			.map(key -> {
				final List<CreditGuarantee> creditGuarantees = yearGroup.get(key);

				final int totalAmount = creditGuarantees.stream().mapToInt(CreditGuarantee::getAmount).sum();

				final Map<String, Integer> bankGroup = creditGuarantees.stream()
					.collect(
						Collectors.groupingBy(creditGuarantee -> creditGuarantee.getBank().getName(),
							Collectors.summingInt(CreditGuarantee::getAmount))
					);

				return CurrentStateOfSupply.builder()
					.year(key)
					.totalAmount(totalAmount)
					.detailAmount(bankGroup)
					.build();
			})
			.collect(Collectors.toList());
	}

	private CurrentStateOfSupply fetchCurrentStateOfSupplyAboutHouseFinance(final Integer year) {
		return new LinkedList<>(fetchCurrentStatesOfSupplyAboutHouseFinance(year, year))
			.getFirst();
	}

	public MostSupportBankAboutYearDto fetchMostSupportBankAbout(final Integer year) {
		return fetchCurrentStateOfSupplyAboutHouseFinance(year)
			.getDetailAmount()
			.entrySet()
			.stream()
			.map(entry -> MostSupportBankAboutYearDto.builder()
				.year(year)
				.bankName(entry.getKey())
				.totalAmount(entry.getValue())
				.build()
			)
			.max(Comparator.comparingInt(MostSupportBankAboutYearDto::getTotalAmount))
			.orElseThrow();
	}

	public MinMaxOfAverageDto fetchMinAndMaxOfAverage(final Long bankSeq, final Integer from, final Integer to) {
		final Bank bank = banks.getOne(bankSeq);

		final Integer currentFrom = Objects.isNull(from) ? 2005 : from;
		final Integer currentTo = Objects.isNull(to) ? 2016 : to;

		final List<CreditGuarantee> creditGuarantees = bank.getCreditGuarantees()
			.parallelStream()
			.filter(
				creditGuarantee ->
					creditGuarantee.getYear() >= currentFrom && creditGuarantee.getYear() <= currentTo
			)
			.collect(Collectors.toList());

		final Map.Entry<Integer, Double> maxEntry = creditGuarantees
			.stream()
			.collect(
				Collectors.groupingBy(
					CreditGuarantee::getYear,
					Collectors.averagingInt(CreditGuarantee::getAmount)
				)
			)
			.entrySet()
			.stream()
			.max(Comparator.comparingDouble(Map.Entry::getValue))
			.orElseThrow();

		final Map.Entry<Integer, Double> minEntry = creditGuarantees
			.stream()
			.collect(
				Collectors.groupingBy(
					CreditGuarantee::getYear,
					Collectors.averagingInt(CreditGuarantee::getAmount)
				)
			)
			.entrySet()
			.stream()
			.min(Comparator.comparingDouble(Map.Entry::getValue))
			.orElseThrow();

		return MinMaxOfAverageDto.builder()
			.bankName(bank.getName())
			.supportAmount(
				SupportAmount.builder()
					.max(
						YearAmount.builder()
							.year(maxEntry.getKey())
							.amount(Math.toIntExact(Math.round(maxEntry.getValue())))
							.build()
					)
					.min(
						YearAmount.builder()
							.year(minEntry.getKey())
							.amount(Math.toIntExact(Math.round(minEntry.getValue())))
							.build()
					)
					.build()
			)
			.build();
	}

	public PredictionDto predictAmount(
		final Long bankSeq,
		final Integer trainingFrom,
		final Integer trainingTo,
		final Integer predictionYear,
		final Integer predictionMonth) {
		final Bank bank = banks.getOne(bankSeq);
		final List<CreditGuarantee> trainData = bank.getCreditGuarantees()
			.stream()
			.filter(creditGuarantee ->
				creditGuarantee.getYear() >= trainingFrom
					&& creditGuarantee.getYear() <= trainingTo
					&& creditGuarantee.getMonth().equals(predictionMonth)
			)
			.collect(Collectors.toList());

		final double[] x = trainData.stream()
			.mapToDouble(CreditGuarantee::getYear)
			.toArray();
		final double[] y = trainData.stream()
			.mapToDouble(CreditGuarantee::getAmount)
			.toArray();

		final double prediction = new LinearRegression(x, y).predict(predictionYear);

		return PredictionDto.builder()
			.bankName(bank.getName())
			.year(predictionYear)
			.month(predictionMonth)
			.amount((int) prediction)
			.build();
	}
}
