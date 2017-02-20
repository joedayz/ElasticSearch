package pe.gob.sunat.megaproceso3.macroproceso.proceso.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class SecureRESTCorsDemoResponseFilter implements ContainerResponseFilter {

    private final static Logger log = Logger.getLogger( SecureRESTCorsDemoResponseFilter.class.getName() );

    @Override
    public void filter( ContainerRequestContext requestCtx, ContainerResponseContext responseCtx ) throws IOException {
        log.info( "Executing REST response filter" );
        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseCtx.getHeaders().add("Access-Control-Max-Age", "3600");
        responseCtx.getHeaders().add("Access-Control-Allow-Headers", "Authorization");
    }
}