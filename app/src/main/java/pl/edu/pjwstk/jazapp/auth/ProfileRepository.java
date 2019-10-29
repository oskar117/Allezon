package pl.edu.pjwstk.jazapp.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class ProfileRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void sampleCodeWithPC() {
        var profile = new ProfileEntity("test", "test", "test", "test", "test", "02-04-2005");

        em.persist(profile);

        final ProfileEntity profileEntity = em.find(ProfileEntity.class, 7L);
        var list = em.createQuery("from ProfileEntity where username = :username", ProfileEntity.class)
                .setParameter("username", "test2")
                .getResultList();
        System.out.println(list);
    }
}