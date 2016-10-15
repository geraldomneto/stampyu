package com.starksoftware.library.security.filter;

import java.util.List;
import java.util.Map;

import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.model.ApiDescription;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;

/**
 * @author framework
 *
 */
@SuppressWarnings("deprecation")
public class StkApiDocFilter implements SwaggerSpecFilter {

	static private String API_DOC_SECRET = "framework2016";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.swagger.core.filter.SwaggerSpecFilter#isOperationAllowed(io.swagger.
	 * models.Operation, io.swagger.model.ApiDescription, java.util.Map,
	 * java.util.Map, java.util.Map)
	 */
	@Override
	public boolean isOperationAllowed(Operation operation, ApiDescription api, Map<String, List<String>> params,
			Map<String, String> cookies, Map<String, List<String>> headers) {

		boolean isAuthorized = checkKey(params, headers);

		if (isAuthorized) {
			return true;
		} else {
			// listar aqui serviços que não poderão ser chamados via api doc
			// mesmo com token de autorização
			if (!"GET".equalsIgnoreCase(api.getMethod())) {
				return false;
			} else
				return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.swagger.core.filter.SwaggerSpecFilter#isParamAllowed(io.swagger.models
	 * .parameters.Parameter, io.swagger.models.Operation,
	 * io.swagger.model.ApiDescription, java.util.Map, java.util.Map,
	 * java.util.Map)
	 */
	@Override
	public boolean isParamAllowed(Parameter parameter, Operation operation, ApiDescription api,
			Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {

		boolean isAuthorized = checkKey(params, headers);
		if ("internal".equals(parameter.getAccess()) && !isAuthorized)
			return false;
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.swagger.core.filter.SwaggerSpecFilter#isPropertyAllowed(io.swagger.
	 * models.Model, io.swagger.models.properties.Property, java.lang.String,
	 * java.util.Map, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean isPropertyAllowed(Model model, Property property, String propertyName,
			Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {

		return true;
	}

	/**
	 * @param params
	 * @param headers
	 * @return
	 */
	public boolean checkKey(Map<String, List<String>> params, Map<String, List<String>> headers) {
		String keyValue = null;
		if (params.containsKey("api_key"))
			keyValue = params.get("api_key").get(0);
		else {
			if (headers.containsKey("api_key"))
				keyValue = headers.get("api_key").get(0);
		}
		if (API_DOC_SECRET.equals(keyValue))
			return true;
		else
			return false;
	}
}
