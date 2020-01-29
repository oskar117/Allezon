package pl.edu.pjwstk.jazapp.rest;

import pl.edu.pjwstk.jazapp.auction.entities.AuctionEntity;
import pl.edu.pjwstk.jazapp.login.auth.ProfileEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CartItemsRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addItem(Long cartId, Long auctionId, Integer amount) {
        CartEntity ce = em.getReference(CartEntity.class, cartId);
        AuctionEntity ae = em.getReference(AuctionEntity.class, auctionId);
        em.persist(new CartItemsEntity(ce, ae, amount));
    }

    @Transactional
    public Boolean containsItem(Long auctionId, Long userId) {

        AuctionEntity ae = em.getReference(AuctionEntity.class, auctionId);
        ProfileEntity pe = em.getReference(ProfileEntity.class, userId);
        CartEntity cart = em.createQuery("From CartEntity where userId = :y ", CartEntity.class).setParameter("y", pe).getSingleResult();

        Object qw = em.createQuery("select count(*) from CartItemsEntity where auctionId = :usr and cartId = :test").setParameter("usr", ae).setParameter("test", cart).getSingleResult();

        if((long)qw != 0) {
            AuctionEntity auction = em.createQuery("from AuctionEntity where id = :usr", AuctionEntity.class).setParameter("usr", auctionId).getSingleResult();
            if(auction != null) {
                if(auction.getId().equals(auctionId)) return true;
            }
        }
        return false;
    }

    @Transactional
    public void deleteItems(CartEntity cart) {
        List<CartItemsEntity> list = em.createQuery("from CartItemsEntity where cartId = :x", CartItemsEntity.class).setParameter("x", cart).getResultList();

        for(var y : list) {
            em.remove(y);
        }
    }

    @Transactional
    public void deleteItem(Long userId, Long itemId) {
        AuctionEntity ae = em.getReference(AuctionEntity.class, itemId);
        ProfileEntity pe = em.getReference(ProfileEntity.class, userId);
        CartEntity cart = em.createQuery("From CartEntity where userId = :y ", CartEntity.class).setParameter("y", pe).getSingleResult();
        CartItemsEntity item = em.createQuery("From CartItemsEntity where cartId = :cart and auctionId = :ae", CartItemsEntity.class).setParameter("cart", cart).setParameter("ae", ae).getSingleResult();
        em.remove(item);
    }

    public List<CartItemsEntity> getCartItems(Long userId) {
        ProfileEntity pe = em.getReference(ProfileEntity.class, userId);
        CartEntity cart;
        try {
            cart = em.createQuery("From CartEntity where userId = :y ", CartEntity.class).setParameter("y", pe).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        List<CartItemsEntity> items = em.createQuery("From CartItemsEntity where cartId = :x").setParameter("x", cart).getResultList();
        return items;
    }

    public List<AuctionEntity> getItems(Long userId) {

        ProfileEntity pe = em.getReference(ProfileEntity.class, userId);
        CartEntity cart;
        try {
            cart = em.createQuery("From CartEntity where userId = :y ", CartEntity.class).setParameter("y", pe).getSingleResult();
        } catch (Exception e) {
            return null;
        }

        List<CartItemsEntity> items = em.createQuery("From CartItemsEntity where cartId = :x").setParameter("x", cart).getResultList();
        List<AuctionEntity> itemIds = new ArrayList<AuctionEntity>();

        for(var x : items) {
            itemIds.add(x.getAuctionId());
        }

        return itemIds;
    }

    @Transactional
    public void increaseItemAmount(Long cartId, Long itemId) {
        AuctionEntity ae = em.getReference(AuctionEntity.class, itemId);
        CartEntity cart = em.createQuery("From CartEntity where id = :y ", CartEntity.class).setParameter("y", cartId).getSingleResult();
        CartItemsEntity item = em.createQuery("From CartItemsEntity where cartId = :cart and auctionId = :ae", CartItemsEntity.class).setParameter("cart", cart).setParameter("ae", ae).getSingleResult();
        item.setAmount(item.getAmount() + 1);
        em.merge(item);
    }
}
