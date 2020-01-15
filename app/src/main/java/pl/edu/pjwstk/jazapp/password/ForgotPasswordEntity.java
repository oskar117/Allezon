package pl.edu.pjwstk.jazapp.password;

import pl.edu.pjwstk.jazapp.login.auth.ProfileEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset")
public class ForgotPasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "email_id")
    private ProfileEntity profileId;

    private String token;
    private LocalDateTime expiration_date;

    public ForgotPasswordEntity() {
    }

    public ForgotPasswordEntity(ProfileEntity profileEntity, String token, LocalDateTime data) {
        this.profileId = profileEntity;
        this.token = token;
        this.expiration_date = data;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileEntity getProfileId() {
        return profileId;
    }

    public void setProfileId(ProfileEntity profileId) {
        this.profileId = profileId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDateTime expiration_date) {
        this.expiration_date = expiration_date;
    }
}
