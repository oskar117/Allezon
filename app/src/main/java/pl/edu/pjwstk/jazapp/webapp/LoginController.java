package pl.edu.pjwstk.jazapp.webapp;

import pl.edu.pjwstk.jazapp.login.LoginRequest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginController {
    @Inject
    private LoginRequest loginRequest;

    public void login() {
        System.out.println("Tried to log in using " + loginRequest.toString());
    }
}