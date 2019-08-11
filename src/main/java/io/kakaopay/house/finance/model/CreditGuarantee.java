package io.kakaopay.house.finance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Byungwook lee on 2019-08-09
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(indexes = {
	@Index(name = "credit_guarantee_year_idx", columnList = "year")
})
public class CreditGuarantee {
	@Id
	@GeneratedValue
	@Column(name = "credit_guarantee_seq")
	private Long seq;

	@ManyToOne
	@JoinColumn(name = "bank_seq")
	private Bank bank;

	private Integer year;

	private Integer month;

	private Integer amount;

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("seq", seq)
			.append("bank", bank)
			.append("year", year)
			.append("month", month)
			.append("amount", amount)
			.toString();
	}
}
