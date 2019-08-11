package io.kakaopay.house.finance.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

/**
 * @author Byungwook lee on 2019-08-10
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@Getter
@Builder
@EqualsAndHashCode
public class CurrentStateOfSupply {
	private Integer year;
	private Integer totalAmount;
	private Map<String, Integer> detailAmount;
}
