package pl.edu.pjwstk.jazapp.rest;

import pl.edu.pjwstk.jazapp.rest.entities.CartEntity;
import pl.edu.pjwstk.jazapp.rest.repos.CartItemsRepository;
import pl.edu.pjwstk.jazapp.rest.repos.CartRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cart")
public class CartResource {

    @Inject
    private CartRepository cartRepository;

    @Inject
    private CartItemsRepository cartItemsRepository;

    @Inject
    private CartManager cartManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(AddToCartCommand addToCartCommand) {

        var userId = addToCartCommand.getUser_id();
        var itemId = addToCartCommand.getItem_id();
        cartManager.addItemToCart(userId, itemId);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cartId}")
    public Response getItems(@PathParam("cartId") Long userId) {
        var items = cartItemsRepository.getCartItems(userId);
        return Response.ok().entity(items).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public Response deleteCartItem(AddToCartCommand addToCartCommand) {
        cartItemsRepository.deleteItem(addToCartCommand.getUser_id(), addToCartCommand.getItem_id());
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{itemId}/inc")
    public Response incItems(@PathParam("itemId") Long id) {
        cartItemsRepository.increaseItemAmount(id);
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{itemId}/dec")
    public Response decItems(@PathParam("itemId") Long id) {
        cartItemsRepository.decreaseItemAmount(id);
        return Response.ok().build();
    }


    //TODO zrobic ładnie z kaskadami i wogle
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{userId}/checkout")
    public Response checkout(@PathParam("userId") Long userId) {
        CartEntity cart = cartRepository.getCart(userId);
        cartItemsRepository.deleteItems(cart);
        cartRepository.deleteCart(userId);
        return Response.ok().build();
    }
}
