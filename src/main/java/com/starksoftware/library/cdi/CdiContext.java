package com.starksoftware.library.cdi;

import java.lang.annotation.Annotation;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

public abstract class CdiContext {

	public static <T> T create(Class<T> clazz) {
		BeanManager beanManager = CdiLocator.getCdiObject();
		if (beanManager == null) {
			return null;
		}
		T result = CdiContext.createBeanManagedObject(clazz, beanManager);
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> T createBeanManagedObject(Class<T> clazz, BeanManager beanManager) {
		Bean bean = (Bean) beanManager.getBeans(clazz, new Annotation[0]).iterator().next();
		CreationalContext cc = beanManager.createCreationalContext((Contextual) bean);
		Object result = beanManager.getReference(bean, clazz, cc);
		return (T) result;
	}
}
