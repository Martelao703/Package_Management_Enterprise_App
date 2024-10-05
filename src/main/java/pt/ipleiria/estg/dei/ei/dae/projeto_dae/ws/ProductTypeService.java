package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos.ProductTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs.ProductTypeBean;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductType;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("producttypes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductTypeService {
    @EJB
    private ProductTypeBean productTypeBean;

    @POST
    @Path("/")
    public Response create(ProductTypeDTO productTypeDTO) throws MyEntityExistsException, MyConstraintViolationException {
        ProductType newProductType = productTypeBean.create(productTypeDTO.getName());
        if (newProductType == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(ProductTypeDTO.from(newProductType)).build();
    }

    @GET
    @Path("/")
    public List<ProductTypeDTO> getAllProductTypes() {
        return ProductTypeDTO.from(productTypeBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getProductType(@PathParam("id") String id) {
        ProductType productType = productTypeBean.find(id);
        if (productType != null) {
            return Response.ok(ProductTypeDTO.from(productType)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRODUCT_TYPE").build();
    }

    @PUT
    @Path("{id}")
    public Response updateProductType(@PathParam("id") String id, ProductTypeDTO updatedProductTypeDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        ProductType productType = productTypeBean.update(id, updatedProductTypeDTO.getName());
        if (productType != null) {
            return Response.ok(ProductTypeDTO.from(productType)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_UPDATING_PRODUCT_TYPE").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProductType(@PathParam("id") String id) throws MyEntityNotFoundException {
        productTypeBean.delete(id);
        return Response.noContent().build();
    }
}
