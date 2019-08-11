package io.kakaopay.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

/**
 * @author Byungwook lee on 2019-08-11
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
@Service
public class TokenService {
	private static final SecretKey SECRET_KEY = MacProvider.generateKey(SignatureAlgorithm.HS256);

	private SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
		@Override
		public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
			final String encode = Encoders.BASE64.encode(SECRET_KEY.getEncoded());
			return Decoders.BASE64.decode(encode);
		}
	};

	public SigningKeyResolver getSigningKeyResolver() {
		return signingKeyResolver;
	}

	public String generateToken(final String id) {
		final Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		final Date exp = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant());

		final String encode = Encoders.BASE64.encode(SECRET_KEY.getEncoded());

		return Jwts.builder()
			.setId(id)
			.setIssuedAt(now)
			.setNotBefore(now)
			.setExpiration(exp)
			.claim("scope", "api user")
			.signWith(SignatureAlgorithm.HS256, Decoders.BASE64.decode(encode))
			.compact();
	}
}
