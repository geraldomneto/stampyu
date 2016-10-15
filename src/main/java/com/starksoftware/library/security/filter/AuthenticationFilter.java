package com.starksoftware.library.security.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import com.starksoftware.library.abstracts.rest.AbstractResponse;
import com.starksoftware.library.security.model.annotation.Secured;
import com.starksoftware.library.security.model.business.FrwSecurityContext;
import com.starksoftware.library.security.model.business.TokenHandler;
import com.starksoftware.library.security.model.entity.UserJWT;
import com.starksoftware.library.security.model.exception.InvalidTokenException;
import com.starksoftware.library.security.util.CommonSecurity;

/**
 * @author desenvolvedor
 *
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter extends AbstractResponse implements ContainerRequestFilter {

	private static final Logger LOG = Logger.getLogger(AuthenticationFilter.class.getName());
	private static final String MSG_ERRO = "MSG Erro: %s";

	@Inject
	TokenHandler tokenHandler = null;

	@Inject
	FrwSecurityContext frwSecurityContext;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		try {

			// String token = obterTokenDoAuthorizationHeader(requestContext);
			String token = obterTokenDoCookie(requestContext);
			if(token == null || "".equals(token)) {
				token = obterTokenDoAuthorizationHeader(requestContext);
			}

			// Validate the token
			validateToken(token);

			UserJWT userJWT = tokenHandler.getUserJWTFromToken(token);

			frwSecurityContext.getUserFrwPrincipal().setName(userJWT.getUsername());
			frwSecurityContext.getUserFrwPrincipal().setRole(userJWT.getRole());
			frwSecurityContext.setSecure(true);

			requestContext.setSecurityContext(frwSecurityContext);

		} catch (InvalidTokenException e) {
			LOG.log(Level.SEVERE, String.format(MSG_ERRO, e.toString()), e);
			requestContext.abortWith(this.errorUnauthorized(null));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, String.format(MSG_ERRO, e.toString()), e);
			requestContext.abortWith(this.errorForbidden(null));
		}
	}

	/**
	 * 
	 * @param requestContext
	 * @return
	 */
	@SuppressWarnings("all")
	private String obterTokenDoAuthorizationHeader(ContainerRequestContext requestContext) {
		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Check if the HTTP Authorization header is present and formatted
		// correctly
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			LOG.log(Level.SEVERE, "Authorization header must be provided");
			throw new NotAuthorizedException("Authorization header must be provided");
		}

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();
		return token;
	}

	private String obterTokenDoCookie(ContainerRequestContext requestContext) throws InvalidTokenException {

		final Cookie frwToken = requestContext.getCookies().get(CommonSecurity.SESSION_ID);

		if (frwToken == null) {
			throw new InvalidTokenException();
		}

		return frwToken.getValue();
	}

	private void validateToken(String token) throws InvalidTokenException {

		// Check if it was issued by the server and if it's not expired
		// Throw an Exception if the token is invalid

		if (Boolean.FALSE.equals(tokenHandler.isValidToken(token))) {
			LOG.finer("Token inválido.");
			throw new InvalidTokenException();
		}
	}
}
