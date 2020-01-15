package pl.edu.pjwstk.jazapp.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.edu.pjwstk.jazapp.login.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.services.ContextUtils;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Named
@RequestScoped
public class NewPasswordController {

    @Inject
    private HttpServletRequest request;

    @Inject
    private ForgotPasswordRepository forgotPasswordRepository;

    @Inject
    private ContextUtils contextUtils;

    @Inject
    private ProfileRepository profileRepository;

    private NewPasswordRequest newPasswordRequest;

    public NewPasswordRequest getNewPasswordRequest() throws IOException {
        if(newPasswordRequest == null) {
            newPasswordRequest = createNewPasswordRequest();
        }
        return newPasswordRequest;
    }

    private NewPasswordRequest createNewPasswordRequest() throws IOException {
        var token = request.getParameter("token");
        return new NewPasswordRequest(token);
    }

    public String changePassword() {

        System.out.println("zmieniamy");

        String password = newPasswordRequest.getPassword();
        if(password.equals(newPasswordRequest.getPassword2()) && !forgotPasswordRepository.hasTokenExpiredByToken(newPasswordRequest.getToken())) {

            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

            Long userId = forgotPasswordRepository.getUserId(newPasswordRequest.getToken());
            forgotPasswordRepository.deletePasswordResetRecord(userId);
            profileRepository.changePassword(userId, bcrypt.encode(password));
            contextUtils.setMessage("loginForm:test", "Pomyślnie zmieniono hasło");
        } else {
            contextUtils.setMessage("loginForm:test", "coś poszło nie tak", FacesMessage.SEVERITY_ERROR);
        }
        return "login.xhtml";
    }
}
