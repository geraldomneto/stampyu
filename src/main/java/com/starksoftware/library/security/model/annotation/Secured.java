package com.starksoftware.library.security.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

import com.starksoftware.library.security.model.enumeration.RoleAccess;

/**
 * 
 * Anotacao utilizada nas classes AuthenticationFilter e AuthorizationFilter,
 * classes essas que permitira manipular a requisicao HTTP.
 * 
 * @see com.starksoftware.library.seguranca.filter.framework.base.security.filter.AuthenticationFilter
 * @see com.starksoftware.library.seguranca.filter.framework.base.security.filter.AuthorizationFilter
 * 
 * @author desenvolvedor
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@NameBinding
public @interface Secured {
	RoleAccess value() default RoleAccess.USER;
}
