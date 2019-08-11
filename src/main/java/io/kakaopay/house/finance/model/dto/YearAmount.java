package io.kakaopay.house.finance.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Byungwook lee on 2019-08-10
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@Getter
@Builder
@EqualsAndHashCode
public class YearAmount {
	private Integer year;
	private Integer amount;
}
