package pl.edu.pjwstk.jazapp.auction.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AuctionParameterId implements Serializable {
    private Long auctionId;
    private Long parameterId;

    public AuctionParameterId() {

    }

    public AuctionParameterId(Long auctionId, Long parameterId) {
        this.auctionId = auctionId;
        this.parameterId = parameterId;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getParameterId() {
        return parameterId;
    }

    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }
}
