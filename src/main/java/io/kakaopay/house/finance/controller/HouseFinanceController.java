package io.kakaopay.house.finance.controller;

import io.kakaopay.house.finance.model.dto.ErrorDto;
import io.kakaopay.house.finance.service.HouseFinanceService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Byungwook lee on 2019-08-10
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@RequiredArgsConstructor
@RestController
public class HouseFinanceController {
	private final HouseFinanceService houseFinanceService;

	@PostMapping("/api/house-finances/csv-upload")
	public ResponseEntity uploadCsvFile(final MultipartFile csvFile) throws IOException {
		if (Objects.isNull(csvFile)) {
			final ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), "You should upload csv file.");

			return ResponseEntity.badRequest().body(errorDto);
		}

		houseFinanceService.parseCsvAndCreateModels(csvFile);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(Map.of("code", HttpStatus.CREATED.value()));
	}

	@GetMapping("/api/banks")
	public ResponseEntity fetchBanks() {
		return ResponseEntity.ok(
			Map.of(
				"code", HttpStatus.OK.value(),
				"banks", houseFinanceService.fetchBanks()
			)
		);
	}

	@GetMapping("/api/house-finances/current-state-of-supply")
	public ResponseEntity fetchCurrentStateOfSupplyAboutHouseFinance(final Integer from, final Integer to) {
		return ResponseEntity.ok(
			Map.of(
				"code", HttpStatus.OK.value(),
				"currentStateOfSupply", houseFinanceService.fetchCurrentStatesOfSupplyAboutHouseFinance(from, to)
			)
		);
	}

	@GetMapping("/api/house-finances/most-support-banks/{year}")
	public ResponseEntity fetchMostSupportBank(@PathVariable final Integer year) {
		return ResponseEntity.ok(
			Map.of(
				"code", HttpStatus.OK.value(),
				"mostSupportBank", houseFinanceService.fetchMostSupportBankAbout(year)
			)
		);
	}

	@GetMapping("/api/house-finances/min-max-of-avg/{bankSeq}")
	public ResponseEntity fetchMinAndMaxOfAverage(@PathVariable final Long bankSeq, final Integer from, final Integer to) {
		return ResponseEntity.ok(
			Map.of(
				"code", HttpStatus.OK.value(),
				"minMaxOfAverage", houseFinanceService.fetchMinAndMaxOfAverage(bankSeq, from, to)
			)
		);
	}

	@GetMapping("/api/house-finances/predictions/{bankSeq}")
	public ResponseEntity predictAmount(
		@PathVariable final Long bankSeq,
		@RequestParam(defaultValue = "2005") final Integer trainingFrom,
		@RequestParam(defaultValue = "2017") final Integer trainingTo,
		@RequestParam(defaultValue = "2018") final Integer predictionYear,
		@RequestParam(defaultValue = "2") final Integer predictionMonth
	) {
		return ResponseEntity.ok(
			Map.of(
				"code", HttpStatus.OK.value(),
				"prediction", houseFinanceService.predictAmount(bankSeq, trainingFrom, trainingTo, predictionYear, predictionMonth)
			)
		);
	}
}
