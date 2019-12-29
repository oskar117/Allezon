package pl.edu.pjwstk.jazapp.password;

import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.services.ContextUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Named
@RequestScoped
public class ForgotPasswordController {

    @Inject
    private ForgotPasswordRequest forgotPasswordRequest;
    @Inject
    private ProfileRepository profileRepository;
    @Inject
    private ContextUtils contextUtils;
    @Inject ForgotPasswordRepository forgotPasswordRepository;

    public String generateToken() {
        int min = 48;
        int max = 122;
        Random random = new Random();

        String res = "";

        for(int x = 0; x < 64; x++) {
            char temp = (char) (random.nextInt((max - min) + 1) + min);
            res += temp;
        }

        return res;
    }

    public String sendPasswordChangeLink() {

        String email = forgotPasswordRequest.getEmail();

        if(profileRepository.userExistsByEmail(email)) {
            String token = generateToken();
            LocalDateTime data = LocalDateTime.now();
            data = data.plusHours(1);
            forgotPasswordRepository.setPasswordResetRecord(email, token, data);
        } else {
            contextUtils.setMessage("aa", "Użytkownik nie istnieje");
        }

        return "login.xhtml";
    }

}
