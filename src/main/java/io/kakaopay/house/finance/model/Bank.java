package io.kakaopay.house.finance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

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
public class Bank {
	@Id
	@GeneratedValue
	@Column(name = "bank_seq")
	private Long seq;

	@Column(unique = true)
	private String name;

	private Integer csvIndex;

	@OneToMany
	@JoinColumn(name = "bank_seq")
	private List<CreditGuarantee> creditGuarantees;

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("seq", seq)
			.append("name", name)
			.append("csvIndex", csvIndex)
			.append("creditGuarantees", creditGuarantees)
			.toString();
	}
}
