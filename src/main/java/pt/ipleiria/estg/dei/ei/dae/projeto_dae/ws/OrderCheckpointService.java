package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.OrderCheckpointDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs.OrderCheckpointBean;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.OrderCheckpoint;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("ordercheckpoints")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderCheckpointService {
    @EJB
    private OrderCheckpointBean orderCheckpointBean;

    @POST
    @Path("/")
    public Response create(OrderCheckpointDTO orderCheckpointDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        OrderCheckpoint newOrderCheckpoint = orderCheckpointBean.create(
                orderCheckpointDTO.getOrderID(),
                orderCheckpointDTO.getPackageID(),
                orderCheckpointDTO.getCarrierID(),
                orderCheckpointDTO.getLocation(),
                orderCheckpointDTO.getDatetime(),
                orderCheckpointDTO.getMinTemperature(),
                orderCheckpointDTO.getMaxTemperature(),
                orderCheckpointDTO.getMinHumidity(),
                orderCheckpointDTO.getMaxHumidity(),
                orderCheckpointDTO.getMinAtmPressure(),
                orderCheckpointDTO.getMaxAtmPressure(),
                orderCheckpointDTO.getMaxAcceleration(),
                orderCheckpointDTO.isWasPackageOpened()
        );
        if (newOrderCheckpoint == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(OrderCheckpointDTO.from(newOrderCheckpoint)).build();
    }

    @GET
    @Path("/")
    public List<OrderCheckpointDTO> getAllOrderCheckpoints() {
        return OrderCheckpointDTO.from(orderCheckpointBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getOrderCheckpoint(@PathParam("id") String id) {
        OrderCheckpoint orderCheckpoint = orderCheckpointBean.find(id);
        if (orderCheckpoint != null) {
            return Response.ok(OrderCheckpointDTO.from(orderCheckpoint)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_ORDER_CHECKPOINT").build();
    }

    @PUT
    @Path("{id}")
    public Response updateOrderCheckpoint(@PathParam("id") String id, OrderCheckpointDTO updatedOrderCheckpointDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        OrderCheckpoint orderCheckpoint = orderCheckpointBean.update(
                id,
                updatedOrderCheckpointDTO.getLocation(),
                updatedOrderCheckpointDTO.getDatetime(),
                updatedOrderCheckpointDTO.getMinTemperature(),
                updatedOrderCheckpointDTO.getMaxTemperature(),
                updatedOrderCheckpointDTO.getMinHumidity(),
                updatedOrderCheckpointDTO.getMaxHumidity(),
                updatedOrderCheckpointDTO.getMinAtmPressure(),
                updatedOrderCheckpointDTO.getMaxAtmPressure(),
                updatedOrderCheckpointDTO.getMaxAcceleration(),
                updatedOrderCheckpointDTO.isWasPackageOpened()
        );
        if (orderCheckpoint != null) {
            return Response.ok(OrderCheckpointDTO.from(orderCheckpoint)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_UPDATING_ORDER_CHECKPOINT").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOrderCheckpoint(@PathParam("id") String id) throws MyEntityNotFoundException {
        orderCheckpointBean.delete(id);
        return Response.noContent().build();
    }
}
