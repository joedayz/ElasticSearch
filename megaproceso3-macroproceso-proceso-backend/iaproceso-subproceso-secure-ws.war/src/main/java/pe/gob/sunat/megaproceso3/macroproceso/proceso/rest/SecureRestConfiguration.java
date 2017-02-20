package pe.gob.sunat.megaproceso3.macroproceso.proceso.rest;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.filter.SecureRESTCorsDemoRequestFilter;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.filter.SecureRESTCorsDemoResponseFilter;

/**
 * JAX-RS configuration.
 */
@ApplicationPath("secure")
public class SecureRestConfiguration extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		return getRestClasses();
	}

	private Set<Class<?>> getRestClasses() {
		Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
		resources.add(SecurePersonServiceRest.class);
		resources.add(SecureRESTCorsDemoRequestFilter.class);
		resources.add(SecureRESTCorsDemoResponseFilter.class);
		return resources;
	}

}
