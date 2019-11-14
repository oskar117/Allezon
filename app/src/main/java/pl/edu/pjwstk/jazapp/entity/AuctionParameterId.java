package pl.edu.pjwstk.jazapp.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AuctionParameterId implements Serializable {
    private Long auctionId;
    private Long parameterId;
}
