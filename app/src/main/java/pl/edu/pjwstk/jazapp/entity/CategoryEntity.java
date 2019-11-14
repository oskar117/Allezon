package pl.edu.pjwstk.jazapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "section_id")
    private SectionEntity sectionId;
}
