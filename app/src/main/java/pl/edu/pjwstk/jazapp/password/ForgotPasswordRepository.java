package pl.edu.pjwstk.jazapp.password;

import pl.edu.pjwstk.jazapp.auth.ProfileEntity;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;

import javax.ejb.Local;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
public class ForgotPasswordRepository {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ProfileRepository profileRepository;

    public Long getUserId(String token) {
        ForgotPasswordEntity fpe = em.createQuery("from ForgotPasswordEntity where token = :pe", ForgotPasswordEntity.class).setParameter("pe", token).getSingleResult();
        return fpe.getProfileId().getId();
    }

    @Transactional
    public void setPasswordResetRecord(String email, String token, LocalDateTime data) {

        ProfileEntity pe = em.getReference(ProfileEntity.class, profileRepository.getIdByEmail(email));

        ForgotPasswordEntity forgotPasswordEntity = new ForgotPasswordEntity(pe, token, data);
        em.persist(forgotPasswordEntity);

    }

    public Boolean doesTokenExist(String token) {
        Object qw = em.createQuery("select count(*) from ForgotPasswordEntity where token = :token").setParameter("token", token).getSingleResult();

        if((long)qw != 0) {
            return true;
        }
        return false;
    }

    public boolean doesEmailExist(String email) {
        ProfileEntity pe = em.getReference(ProfileEntity.class, profileRepository.getIdByEmail(email));

        Object qw = em.createQuery("select count(*) from ForgotPasswordEntity where profileId = :pe").setParameter("pe", pe).getSingleResult();

        if((long)qw != 0) {
            return true;
        }
        return false;
    }

    public boolean hasTokenExpiredByEmail(String email) {
        ProfileEntity pe = em.getReference(ProfileEntity.class, profileRepository.getIdByEmail(email));
        ForgotPasswordEntity fpe = em.createQuery("from ForgotPasswordEntity where profileId = :pe", ForgotPasswordEntity.class).setParameter("pe", pe).getSingleResult();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = fpe.getExpiration_date();

        if(now.isAfter(expirationDate)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasTokenExpiredByToken(String token) {

        if(doesTokenExist(token)) {
            ForgotPasswordEntity fpe = em.createQuery("from ForgotPasswordEntity where token = :pe", ForgotPasswordEntity.class).setParameter("pe", token).getSingleResult();

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expirationDate = fpe.getExpiration_date();

            if(now.isAfter(expirationDate)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public void deletePasswordResetRecord(Long id) {
        ProfileEntity pe = em.getReference(ProfileEntity.class, id);
        ForgotPasswordEntity fpe = em.createQuery("from ForgotPasswordEntity where profileId = :pe", ForgotPasswordEntity.class).setParameter("pe", pe).getSingleResult();
        em.remove(fpe);
    }

    @Transactional
    public void deletePasswordResetRecordByEmail(String email) {
        ProfileEntity pe = em.getReference(ProfileEntity.class, profileRepository.getIdByEmail(email));
        ForgotPasswordEntity fpe = em.createQuery("from ForgotPasswordEntity where profileId = :pe", ForgotPasswordEntity.class).setParameter("pe", pe).getSingleResult();
        em.remove(fpe);
    }
}
