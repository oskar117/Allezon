package pl.edu.pjwstk.jazapp.auction.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@JsonIgnoreProperties({"auctionId"})
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private AuctionEntity auctionId;

    private String url;

    public PhotoEntity(AuctionEntity auctionEntity, String url) {
        this.auctionId = auctionEntity;
        this.url = url;
    }

    public PhotoEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuctionEntity getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(AuctionEntity auctionId) {
        this.auctionId = auctionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
