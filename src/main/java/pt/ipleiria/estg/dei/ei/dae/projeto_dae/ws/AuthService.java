package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ws;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.security.TokenIssuer;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {

    @Inject
    private TokenIssuer issuer;

    @Context
    private SecurityContext securityContext;

    @EJB
    private UserBean userBean;


    @POST
    @Path("login")
    public Response authenticat(@Valid AuthDTO auth){
        if(userBean.canLogin(auth.getUsername(),auth.getPassword())){
            String token = issuer.issue(auth.getUsername());
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @GET
    @Authenticated
    @Path("user")
    public Response getAuthenticatedUser() {
        var username = securityContext.getUserPrincipal().getName();
        var user = userBean.findOrFail(username);
        return Response.ok(UserDTO.from(user)).build();
    }


}
