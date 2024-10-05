package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.ProductEntryDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs.ProductEntryBean;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductEntry;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("productentries")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductEntryService {
    @EJB
    private ProductEntryBean productEntryBean;

    @POST
    @Path("/")
    public Response create(ProductEntryDTO productEntryDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        ProductEntry newProductEntry = productEntryBean.create(
                productEntryDTO.getName(),
                productEntryDTO.getProductTypeId(),
                productEntryDTO.getWeight(),
                productEntryDTO.getManufacturerId(),
                productEntryDTO.getIngredients()
        );
        if (newProductEntry == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(ProductEntryDTO.fromWithNoLists(newProductEntry)).build();
    }

    @GET
    @Path("/")
    public List<ProductEntryDTO> getAllProductEntries() {
        return ProductEntryDTO.fromWithNoLists(productEntryBean.getAll());
    }

    @GET
    @Path("/lists")
    public List<ProductEntryDTO> getAllProductEntriesWithLists() {
        return ProductEntryDTO.from(productEntryBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getProductEntry(@PathParam("id") String id) {
        ProductEntry productEntry = productEntryBean.find(id);
        if (productEntry != null) {
            return Response.ok(ProductEntryDTO.fromWithNoLists(productEntry)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRODUCT_ENTRY").build();
    }

    @GET
    @Path("{id}/list")
    public Response getProductEntryWithList(@PathParam("id") String id) {
        ProductEntry productEntry = productEntryBean.find(id);
        if (productEntry != null) {
            return Response.ok(ProductEntryDTO.from(productEntry)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRODUCT_ENTRY").build();
    }

    @PUT
    @Path("{id}")
    public Response updateProductEntry(@PathParam("id") String id, ProductEntryDTO updatedProductEntryDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        ProductEntry productEntry = productEntryBean.update(
                id,
                updatedProductEntryDTO.getName(),
                updatedProductEntryDTO.getWeight(),
                updatedProductEntryDTO.getIngredients()
        );
        if (productEntry != null) {
            return Response.ok(ProductEntryDTO.fromWithNoLists(productEntry)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_UPDATING_PRODUCT_ENTRY").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProductEntry(@PathParam("id") String id) throws MyEntityNotFoundException {
        productEntryBean.delete(id);
        return Response.noContent().build();
    }
}
