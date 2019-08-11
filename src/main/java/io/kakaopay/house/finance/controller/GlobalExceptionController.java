package io.kakaopay.house.finance.controller;

import io.kakaopay.house.finance.model.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Byungwook lee on 2019-08-10
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionController {
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleException(final Exception exception) {
		final ErrorDto errorDto = new ErrorDto(
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			"Internal server error, check your server.",
			exception.getMessage()
		);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
	}
}
