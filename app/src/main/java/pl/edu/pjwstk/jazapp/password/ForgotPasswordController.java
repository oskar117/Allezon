package pl.edu.pjwstk.jazapp.password;

import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.services.ContextUtils;
import pl.edu.pjwstk.jazapp.services.EmailUtils;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    @Inject
    private ForgotPasswordRepository forgotPasswordRepository;
    @Inject
    private EmailUtils emailUtils;

    public String generateToken() {
        int min = 48;
        int max = 122;
        Random random = new Random();

        String res = "";

        for(int x = 0; x < 64; x++) {
            char temp;
            do {
                temp = (char) (random.nextInt((max - min) + 1) + min);

            }while (temp > 57 && temp < 97);

            res += temp;
        }

        return res;
    }

    public void sendEmail(String email, String token, LocalDateTime data) {
        Map<String, String> input = new HashMap<String, String>();
        input.put("token", "http://localhost:8080/app/setNewPassword.xhtml?token="+token);

        String body = emailUtils.readEmailFromHtml("/home/olek/Projects/jazzapp/app/src/main/webapp/template/passwordResetTemplate.html" ,input);
        forgotPasswordRepository.setPasswordResetRecord(email, token, data);
        emailUtils.sendEmail(email, "Przypomnienie hasła", body);
    }

    public String sendPasswordChangeLink() {

        String email = forgotPasswordRequest.getEmail();

        if(profileRepository.userExistsByEmail(email)) {
            String token = generateToken();
            LocalDateTime data = LocalDateTime.now();
            data = data.plusHours(1);
            if(!forgotPasswordRepository.doesEmailExist(email)) {
                sendEmail(email, token, data);
                contextUtils.setMessage("loginForm:test", "Wysłano wiadomość email");
            } else {
                if(forgotPasswordRepository.hasTokenExpiredByEmail(email)) {
                    forgotPasswordRepository.deletePasswordResetRecordByEmail(email);
                    sendEmail(email, token, data);
                    contextUtils.setMessage("loginForm:test", "Wysłano wiadomość email");
                } else {
                    contextUtils.setMessage("loginForm:test", "Na ten email już wysłano wiadomość", FacesMessage.SEVERITY_ERROR);
                }
            }
        } else {
            contextUtils.setMessage("loginForm:test", "Użytkownik nie istnieje", FacesMessage.SEVERITY_ERROR);
        }

        return "login.xhtml";
    }

}
