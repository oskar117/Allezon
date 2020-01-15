package pl.edu.pjwstk.jazapp.auction.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "auction_parameter")
@JsonIgnoreProperties({"auctionId"})
public class AuctionParameterEntity {

    @EmbeddedId
    private AuctionParameterId auctionParameterId;
    @ManyToOne
    @MapsId("auctionId")
    @JoinColumn(name = "auction_id")
    private AuctionEntity auctionId;
    @ManyToOne
    @MapsId("parameterId")
    @JoinColumn(name = "parameter_id")
    private ParameterEntity parameterId;
    private String value;

    public AuctionParameterEntity() {}

    public AuctionParameterEntity(AuctionEntity auctionId, ParameterEntity parameterId, String varchar) {
        this.auctionId = auctionId;
        this.parameterId = parameterId;
        this.value = varchar;
    }

    public AuctionParameterId getAuctionParameterId() {
        return auctionParameterId;
    }

    public void setAuctionParameterId(AuctionParameterId auctionParameterId) {
        this.auctionParameterId = auctionParameterId;
    }

    public AuctionEntity getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(AuctionEntity auctionId) {
        this.auctionId = auctionId;
    }

    public ParameterEntity getParameterId() {
        return parameterId;
    }

    public void setParameterId(ParameterEntity parameterId) {
        this.parameterId = parameterId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
