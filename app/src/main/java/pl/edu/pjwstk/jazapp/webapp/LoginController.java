package pl.edu.pjwstk.jazapp.webapp;

import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.login.LoginRequest;

import javax.enterprise.context.RequestScoped;
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

    Users users = new Users();

    public String login() {

        if(profileRepository.userExists(loginRequest.getUsername())) {

            String correctLogin = loginRequest.getUsername();
            String correctPassword = profileRepository.getPassword(correctLogin);

            if(loginRequest.getPassword().equals(correctPassword)) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("username", correctLogin);
                return "index.xhtml";
            } else {
                System.out.println("Tried to log in using " + loginRequest.toString() + correctPassword + " " + loginRequest.getPassword());
            }
        }
        return "login.xhtml";
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "login.xhtml";
    }
}