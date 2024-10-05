package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.PackageTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs.PackageTypeBean;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("packagetypes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PackageTypeService {
    @EJB
    private PackageTypeBean packageTypeBean;

    @POST
    @Path("/")
    public Response create(PackageTypeDTO packageTypeDTO) throws MyEntityExistsException, MyConstraintViolationException {
        PackageType newPackageType = packageTypeBean.create(packageTypeDTO.getName());
        if (newPackageType == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(PackageTypeDTO.from(newPackageType)).build();
    }

    @GET
    @Path("/")
    public List<PackageTypeDTO> getAllPackageTypes() {
        return PackageTypeDTO.from(packageTypeBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getPackageType(@PathParam("id") String id) {
        PackageType packageType = packageTypeBean.find(id);
        if (packageType != null) {
            return Response.ok(PackageTypeDTO.from(packageType)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PACKAGE_TYPE").build();
    }

    @PUT
    @Path("{id}")
    public Response updatePackageType(@PathParam("id") String id, PackageTypeDTO updatedPackageTypeDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        PackageType packageType = packageTypeBean.update(id, updatedPackageTypeDTO.getName());
        if (packageType != null) {
            return Response.ok(PackageTypeDTO.from(packageType)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_UPDATING_PACKAGE_TYPE").build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePackageType(@PathParam("id") String id) throws MyEntityNotFoundException {
        packageTypeBean.delete(id);
        return Response.noContent().build();
    }

}
