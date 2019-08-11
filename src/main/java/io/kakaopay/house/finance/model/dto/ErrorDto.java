package io.kakaopay.house.finance.model.dto;

import lombok.Getter;

/**
 * @author Byungwook lee on 2019-08-07
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@Getter
public class ErrorDto {
	private int code;
	private String message;
	private String description;

	public ErrorDto(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ErrorDto(int code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}
}
