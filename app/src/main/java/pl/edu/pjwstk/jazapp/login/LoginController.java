package pl.edu.pjwstk.jazapp.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.login.LoginRequest;
import pl.edu.pjwstk.jazapp.services.ContextUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginController {
    @Inject
    private LoginRequest loginRequest;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private ContextUtils contextUtils;

    public String login() {

        if(profileRepository.userExists(loginRequest.getUsername())) {

            String correctLogin = loginRequest.getUsername();
            String correctPassword = profileRepository.getPassword(correctLogin);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if(encoder.matches(loginRequest.getPassword(), correctPassword)) {
                HttpSession session = contextUtils.getSession();
                session.setAttribute("username", correctLogin);
                session.setAttribute("name", profileRepository.getName(correctLogin));
                session.setAttribute("surname", profileRepository.getSurname(correctLogin));
                return "index.xhtml";
            }
        }
        contextUtils.setMessage("loginForm:password", "Błędne hasło, albo użytkownik nie istnieje");
        return "login.xhtml";
    }

    public String logout() {
        HttpSession session = contextUtils.getSession();
        session.invalidate();
        return "login.xhtml";
    }
}