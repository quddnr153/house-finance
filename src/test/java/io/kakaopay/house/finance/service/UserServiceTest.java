package io.kakaopay.house.finance.service;

import static org.assertj.core.api.Java6Assertions.*;
import static org.mockito.Mockito.*;

import io.kakaopay.house.finance.model.User;
import io.kakaopay.house.finance.model.dto.UserDto;
import io.kakaopay.house.finance.repository.UserRepository;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Byungwook lee on 2019-08-11
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository users;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	public void signUp() {
		// Given
		final UserDto userDto = new UserDto();
		userDto.setId("userid");
		userDto.setPassword("password");
		when(bCryptPasswordEncoder.encode(userDto.getPassword())).thenReturn(userDto.getPassword());
		when(users.save(any(User.class))).thenReturn(new User());

		// When
		userService.signUp(userDto);

		// Then
		verify(bCryptPasswordEncoder, times(1)).encode(userDto.getPassword());
		verify(users, times(1)).save(any(User.class));
	}

	@Test
	public void signIn() {
		// Given
		final UserDto userDto = new UserDto();
		userDto.setId("userid");
		userDto.setPassword("password");
		final Optional<User> foundUser = Optional.of(new User(userDto));
		when(users.findById(userDto.getId())).thenReturn(foundUser);
		when(bCryptPasswordEncoder.matches(userDto.getPassword(), foundUser.orElseThrow().getPassword())).thenReturn(true);

		// When
		final boolean actual = userService.signIn(userDto);

		// Then
		assertThat(actual)
			.isTrue();
		verify(bCryptPasswordEncoder, times(1)).matches(userDto.getPassword(), foundUser.orElseThrow().getPassword());
		verify(users, times(1)).findById(userDto.getId());
	}
}