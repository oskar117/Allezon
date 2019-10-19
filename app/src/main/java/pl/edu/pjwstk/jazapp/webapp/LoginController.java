package pl.edu.pjwstk.jazapp.webapp;

import pl.edu.pjwstk.jazapp.login.LoginRequest;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginController {
    @Inject
    private LoginRequest loginRequest;

    public void login() {

        String correctLogin = "kamil";
        String correctPassword = "daszke";

        if(loginRequest.getUsername() == correctLogin && loginRequest.getPassword() == correctPassword) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("username", correctLogin);
            System.out.println(session.getAttribute("username"));
        } else {
            System.out.println("Tried to log in using " + loginRequest.toString() + correctPassword + " " + loginRequest.getPassword());
        }
    }
}