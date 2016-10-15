package com.starksoftware.library.security.model.business;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;

import com.starksoftware.library.security.model.entity.UserJWT;
import com.starksoftware.library.security.model.enumeration.RoleAccess;
import com.starksoftware.library.security.util.CommonSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author desenvolvedor
 *
 */
@Stateless
public class TokenHandler {

	/**
	 * @param personId
	 * @return
	 */
	public static String buildSimpleToken(Long personId) {

		byte salt[] = new byte[8];
		new SecureRandom().nextBytes(salt);

		return Long.toHexString(personId) + Arrays.toString(salt);
	}

	/**
	 * @param username
	 * @param role
	 * @return
	 */
	public String createTokenForUser(String login, RoleAccess role) {

		// Token expira em 12 Horas
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, 12);
		date = cal.getTime();

		return Jwts.builder().claim("role", role.getNome()).setSubject(login).setExpiration(date)
				.signWith(SignatureAlgorithm.HS512, CommonSecurity.SECRET).compact();
	}

	/**
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token) {

		return Jwts.parser().setSigningKey(CommonSecurity.SECRET).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * Atraves do TOKEN obtem o nome do usuario e as roles desse usuario.
	 * 
	 * @param token
	 * @return
	 */
	public UserJWT getUserJWTFromToken(String token) {

		Claims claim = Jwts.parser().setSigningKey(CommonSecurity.SECRET).parseClaimsJws(token).getBody();

		RoleAccess role = convertStringToRoleAccess(claim.get("role", String.class));
		UserJWT userJWT = new UserJWT(claim.getSubject(), role);

		return userJWT;
	}

	/**
	 * @param token
	 * @return
	 */
	public boolean isValidToken(String token) {

		try {

			Jwts.parser().setSigningKey(CommonSecurity.SECRET).parseClaimsJws(token);

			return true;
		} catch (ExpiredJwtException e) {
			return false;
		}
	}

	/**
	 * 
	 * @param roleString
	 * @return
	 */
	public RoleAccess convertStringToRoleAccess(String roleString) {

		if (CommonSecurity.ADMIN.equals(roleString)) {
			return RoleAccess.ADMIN;
		}

		return RoleAccess.USER;
	}
}
