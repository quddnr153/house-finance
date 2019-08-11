package io.kakaopay.house.finance.service;

import io.kakaopay.house.finance.model.User;
import io.kakaopay.house.finance.model.dto.UserDto;
import io.kakaopay.house.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Byungwook lee on 2019-08-11
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository users;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public void signUp(final UserDto user) {
		final String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		users.save(new User(user));
	}

	public boolean signIn(final UserDto user) {
		final String id = user.getId();
		final User foundUserById = users.findById(id).orElseThrow();

		return bCryptPasswordEncoder.matches(user.getPassword(), foundUserById.getPassword());
	}
}
