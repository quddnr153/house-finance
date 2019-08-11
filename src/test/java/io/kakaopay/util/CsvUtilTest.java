package io.kakaopay.util;

import static org.assertj.core.api.Assertions.*;

import io.kakaopay.house.finance.model.Bank;
import io.kakaopay.house.finance.model.CreditGuarantee;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author Byungwook lee on 2019-08-09
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@RunWith(MockitoJUnitRunner.class)
public class CsvUtilTest {
	private static final int NUMBER_OF_BANKS = 9;

	@InjectMocks
	private CsvUtil csvUtil;

	@Test
	public void parseBanks() throws IOException {
		// Given
		final Resource resource = new ClassPathResource("csv/ex3-utf-8.csv");

		// When
		final List<Bank> banks = csvUtil.parseBanks(resource.getFile().toPath());

		// Then
		assertThat(banks)
			.hasSize(NUMBER_OF_BANKS)
			.extracting("name")
			.contains("주택도시기금1)", "국민은행", "우리은행", "신한은행", "한국시티은행", "하나은행", "농협은행/수협은행", "외환은행", "기타은행");
	}

	@Test
	public void parseCreditGuarantees() throws IOException {
		// Given
		final Resource resource = new ClassPathResource("csv/ex3-utf-8.csv");
		final List<Bank> banks = List.of(
			Bank.builder()
				.name("주택도시기금1)")
				.csvIndex(0)
				.build(),
			Bank.builder()
				.name("국민은행")
				.csvIndex(1)
				.build(),
			Bank.builder()
				.name("우리은행")
				.csvIndex(2)
				.build(),
			Bank.builder()
				.name("신한은행")
				.csvIndex(3)
				.build(),
			Bank.builder()
				.name("한국시티은행")
				.csvIndex(4)
				.build(),
			Bank.builder()
				.name("하나은행")
				.csvIndex(5)
				.build(),
			Bank.builder()
				.name("농협은행/수협은행")
				.csvIndex(6)
				.build(),
			Bank.builder()
				.name("외환은행")
				.csvIndex(7)
				.build(),
			Bank.builder()
				.name("기타은행")
				.csvIndex(8)
				.build()
		);

		// When
		final List<CreditGuarantee> creditGuarantees = csvUtil.parseCreditGuarantees(resource.getFile().toPath(), banks);

		// Then
		assertThat(creditGuarantees)
			.hasSize(27);
		assertThat(creditGuarantees.stream().map(CreditGuarantee::getMonth).distinct().count())
			.isEqualTo(3);
	}
}