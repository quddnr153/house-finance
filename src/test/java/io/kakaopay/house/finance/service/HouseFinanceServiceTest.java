package io.kakaopay.house.finance.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Byungwook lee on 2019-08-10
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@RunWith(MockitoJUnitRunner.class)
public class HouseFinanceServiceTest {
	@InjectMocks
	private HouseFinanceService houseFinanceService;

	@Mock
	private CsvUtil csvUtil;

	@Mock
	private BankRepository banks;

	@Mock
	private CreditGuaranteeRepository creditGuarantees;

	@Test
	public void parseCsvAndCreateModels() throws IOException {
		// Given
		final String csvContents = "123,123,123,123,123,\n123,123,123,123,123,";
		final MultipartFile file = new MockMultipartFile("ex3.csv", "ex3.csv", "application/csv", csvContents.getBytes());
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
				.build()
		);

		final List<CreditGuarantee> creditGuarantees = List.of(
			CreditGuarantee.builder().bank(banks.get(0)).year(2019).month(1).amount(10).build(),
			CreditGuarantee.builder().bank(banks.get(0)).year(2019).month(2).amount(10).build(),
			CreditGuarantee.builder().bank(banks.get(0)).year(2019).month(3).amount(10).build(),
			CreditGuarantee.builder().bank(banks.get(1)).year(2019).month(1).amount(10).build(),
			CreditGuarantee.builder().bank(banks.get(1)).year(2019).month(2).amount(10).build(),
			CreditGuarantee.builder().bank(banks.get(1)).year(2019).month(3).amount(10).build(),
			CreditGuarantee.builder().bank(banks.get(2)).year(2019).month(1).amount(10).build(),
			CreditGuarantee.builder().bank(banks.get(2)).year(2019).month(2).amount(10).build(),
			CreditGuarantee.builder().bank(banks.get(2)).year(2019).month(3).amount(10).build()
		);

		when(csvUtil.parseBanks(any(Path.class))).thenReturn(banks);
		when(csvUtil.parseCreditGuarantees(any(Path.class), anyList())).thenReturn(creditGuarantees);
		when(this.banks.saveAll(banks)).thenReturn(banks);
		when(this.creditGuarantees.saveAll(creditGuarantees)).thenReturn(creditGuarantees);

		// When
		houseFinanceService.parseCsvAndCreateModels(file);

		// Then
		verify(csvUtil, times(1)).parseBanks(any(Path.class));
		verify(csvUtil, times(1)).parseCreditGuarantees(any(Path.class), anyList());
		verify(this.banks, times(1)).saveAll(banks);
		verify(this.creditGuarantees, times(1)).saveAll(creditGuarantees);
	}

	@Test
	public void fetchBanks() {
		// Given
		final List<Bank> foundBanks = List.of(
			Bank.builder().seq(0L).name("test 01").build(),
			Bank.builder().seq(1L).name("test 02").build(),
			Bank.builder().seq(2L).name("test 03").build()
		);
		when(banks.findAll()).thenReturn(foundBanks);

		// When
		final List<BankDto> actual = houseFinanceService.fetchBanks();

		// Then
		assertThat(actual)
			.hasSize(3)
			.extracting("name")
			.contains(foundBanks.stream().map(Bank::getName).toArray());
	}

	@Test
	public void fetchCreditGuarantee_FROM_TO() {
		// Given
		final Integer from = 2005;
		final Integer to = 2017;
		final List<CreditGuarantee> expected = List.of(
			CreditGuarantee.builder()
				.seq(1L)
				.year(2005)
				.month(2)
				.amount(300)
				.build(),
			CreditGuarantee.builder()
				.seq(2L)
				.year(2006)
				.month(3)
				.amount(300)
				.build(),
			CreditGuarantee.builder()
				.seq(3L)
				.year(20017)
				.month(2)
				.amount(300)
				.build()
		);
		when(this.creditGuarantees.findAllByYearGreaterThanEqualAndYearLessThanEqual(from, to)).thenReturn(expected);

		// When
		final List<CreditGuarantee> actual = houseFinanceService.fetchCreditGuarantee(from, to);

		// Then
		assertThat(actual)
			.isEqualTo(expected);
		verify(this.creditGuarantees, times(1)).findAllByYearGreaterThanEqualAndYearLessThanEqual(from, to);
	}

	@Test
	public void fetchCreditGuarantee_FROM() {
		// Given
		final Integer from = 2005;
		final Integer to = null;
		final List<CreditGuarantee> expected = List.of(
			CreditGuarantee.builder()
				.seq(1L)
				.year(2005)
				.month(2)
				.amount(300)
				.build(),
			CreditGuarantee.builder()
				.seq(2L)
				.year(2006)
				.month(3)
				.amount(300)
				.build(),
			CreditGuarantee.builder()
				.seq(3L)
				.year(20017)
				.month(2)
				.amount(300)
				.build()
		);
		when(this.creditGuarantees.findAllByYearGreaterThanEqual(from)).thenReturn(expected);

		// When
		final List<CreditGuarantee> actual = houseFinanceService.fetchCreditGuarantee(from, to);

		// Then
		assertThat(actual)
			.isEqualTo(expected);
		verify(this.creditGuarantees, times(1)).findAllByYearGreaterThanEqual(from);
	}

	@Test
	public void fetchCreditGuarantee_TO() {
		// Given
		final Integer from = null;
		final Integer to = 2017;
		final List<CreditGuarantee> expected = List.of(
			CreditGuarantee.builder()
				.seq(1L)
				.year(2005)
				.month(2)
				.amount(300)
				.build(),
			CreditGuarantee.builder()
				.seq(2L)
				.year(2006)
				.month(3)
				.amount(300)
				.build(),
			CreditGuarantee.builder()
				.seq(3L)
				.year(20017)
				.month(2)
				.amount(300)
				.build()
		);
		when(this.creditGuarantees.findAllByYearLessThanEqual(to)).thenReturn(expected);

		// When
		final List<CreditGuarantee> actual = houseFinanceService.fetchCreditGuarantee(from, to);

		// Then
		assertThat(actual)
			.isEqualTo(expected);
		verify(this.creditGuarantees, times(1)).findAllByYearLessThanEqual(to);
	}

	@Test
	public void fetchCreditGuarantee_ALL() {
		// Given
		final Integer from = null;
		final Integer to = null;
		final List<CreditGuarantee> expected = List.of(
			CreditGuarantee.builder()
				.seq(1L)
				.year(2005)
				.month(2)
				.amount(300)
				.build(),
			CreditGuarantee.builder()
				.seq(2L)
				.year(2006)
				.month(3)
				.amount(300)
				.build(),
			CreditGuarantee.builder()
				.seq(3L)
				.year(20017)
				.month(2)
				.amount(300)
				.build()
		);
		when(this.creditGuarantees.findAll()).thenReturn(expected);

		// When
		final List<CreditGuarantee> actual = houseFinanceService.fetchCreditGuarantee(from, to);

		// Then
		assertThat(actual)
			.isEqualTo(expected);
		verify(this.creditGuarantees, times(1)).findAll();
	}

	@Test
	public void fetchCurrentStatesOfSupplyAboutHouseFinance() {
		// Given
		final Integer from = null;
		final Integer to = null;

		final Bank bank01 = Bank.builder()
			.seq(1L)
			.name("test01")
			.build();
		final CreditGuarantee cg01 = CreditGuarantee.builder()
			.seq(1L)
			.year(2005)
			.month(2)
			.amount(300)
			.bank(
				bank01
			)
			.build();
		final CreditGuarantee cg02 = CreditGuarantee.builder()
			.seq(2L)
			.year(2006)
			.month(3)
			.amount(300)
			.bank(bank01)
			.build();
		final CreditGuarantee cg03 = CreditGuarantee.builder()
			.seq(3L)
			.year(2017)
			.month(2)
			.amount(300)
			.bank(bank01)
			.build();
		final List<CreditGuarantee> creditGuarantees = List.of(
			cg01,
			cg02,
			cg03
		);
		when(this.creditGuarantees.findAll()).thenReturn(creditGuarantees);
		List<CurrentStateOfSupply> expected = List.of(
			CurrentStateOfSupply.builder()
				.year(2005)
				.totalAmount(300)
				.detailAmount(
					Map.of(
						bank01.getName(), cg01.getAmount()
					)
				)
				.build(),
			CurrentStateOfSupply.builder()
				.year(2006)
				.totalAmount(300)
				.detailAmount(
					Map.of(
						bank01.getName(), cg01.getAmount()
					)
				)
				.build(),
			CurrentStateOfSupply.builder()
				.year(2017)
				.totalAmount(300)
				.detailAmount(
					Map.of(
						bank01.getName(), cg01.getAmount()
					)
				)
				.build()
		);

		// When
		final List<CurrentStateOfSupply> actual = houseFinanceService.fetchCurrentStatesOfSupplyAboutHouseFinance(from, to);

		// Then
		assertThat(actual)
			.contains(expected.get(0), expected.get(1), expected.get(2));
		verify(this.creditGuarantees, times(1)).findAll();
	}

	@Test
	public void fetchMostSupportBankAbout() {
		// Given
		final Integer year = 2005;

		final Bank bank01 = Bank.builder()
			.seq(1L)
			.name("test01")
			.build();
		final Bank bank02 = Bank.builder()
			.seq(2L)
			.name("test02")
			.build();
		final CreditGuarantee cg01 = CreditGuarantee.builder()
			.seq(1L)
			.year(year)
			.month(2)
			.amount(300)
			.bank(
				bank01
			)
			.build();
		final CreditGuarantee cg02 = CreditGuarantee.builder()
			.seq(2L)
			.year(year)
			.month(3)
			.amount(400)
			.bank(bank01)
			.build();
		final CreditGuarantee cg03 = CreditGuarantee.builder()
			.seq(3L)
			.year(year)
			.month(2)
			.amount(300)
			.bank(bank02)
			.build();
		final List<CreditGuarantee> creditGuarantees = List.of(
			cg01,
			cg02,
			cg03
		);
		when(this.creditGuarantees.findAllByYearGreaterThanEqualAndYearLessThanEqual(year, year)).thenReturn(creditGuarantees);
		MostSupportBankAboutYearDto expected = MostSupportBankAboutYearDto.builder()
			.year(year)
			.bankName(bank01.getName())
			.totalAmount(cg01.getAmount() + cg02.getAmount())
			.build();

		// When
		final MostSupportBankAboutYearDto actual = houseFinanceService.fetchMostSupportBankAbout(year);

		// Then
		System.out.println(actual);
		System.out.println(expected);
		verify(this.creditGuarantees, times(1)).findAllByYearGreaterThanEqualAndYearLessThanEqual(year, year);
		assertThat(actual)
			.isEqualTo(expected);
	}

	@Test
	public void fetchMinAndMaxOfAverage() {
		// Given
		final Long bankSeq = 1L;
		final Integer from = 2005;
		final Integer to = 2007;

		final CreditGuarantee cg1 = CreditGuarantee.builder()
			.seq(1L)
			.year(2005)
			.month(1)
			.amount(100)
			.build();
		final CreditGuarantee cg2 = CreditGuarantee.builder()
			.seq(2L)
			.year(2005)
			.month(2)
			.amount(300)
			.build();
		final CreditGuarantee cg3 = CreditGuarantee.builder()
			.seq(3L)
			.year(2006)
			.month(1)
			.amount(10)
			.build();
		final CreditGuarantee cg4 = CreditGuarantee.builder()
			.seq(4L)
			.year(2006)
			.month(2)
			.amount(30)
			.build();
		final CreditGuarantee cg5 = CreditGuarantee.builder()
			.seq(5L)
			.year(2007)
			.month(1)
			.amount(2)
			.build();
		final CreditGuarantee cg6 = CreditGuarantee.builder()
			.seq(6L)
			.year(2007)
			.month(2)
			.amount(30)
			.build();

		final Bank bank = Bank.builder()
			.seq(bankSeq)
			.name("test01")
			.creditGuarantees(List.of(cg1, cg2, cg3, cg4, cg5, cg6))
			.build();
		when(this.banks.getOne(bankSeq)).thenReturn(bank);
		final MinMaxOfAverageDto expected = MinMaxOfAverageDto.builder()
			.bankName(bank.getName())
			.supportAmount(
				SupportAmount.builder()
					.min(
						YearAmount.builder()
							.year(2007)
							.amount((cg5.getAmount() + cg6.getAmount()) / 2)
							.build()
					)
					.max(
						YearAmount.builder()
							.year(2005)
							.amount((cg1.getAmount() + cg2.getAmount()) / 2)
							.build()
					)
					.build()
			)
			.build();

		// When
		final MinMaxOfAverageDto actual = houseFinanceService.fetchMinAndMaxOfAverage(bankSeq, from, to);

		// Then
		assertThat(actual)
			.isEqualTo(expected);
		verify(this.banks, times(1)).getOne(bankSeq);
	}

	@Test
	public void predictAmount() {
		// Given
		final Long bankSeq = 1L;
		final Integer trainingFrom = 2005;
		final Integer trainingTo = 2017;
		final Integer predictionYear = 2018;
		final Integer predictionMonth = 2;

		final CreditGuarantee cg01 = CreditGuarantee.builder()
			.seq(1L)
			.year(2005)
			.month(predictionMonth)
			.amount(864)
			.build();
		final CreditGuarantee cg02 = CreditGuarantee.builder()
			.seq(2L)
			.year(2006)
			.month(predictionMonth)
			.amount(416)
			.build();
		final CreditGuarantee cg03 = CreditGuarantee.builder()
			.seq(3L)
			.year(2007)
			.month(predictionMonth)
			.amount(263)
			.build();
		final CreditGuarantee cg04 = CreditGuarantee.builder()
			.seq(4L)
			.year(2008)
			.month(predictionMonth)
			.amount(1659)
			.build();
		final CreditGuarantee cg05 = CreditGuarantee.builder()
			.seq(5L)
			.year(2009)
			.month(predictionMonth)
			.amount(394)
			.build();
		final CreditGuarantee cg06 = CreditGuarantee.builder()
			.seq(6L)
			.year(2010)
			.month(predictionMonth)
			.amount(2233)
			.build();
		final CreditGuarantee cg07 = CreditGuarantee.builder()
			.seq(7L)
			.year(2011)
			.month(predictionMonth)
			.amount(1140)
			.build();
		final CreditGuarantee cg08 = CreditGuarantee.builder()
			.seq(8L)
			.year(2012)
			.month(predictionMonth)
			.amount(2527)
			.build();
		final CreditGuarantee cg09 = CreditGuarantee.builder()
			.seq(9L)
			.year(2013)
			.month(predictionMonth)
			.amount(3486)
			.build();
		final CreditGuarantee cg10 = CreditGuarantee.builder()
			.seq(10L)
			.year(2014)
			.month(predictionMonth)
			.amount(2932)
			.build();
		final CreditGuarantee cg11 = CreditGuarantee.builder()
			.seq(11L)
			.year(2015)
			.month(predictionMonth)
			.amount(3906)
			.build();
		final CreditGuarantee cg12 = CreditGuarantee.builder()
			.seq(12L)
			.year(2016)
			.month(predictionMonth)
			.amount(5073)
			.build();
		final CreditGuarantee cg13 = CreditGuarantee.builder()
			.seq(13L)
			.year(2017)
			.month(predictionMonth)
			.amount(3278)
			.build();

		final Bank bank = Bank.builder()
			.seq(bankSeq)
			.name("test01")
			.creditGuarantees(List.of(cg01, cg02, cg03, cg04, cg05, cg06, cg07, cg08, cg09, cg10, cg11, cg12, cg13))
			.build();
		when(this.banks.getOne(bankSeq)).thenReturn(bank);
		final PredictionDto expected = PredictionDto.builder()
			.year(predictionYear)
			.month(predictionMonth)
			.bankName(bank.getName())
			.amount(4576)
			.build();

		// When
		final PredictionDto actual = houseFinanceService.predictAmount(bankSeq, trainingFrom, trainingTo, predictionYear, predictionMonth);

		// Then
		assertThat(actual)
			.isEqualTo(expected);
		verify(this.banks, times(1)).getOne(bankSeq);
	}
}