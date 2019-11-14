package pl.edu.pjwstk.jazapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "parameter")
public class ParameterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String key;

}
