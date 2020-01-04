package pl.edu.pjwstk.jazapp.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ProfileRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void registerUser(String username, String surname, String email, String password, String name, LocalDate date) {

        ProfileEntity profile = new ProfileEntity(username, surname, email, password, name, date);
        em.persist(profile);
    }

    @Transactional
    public boolean userExists(String nickname) {

        Object qw = em.createQuery("select count(*) from ProfileEntity where username = :usr").setParameter("usr", nickname).getSingleResult();

        if((long)qw != 0) {
            ProfileEntity user = em.createQuery("from ProfileEntity where username = :usr", ProfileEntity.class).setParameter("usr", nickname).getSingleResult();
            if(user != null) {
                if(user.getUsername().equals(nickname)) return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean userExistsByEmail(String email) {

        Object qw = em.createQuery("select count(*) from ProfileEntity where email = :usr").setParameter("usr", email).getSingleResult();

        if((long)qw != 0) {
            ProfileEntity user = em.createQuery("from ProfileEntity where email = :usr", ProfileEntity.class).setParameter("usr", email).getSingleResult();
            if(user != null) {
                if(user.getEmail().equals(email)) return true;
            }
        }
        return false;
    }


    public String getPassword(String nickname) {
        ProfileEntity user = em.createQuery("from ProfileEntity where username = :usr", ProfileEntity.class).setParameter("usr", nickname).getSingleResult();
        return user.getPassword();
    }

    public String getName(String username) {
        ProfileEntity user = em.createQuery("from ProfileEntity where username = :usr", ProfileEntity.class).setParameter("usr", username).getSingleResult();
        return user.getName();
    }

    public String getSurname(String username) {
        ProfileEntity user = em.createQuery("from ProfileEntity where username = :usr", ProfileEntity.class).setParameter("usr", username).getSingleResult();
        return user.getSurname();
    }

    public Long getId(String username) {
        ProfileEntity user = em.createQuery("from ProfileEntity where username = :usr", ProfileEntity.class).setParameter("usr", username).getSingleResult();
        return user.getId();
    }

    public Long getIdByEmail(String email) {
        ProfileEntity user = em.createQuery("from ProfileEntity where email = :usr", ProfileEntity.class).setParameter("usr", email).getSingleResult();
        return user.getId();
    }

    @Transactional
    public void changePassword(Long userId, String password) {

        ProfileEntity pe = em.find(ProfileEntity.class, userId);
        pe.setPassword(password);
        em.merge(pe);
    }
}