package io.kakaopay.common;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Byungwook lee on 2019-08-11
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@RequiredArgsConstructor
@Component
public class TokenInterceptor implements HandlerInterceptor {
	private final TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			final String authorization = request.getHeader("authorization");
			if (StringUtils.isEmpty(authorization)) {
				final String token = request.getHeader("token");
				validateToken(token);
			} else {
				final String token = StringUtils.split(authorization, " ")[1];
				validateToken(token);
			}
			return true;
		} catch (final Exception exception) {
			throw new IllegalStateException("token 문제있어", exception);
		}
	}

	private void validateToken(final String token) {
		Jwts.parser()
			.setSigningKeyResolver(tokenService.getSigningKeyResolver())
			.parseClaimsJws(token);
	}
}
