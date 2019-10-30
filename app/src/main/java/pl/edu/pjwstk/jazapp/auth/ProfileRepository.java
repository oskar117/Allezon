package pl.edu.pjwstk.jazapp.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProfileRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void registerUser(String username, String surname, String email, String password, String name, String date) {

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

    public String getPassword(String nickname) {
        ProfileEntity user = em.createQuery("from ProfileEntity where username = :usr", ProfileEntity.class).setParameter("usr", nickname).getSingleResult();
        return user.getPassword();
    }

}