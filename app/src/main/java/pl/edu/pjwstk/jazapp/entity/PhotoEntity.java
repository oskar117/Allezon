package pl.edu.pjwstk.jazapp.entity;

import jdk.jfr.Enabled;

import javax.persistence.*;
import java.util.List;

@Enabled
@Table(name = "photo")
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @JoinColumn(name = "auction_id")
    private AuctionEntity auctionId;

    @OneToMany
    private List<String> url;
}
