package pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger logger = Logger.getLogger(CatchAllExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(Exception e) {
        String errorMsg = e.getMessage();
        logger.severe("ERROR: " + errorMsg);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Internal Server Error")
                .build();
    }
}
