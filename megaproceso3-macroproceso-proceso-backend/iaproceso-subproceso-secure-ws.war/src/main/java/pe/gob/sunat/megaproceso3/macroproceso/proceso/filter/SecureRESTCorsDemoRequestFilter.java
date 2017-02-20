package pe.gob.sunat.megaproceso3.macroproceso.proceso.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecureRESTCorsDemoRequestFilter implements ContainerRequestFilter {

    private final static Logger log = Logger.getLogger( SecureRESTCorsDemoRequestFilter.class.getName() );

    


    @Override
    public void filter( ContainerRequestContext requestCtx ) throws IOException {
        log.info( "Executing REST request filter" );

        String authorizationHeader = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        // When HttpMethod comes as OPTIONS, just acknowledge that it accepts...
        if(!"OPTIONS".equals(requestCtx.getMethod())) {
            log.info( "HTTP Method (OPTIONS) - Detected!" );

            // Check if the HTTP Authorization header is present and formatted correctly 
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new NotAuthorizedException("Authorization header must be provided");
            }


            // Extract the token from the HTTP Authorization header
            String token = authorizationHeader.substring("Bearer".length()).trim();

            try {
                JWTVerifier jwtVerifier = new JWTVerifier(
                    new Base64(true).decode("RCWqCKrzgRCBAeJEMJMZu6gIJjZiuU12kaR8zvUBucIu6qwGcrIImqmTciPE0QBH"),
                    "OcHlrWyV6FwG3Q18AK4abSMMQVcE2mB1");
                Map<String, Object> decoded = jwtVerifier.verify(token);
                
                requestCtx.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return new Principal() {
                            @Override
                            public String getName() {
                                return (String) decoded.get("sub");
                            }
                        };
                    }

                    @Override
                    public boolean isUserInRole(String role) { return true;}

                    @Override
                    public boolean isSecure() {return false;}

                    @Override
                    public String getAuthenticationScheme() {return null;}
                });

            } catch (Exception e) {
            	requestCtx.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
            }            

            // Just send a OK signal back to the browser
            requestCtx.abortWith( Response.status( Response.Status.OK ).build() );
        }
    }
}