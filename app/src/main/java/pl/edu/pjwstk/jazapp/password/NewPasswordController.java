package pl.edu.pjwstk.jazapp.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.edu.pjwstk.jazapp.admin.SectionRequest;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;

@Named
@RequestScoped
public class NewPasswordController {

    @Inject
    private HttpServletRequest request;

    @Inject
    private ForgotPasswordRepository forgotPasswordRepository;

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
        if (request.getParameter("token") != null) {
            var token = request.getParameter("token");
            return new NewPasswordRequest(token);
        } else {
            return new NewPasswordRequest();
        }
    }

    public String changePassword() {
        String password = newPasswordRequest.getPassword();

        if(password.equals(newPasswordRequest.getPassword2()) && !forgotPasswordRepository.hasTokenExpiredByToken(newPasswordRequest.getToken())) {

            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

            Long userId = forgotPasswordRepository.getUserId(newPasswordRequest.getToken());
            forgotPasswordRepository.deletePasswordResetRecord(userId);
            profileRepository.changePassword(userId, bcrypt.encode(password));
            System.out.println("zminiono");
        }
        return "login.xhtml";
    }
}
