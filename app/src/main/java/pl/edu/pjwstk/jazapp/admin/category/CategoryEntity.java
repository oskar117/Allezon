package pl.edu.pjwstk.jazapp.admin.category;

import pl.edu.pjwstk.jazapp.admin.section.SectionEntity;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "section_id")
    private SectionEntity sectionId;


    public SectionEntity getSectionId() {
        return sectionId;
    }

    public void setSectionId(SectionEntity sectionId) {
        this.sectionId = sectionId;
    }

    public CategoryEntity(String name, SectionEntity sectionEntity) {
        this.name = name;
        this.sectionId = sectionEntity;
    }

    public CategoryEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
