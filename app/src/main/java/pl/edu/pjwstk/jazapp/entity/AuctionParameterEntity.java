package pl.edu.pjwstk.jazapp.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "auction_parameter")
public class AuctionParameterEntity {

    @EmbeddedId
    private AuctionParameterId auctionParameterId;
    @OneToOne
    @JoinColumn(name = "auction_id")
    private AuctionEntity auctionId;
    @ManyToOne
    @JoinColumn(name = "parameter_id")
    private ParameterEntity parameterId;
    private String varchar;
}
