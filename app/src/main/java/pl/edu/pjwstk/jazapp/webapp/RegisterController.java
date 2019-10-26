package pl.edu.pjwstk.jazapp.webapp;

import pl.edu.pjwstk.jazapp.register.RegisterRequest;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintWriter;

@Named
@RequestScoped
public class RegisterController {
    @Inject
    private RegisterRequest registerRequest;

    public void register() {

        if(registerRequest.getPassword().equals(registerRequest.getPassword2())) {
            Users users = new Users();
            users.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
        } else {
            System.out.println("zle haslo w sensie powtorzone");
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("Password must match confirm password");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);
            fc.renderResponse();
        }

        System.out.println("Tried to register in using " + registerRequest.toString());
    }

}
