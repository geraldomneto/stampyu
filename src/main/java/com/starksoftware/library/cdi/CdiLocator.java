package com.starksoftware.library.cdi;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

final class CdiLocator {

	private static Logger LOG = Logger.getLogger(CdiLocator.class.getName());
	private static final String CDI_NAO_ENCONTRADO = "Contexto CDI não foi encontrado para instanciar objeto. Ocorre em entidades com @GeneratedValue ou testes unitários.";
	private static final String JNDI_BEAN_MANAGER = "java:comp/BeanManager";

	private static BeanManager beanManager;

	private CdiLocator() {
	}

	private static void initCdi() {
		try {
			beanManager = (BeanManager) new InitialContext().lookup(JNDI_BEAN_MANAGER);
		} catch (NamingException e) {
			LOG.info(CDI_NAO_ENCONTRADO);
		}
	}

	public static BeanManager getCdiObject() {
		if (beanManager == null) {
			CdiLocator.initCdi();
		}
		return beanManager;
	}
}
