package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("packages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PackageService {
    @EJB
    private PackageBean packageBean;

    @POST
    @Path("/")
    public Response create(PackageDTO packageDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        Package newPackage = packageBean.create(
                packageDTO.getMaterials(),
                packageDTO.getIsRecyclable(),
                packageDTO.getFabricationDate()
        );
        if (newPackage == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(PackageDTO.fromWithNoLists(newPackage)).build();
    }

    @GET
    @Path("/")
    public List<PackageDTO> getAllPackages() {
        return PackageDTO.fromWithNoLists(packageBean.getAll());
    }

    @GET
    @Path("/lists")
    public List<PackageDTO> getAllPackagesWithLists() {
        return PackageDTO.from(packageBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getPackage(@PathParam("id") String id) {
        Package package_ = packageBean.find(id);
        if (package_ != null) {
            return Response.ok(PackageDTO.fromWithNoLists(package_)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PACKAGE").build();
    }

    @GET
    @Path("{id}/list")
    public Response getPackageWithList(@PathParam("id") String id) {
        Package package_ = packageBean.find(id);
        if (package_ != null) {
            return Response.ok(PackageDTO.from(package_)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PACKAGE").build();
    }

    @PUT
    @Path("{id}")
    public Response updatePackage(@PathParam("id") String id, PackageDTO updatedPackageDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Package package_ = packageBean.update(
                id,
                updatedPackageDTO.getMaterials(),
                updatedPackageDTO.getIsRecyclable(),
                updatedPackageDTO.getFabricationDate()
        );
        if (package_ != null) {
            return Response.ok(PackageDTO.fromWithNoLists(package_)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_UPDATING_PACKAGE").build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePackage(@PathParam("id") String id) throws MyEntityNotFoundException {
        packageBean.delete(id);
        return Response.noContent().build();
    }
}
