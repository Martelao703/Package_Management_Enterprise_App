package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductService {
    @EJB
    private ProductBean productBean;

    @POST
    @Path("/")
    public Response create(ProductDTO productDTO)
            throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        Product newProduct = productBean.create(
                productDTO.getProductEntryId(),
                productDTO.getQuantityLeft(),
                productDTO.getValidityDate()
        );
        if (newProduct == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(ProductDTO.from(newProduct)).build();
    }

    @GET
    @Path("/")
    public List<ProductDTO> getAllProducts() {
        return ProductDTO.from(productBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getProduct(@PathParam("id") String id) {
        Product product = productBean.find(id);
        if (product != null) {
            return Response.ok(ProductDTO.from(product)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRODUCT").build();
    }

    @PUT
    @Path("{id}")
    public Response updateProduct(@PathParam("id") String id, ProductDTO updatedProductDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        Product product = productBean.update(
                id,
                updatedProductDTO.getQuantityLeft(),
                updatedProductDTO.getValidityDate()
        );
        if (product != null) {
            return Response.ok(ProductDTO.from(product)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_UPDATING_PRODUCT").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") String id) throws MyEntityNotFoundException {
        productBean.delete(id);
        return Response.noContent().build();
    }
}
