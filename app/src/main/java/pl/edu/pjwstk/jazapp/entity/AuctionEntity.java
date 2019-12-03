package pl.edu.pjwstk.jazapp.entity;

import pl.edu.pjwstk.jazapp.auth.ProfileEntity;

import javax.persistence.*;
import java.util.List;

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
    private double price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private ProfileEntity ownerId;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "auctionId")
    private List<PhotoEntity> photos;

    public AuctionEntity(String title, String description, double price, CategoryEntity categoryEntity, SectionEntity sectionEntity, ProfileEntity profileEntity) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.categoryId = categoryEntity;
        this.sectionId = sectionEntity;
        this.ownerId = profileEntity;
        //this.photos = photos;
    }

    public AuctionEntity() {

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SectionEntity getSectionId() {
        return sectionId;
    }

    public void setSectionId(SectionEntity sectionId) {
        this.sectionId = sectionId;
    }

    public CategoryEntity getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryEntity categoryId) {
        this.categoryId = categoryId;
    }

    public List<PhotoEntity> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoEntity> photos) {
        this.photos = photos;
    }

}
