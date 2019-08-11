package io.kakaopay.house.finance.model.dto;

import io.kakaopay.house.finance.model.Bank;
import lombok.Getter;

/**
 * @author Byungwook lee on 2019-08-10
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@Getter
public class BankDto {
	private Long seq;
	private String name;

	public BankDto(final Bank bank) {
		seq = bank.getSeq();
		name = bank.getName();
	}
}
