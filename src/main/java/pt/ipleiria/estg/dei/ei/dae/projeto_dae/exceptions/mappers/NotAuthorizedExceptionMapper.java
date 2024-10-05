package pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.mappers;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
    private static final Logger logger = Logger.getLogger(NotAuthorizedException.class.getCanonicalName());
    @Override
    public jakarta.ws.rs.core.Response toResponse(NotAuthorizedException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.UNAUTHORIZED)
                .entity(e.getMessage())
                .build();
    }
}
