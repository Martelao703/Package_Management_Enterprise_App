package pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.mappers;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    private static final Logger logger = Logger.getLogger(ForbiddenException.class.getCanonicalName());
    @Override
    public jakarta.ws.rs.core.Response toResponse(ForbiddenException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.FORBIDDEN)
                .entity(e.getMessage())
                .build();
    }
}
