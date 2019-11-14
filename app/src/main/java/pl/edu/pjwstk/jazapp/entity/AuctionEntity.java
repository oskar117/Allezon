package pl.edu.pjwstk.jazapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "auction")
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "section_id")
    private SectionEntity sectionId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryId;
}
