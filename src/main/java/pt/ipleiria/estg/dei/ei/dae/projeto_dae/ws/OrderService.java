package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("orders")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderService {
    @EJB
    private OrderBean orderBean;

    @POST
    @Path("/")
    public Response create(OrderDTO orderDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        Order newOrder = orderBean.create(
                orderDTO.getStartDate(),
                orderDTO.getDeliveryDate(),
                orderDTO.getOrigin(),
                orderDTO.getDestiny()
        );
        if (newOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(OrderDTO.fromWithNoLists(newOrder)).build();
    }

    @GET
    @Path("/")
    public List<OrderDTO> getAllProductEntries() {
        return OrderDTO.fromWithNoLists(orderBean.getAll());
    }

    @GET
    @Path("/lists")
    public List<OrderDTO> getAllProductEntriesWithLists() {
        return OrderDTO.from(orderBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getOrder(@PathParam("id") String id) {
        Order order = orderBean.find(id);
        if (order != null) {
            return Response.ok(OrderDTO.fromWithNoLists(order)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_ORDER").build();
    }

    @GET
    @Path("{id}/list")
    public Response getOrderWithList(@PathParam("id") String id) {
        Order order = orderBean.find(id);
        if (order != null) {
            return Response.ok(OrderDTO.from(order)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_ORDER").build();
    }

    @PUT
    @Path("{id}")
    public Response updateOrder(@PathParam("id") String id, OrderDTO updatedOrderDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Order order = orderBean.update(
                id,
                updatedOrderDTO.getStartDate(),
                updatedOrderDTO.getDeliveryDate(),
                updatedOrderDTO.getOrigin(),
                updatedOrderDTO.getDestiny()
        );
        if (order != null) {
            return Response.ok(OrderDTO.fromWithNoLists(order)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_UPDATING_ORDER").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") String id) throws MyEntityNotFoundException {
        orderBean.delete(id);
        return Response.noContent().build();
    }
}
