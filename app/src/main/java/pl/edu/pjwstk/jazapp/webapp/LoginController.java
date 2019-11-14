package pl.edu.pjwstk.jazapp.webapp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.login.LoginRequest;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Named
@RequestScoped
public class LoginController {
    @Inject
    private LoginRequest loginRequest;

    @Inject
    private ProfileRepository profileRepository;

    public String login() {

        if(profileRepository.userExists(loginRequest.getUsername())) {

            String correctLogin = loginRequest.getUsername();
            String correctPassword = profileRepository.getPassword(correctLogin);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if(encoder.matches(loginRequest.getPassword(), correctPassword)) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("username", correctLogin);
                session.setAttribute("name", profileRepository.getName(correctLogin));
                session.setAttribute("surname", profileRepository.getSurname(correctLogin));
                return "index.xhtml";
            }
        }
        FacesContext.getCurrentInstance().addMessage("loginForm:password", new FacesMessage("Błędne hasło, albo użytkownik nie istnieje"));
        return "login.xhtml";
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "login.xhtml";
    }
}