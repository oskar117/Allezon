package pl.edu.pjwstk.jazapp.rest.entities;

import pl.edu.pjwstk.jazapp.login.auth.ProfileEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private ProfileEntity userId;

    @Column(name="expiration_date")
    private LocalDateTime expirationDate;

    public CartEntity() {
    }

    public CartEntity(ProfileEntity pe, LocalDateTime plusMonths) {
        userId = pe;
        expirationDate = plusMonths;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileEntity getUserId() {
        return userId;
    }

    public void setUserId(ProfileEntity user_id) {
        this.userId = user_id;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expiration_date) {
        this.expirationDate = expiration_date;
    }
}
