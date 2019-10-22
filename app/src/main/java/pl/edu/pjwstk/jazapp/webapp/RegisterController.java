package pl.edu.pjwstk.jazapp.webapp;

import pl.edu.pjwstk.jazapp.register.RegisterRequest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RegisterController {
    @Inject
    private RegisterRequest registerRequest;

    public void register() {

        Users users = new Users();
        users.registerUser(registerRequest.getUsername(), registerRequest.getPassword());

        System.out.println("Tried to register in using " + registerRequest.toString());
    }

}
