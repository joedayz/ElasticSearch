package pe.gob.sunat.megaproceso3.macroproceso.proceso.rest;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.filter.RESTCorsDemoRequestFilter;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.filter.RESTCorsDemoResponseFilter;

/**
 * JAX-RS configuration.
 */
@ApplicationPath("rest")
public class RestConfiguration extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		return getRestClasses();
	}

	private Set<Class<?>> getRestClasses() {
		Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
		resources.add(ElasticServiceRest.class);
		resources.add(RESTCorsDemoRequestFilter.class);
		resources.add(RESTCorsDemoResponseFilter.class);
		return resources;
	}

}
