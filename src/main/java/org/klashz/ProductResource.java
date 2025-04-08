package org.klashz;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.annotations.Pos;

import java.util.Optional;
import java.util.UUID;

@Path("/product")
@Transactional
public class ProductResource {

    private final Optional optional;

    @jakarta.inject.Inject
    public ProductResource(Optional optional) {
        this.optional = optional;
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @POST
    public Response saveProduct(@Valid ProductEntity product) {
        //id == null
        product.persist();
        //id == 1
        return Response.status(201).entity(product).build();
    }

    @GET
    @Path("/all")
    public Response getAllProducts() {
        return Response.ok(ProductEntity.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") UUID id) {
        Optional<ProductEntity>optionalProduct= ProductEntity.findByIdOptional(id);
        return optionalProduct.isPresent() ?
                Response.ok(optionalProduct.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") UUID id, ProductEntity newDataProduct) {
        Optional<ProductEntity>optionalProduct= ProductEntity.findByIdOptional(id);
        if(optionalProduct.isPresent()) {
            ProductEntity oldProduct= optionalProduct.get();
            oldProduct.name = newDataProduct.name;
            oldProduct.description = newDataProduct.description;
            oldProduct.price = newDataProduct.price;
            oldProduct.stock = newDataProduct.stock;
            oldProduct.persist();
            return Response.ok(oldProduct).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") UUID id) {
        return ProductEntity.deleteById(id)
                ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).entity("No se encuentra").build();
    }
}
