package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.CarrierDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs.CarrierBean;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Carrier;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("carriers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CarrierService {
    @EJB
    private CarrierBean carrierBean;

    @POST
    @Path("/")
    public Response create(CarrierDTO carrierDTO) throws MyEntityExistsException, MyConstraintViolationException {
        Carrier newCarrier = carrierBean.create(carrierDTO.getName());
        if (newCarrier == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(CarrierDTO.from(newCarrier)).build();
    }

    @GET
    @Path("/")
    public List<CarrierDTO> getAllCarriers() {
        return CarrierDTO.from(carrierBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getCarrier(@PathParam("id") String id) {
        Carrier carrier = carrierBean.find(id);
        if (carrier != null) {
            return Response.ok(CarrierDTO.from(carrier)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_CARRIER").build();
    }

    @PUT
    @Path("{id}")
    public Response updateCarrier(@PathParam("id") String id, CarrierDTO updatedCarrierDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        Carrier carrier = carrierBean.update(id, updatedCarrierDTO.getName());
        if (carrier != null) {
            return Response.ok(CarrierDTO.from(carrier)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_UPDATING_CARRIER").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCarrier(@PathParam("id") String id) throws MyEntityNotFoundException {
        carrierBean.delete(id);
        return Response.noContent().build();
    }
}
