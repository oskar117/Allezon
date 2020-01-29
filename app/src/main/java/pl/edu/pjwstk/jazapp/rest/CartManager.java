package pl.edu.pjwstk.jazapp.rest;

import pl.edu.pjwstk.jazapp.rest.entities.CartEntity;
import pl.edu.pjwstk.jazapp.rest.repos.CartItemsRepository;
import pl.edu.pjwstk.jazapp.rest.repos.CartRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class CartManager {

    @Inject
    private CartItemsRepository cartItemsRepository;
    @Inject
    private CartRepository cartRepository;

    public void addItemToCart(Long userId, Long itemId) {
        CartEntity cart = cartRepository.getCart(userId);

        LocalDateTime date = LocalDateTime.now();

        if(cart == null) {
            cart = cartRepository.addCart(userId, date);
        }

        System.out.println(date + ":::"+ cart.getExpirationDate());
        if(date.isAfter(cart.getExpirationDate())) {
            cart = cartRepository.renewCart(cart, date);
            cartItemsRepository.deleteItems(cart);
        }

        if(!cartItemsRepository.containsItem(itemId, userId)) {
            cartItemsRepository.addItem(cart.getId(), itemId, 1);
        } else {
            cartItemsRepository.increaseItemAmount(cart.getId(), itemId);
        }
    }
}
