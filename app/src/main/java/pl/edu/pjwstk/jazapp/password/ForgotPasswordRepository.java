package pl.edu.pjwstk.jazapp.password;

import pl.edu.pjwstk.jazapp.auth.ProfileEntity;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;

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

    @Transactional
    public void setPasswordResetRecord(String email, String token, LocalDateTime data) {

        ProfileEntity pe = em.getReference(ProfileEntity.class, profileRepository.getIdByEmail(email));

        ForgotPasswordEntity forgotPasswordEntity = new ForgotPasswordEntity(pe, token, data);
        em.persist(forgotPasswordEntity);

    }
}
