package io.kakaopay.house.finance.controller;

import io.kakaopay.common.TokenService;
import io.kakaopay.house.finance.model.dto.ErrorDto;
import io.kakaopay.house.finance.model.dto.UserDto;
import io.kakaopay.house.finance.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Byungwook lee on 2019-08-11
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserService userService;
	private final TokenService tokenService;

	@PostMapping("/users/sign-up")
	public ResponseEntity signUp(@RequestBody final UserDto user) {
		userService.signUp(user);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(Map.of("code", HttpStatus.CREATED.value()));
	}

	@PostMapping("/users/sign-in")
	public ResponseEntity signIn(@RequestBody final UserDto user) {
		if (Objects.isNull(user.getId()) || Objects.isNull(user.getPassword())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(
					new ErrorDto(HttpStatus.BAD_REQUEST.value(), "id password 잘 보냈나 확인해봐.")
				);
		}

		if (!userService.signIn(user)) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.body(new ErrorDto(HttpStatus.NOT_ACCEPTABLE.value(), "wrong password"));
		}

		return ResponseEntity.ok(
			Map.of(
				"code", HttpStatus.OK.value(),
				"token", tokenService.generateToken(user.getId())
			)
		);
	}

	@PostMapping("/api/tokens/refresh")
	public ResponseEntity refreshToken(@RequestHeader final String authorization) {
		return ResponseEntity.ok(
			Map.of(
				"code", HttpStatus.OK.value(),
				"refreshToken", tokenService.generateToken(authorization)
			)
		);
	}
}
