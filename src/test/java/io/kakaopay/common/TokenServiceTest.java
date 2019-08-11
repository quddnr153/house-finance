package io.kakaopay.common;

import static org.assertj.core.api.Assertions.*;

import io.jsonwebtoken.Jwts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Byungwook lee on 2019-08-11
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@RunWith(MockitoJUnitRunner.class)
public class TokenServiceTest {
	@InjectMocks
	private TokenService tokenService;

	@Test
	public void generateToken() {
		// Given
		final String id = "test";

		// When
		final String token = tokenService.generateToken(id);
		final Throwable throwable = catchThrowable(() -> Jwts.parser()
			.setSigningKeyResolver(tokenService.getSigningKeyResolver())
			.parseClaimsJws(token));

		// Then
		assertThat(throwable)
			.doesNotThrowAnyException();
		assertThat(token)
			.isNotEmpty();
	}
}