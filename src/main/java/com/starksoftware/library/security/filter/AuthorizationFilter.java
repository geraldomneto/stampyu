package com.starksoftware.library.security.filter;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import com.starksoftware.library.abstracts.rest.AbstractResponse;
import com.starksoftware.library.security.model.annotation.Secured;
import com.starksoftware.library.security.model.business.FrwSecurityContext;
import com.starksoftware.library.security.model.enumeration.RoleAccess;
import com.starksoftware.library.security.model.exception.RoleException;

/**
 * @author desenvolvedor
 *
 */
@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter extends AbstractResponse implements ContainerRequestFilter {

	private static final Logger LOG = Logger.getLogger(AuthorizationFilter.class.getName());
	private static final String MSG_ERRO = "MSG Erro: %s";

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Get the resource class which matches with the requested URL
		// Extract the roles declared by it
		RoleAccess classRoles = extractRoles(resourceInfo.getResourceClass());

		// Get the resource method which matches with the requested URL
		// Extract the roles declared by it
		RoleAccess methodRoles = extractRoles(resourceInfo.getResourceMethod());

		RoleAccess userRole = ((FrwSecurityContext) requestContext.getSecurityContext()).getUserFrwPrincipal()
				.getRole();

		if (userRole == null) {
			userRole = RoleAccess.USER;
		}

		try {

			// Check if the user is allowed to execute the method
			// The method annotations override the class annotations
			if (RoleAccess.NONE.equals(methodRoles)) {
				checkPermissions(classRoles, userRole);
			} else {
				checkPermissions(methodRoles, userRole);
			}

		} catch (RoleException e) {
			LOG.log(Level.SEVERE, String.format(MSG_ERRO, e.toString()), e);
			requestContext.abortWith(this.errorUnauthorized(null));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, String.format(MSG_ERRO, e.toString()), e);
			requestContext.abortWith(this.errorForbidden(null));
		}
	}

	/**
	 * 
	 * Extract the roles from the annotated element.
	 * 
	 * @param annotatedElement
	 * @return
	 */
	private RoleAccess extractRoles(AnnotatedElement annotatedElement) {
		if (annotatedElement == null) {
			return RoleAccess.NONE;
		}

		Secured secured = annotatedElement.getAnnotation(Secured.class);
		if (secured == null) {
			return RoleAccess.NONE;
		}

		return secured.value();
	}

	private void checkPermissions(RoleAccess allowedRoles, RoleAccess userRole) throws RoleException {

		if (!((userRole.getFlag() & allowedRoles.getFlag()) == allowedRoles.getFlag())) {
			LOG.log(Level.SEVERE, "Usuário não possui o acesso necessário");
			throw new RoleException();
		}
	}
}
