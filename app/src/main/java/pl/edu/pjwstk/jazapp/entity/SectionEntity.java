package pl.edu.pjwstk.jazapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "section")
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
