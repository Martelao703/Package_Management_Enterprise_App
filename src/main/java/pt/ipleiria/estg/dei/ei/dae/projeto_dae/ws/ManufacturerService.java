package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.ManufacturerDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs.ManufacturerBean;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("manufacturers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ManufacturerService {
    @EJB
    private ManufacturerBean manufacturerBean;

    @POST
    @Path("/")
    public Response create(ManufacturerDTO manufacturerDTO) throws MyEntityExistsException, MyConstraintViolationException {
        Manufacturer newManufacturer = manufacturerBean.create(manufacturerDTO.getName());
        if (newManufacturer == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(ManufacturerDTO.from(newManufacturer)).build();
    }

    @GET
    @Path("/")
    public List<ManufacturerDTO> getAllManufacturers() {
        return ManufacturerDTO.from(manufacturerBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getManufacturer(@PathParam("id") String id) {
        Manufacturer manufacturer = manufacturerBean.find(id);
        if (manufacturer != null) {
            return Response.ok(ManufacturerDTO.from(manufacturer)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_MANUFACTURER").build();
    }

    @PUT
    @Path("{id}")
    public Response updateManufacturer(@PathParam("id") String id, ManufacturerDTO updatedManufacturerDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        Manufacturer manufacturer = manufacturerBean.update(id, updatedManufacturerDTO.getName());
        if (manufacturer != null) {
            return Response.ok(ManufacturerDTO.from(manufacturer)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_UPDATING_MANUFACTURER").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteManufacturer(@PathParam("id") String id) throws MyEntityNotFoundException {
        manufacturerBean.delete(id);
        return Response.noContent().build();
    }

}

