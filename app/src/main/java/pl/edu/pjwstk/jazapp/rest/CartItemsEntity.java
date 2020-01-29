package pl.edu.pjwstk.jazapp.rest;

import pl.edu.pjwstk.jazapp.auction.entities.AuctionEntity;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartId;
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private AuctionEntity auctionId;
    private Integer amount;

    public CartItemsEntity() {
    }

    public CartItemsEntity(CartEntity ce, AuctionEntity ae, Integer amount) {
        cartId = ce;
        auctionId = ae;
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartEntity getCartId() {
        return cartId;
    }

    public void setCartId(CartEntity cart_id) {
        this.cartId = cart_id;
    }

    public AuctionEntity getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(AuctionEntity auction_id) {
        this.auctionId = auction_id;
    }
}
