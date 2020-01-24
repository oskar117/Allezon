package pl.edu.pjwstk.jazapp.rest;

import pl.edu.pjwstk.jazapp.auction.repositories.AuctionRepository;
import pl.edu.pjwstk.jazapp.login.auth.ProfileRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Path("/cart")
public class CartResource {

    @Inject
    private CartRepository cartRepository;

    @Inject CartItemsRepository cartItemsRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(AddToCartCommand addToCartCommand) {

        System.out.println("itemId: "+addToCartCommand.getItem_id()+"\tuserId: "+addToCartCommand.getUser_id());

        var userId = addToCartCommand.getUser_id();
        var itemId = addToCartCommand.getItem_id();

        CartEntity cart = cartRepository.getCart(userId);

        LocalDateTime date = LocalDateTime.now();

        if(cart == null) {
            cart = cartRepository.addCart(userId, date);
        }

        if(date.isAfter(cart.getExpirationDate())) {
            cart = cartRepository.renewCart(cart, date);
            cartItemsRepository.deleteItems(cart);
        }

        if(!cartItemsRepository.containsItem(itemId)) {
            cartItemsRepository.addItem(cart.getId(), itemId);
        }

        return Response.ok().build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cartId}")
    public Response getItems(@PathParam("cartId") Long userId) {
        var items = cartItemsRepository.getItems(userId);
        return Response.ok().entity(items).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public Response deleteCartItem(AddToCartCommand addToCartCommand) {
        cartItemsRepository.deleteItem(addToCartCommand.getUser_id(), addToCartCommand.getItem_id());
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
