package io.kakaopay.house.finance.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Byungwook lee on 2019-08-10
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@Getter
@Builder
@EqualsAndHashCode
public class MostSupportBankAboutYearDto {
	private Integer year;
	private String bankName;
	@JsonIgnore
	private Integer totalAmount;
}
