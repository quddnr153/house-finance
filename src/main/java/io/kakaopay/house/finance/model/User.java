package io.kakaopay.house.finance.model;

import io.kakaopay.house.finance.model.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Byungwook lee on 2019-08-11
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@NoArgsConstructor
@Getter
@Entity
public class User {
	@Id
	@GeneratedValue
	@Column(name = "user_seq")
	private Long seq;

	@Column(unique = true)
	private String id;
	private String password;

	public User(final UserDto userDto) {
		this.id = userDto.getId();
		this.password = userDto.getPassword();
	}
}
