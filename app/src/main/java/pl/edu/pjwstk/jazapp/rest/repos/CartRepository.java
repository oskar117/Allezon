package pl.edu.pjwstk.jazapp.rest.repos;

import pl.edu.pjwstk.jazapp.login.auth.ProfileEntity;
import pl.edu.pjwstk.jazapp.rest.entities.CartEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
public class CartRepository {

    @PersistenceContext
    private EntityManager em;

    public CartEntity getCart(Long userId) {
        try{
            ProfileEntity pe = em.getReference(ProfileEntity.class, userId);
            return em.createQuery("from CartEntity where userId = :x", CartEntity.class).setParameter("x", pe).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public CartEntity addCart(Long userId, LocalDateTime date) {
        ProfileEntity pe = em.getReference(ProfileEntity.class, userId);
        CartEntity cartEntity = new CartEntity(pe, date.plusMonths(1));
        em.persist(cartEntity);
        em.flush();
        return cartEntity;
    }

    @Transactional
    public void deleteCart(Long userId) {
        ProfileEntity pe = em.getReference(ProfileEntity.class, userId);
        CartEntity cart = em.createQuery("from CartEntity where userId = :x", CartEntity.class).setParameter("x", pe).getSingleResult();
        em.remove(cart);
    }

    @Transactional
    public CartEntity renewCart(CartEntity cart, LocalDateTime date) {
        cart.setExpirationDate(date.plusMonths(1));
        em.merge(cart);
        return cart;
    }
}
