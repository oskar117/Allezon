package pl.edu.pjwstk.jazapp.login.auth;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String surname;
    private String email;
    private String password;
    private String name;
    private LocalDate birth_date;

    public ProfileEntity(String username) {
        this.username = username;
    }

    public ProfileEntity(String username, String surname, String email, String password, String name, LocalDate date) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.surname = surname;
        this.password = password;
        this.birth_date = date;
    }

    public ProfileEntity() {

    }

    public String getUsername() {
        return username;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String nick) {
        this.name = nick;
    }

    public LocalDate getDate() {
        return birth_date;
    }

    public void setDate(LocalDate date) {
        this.birth_date = date;
    }

    public Long getId() {
        return id;
    }

}