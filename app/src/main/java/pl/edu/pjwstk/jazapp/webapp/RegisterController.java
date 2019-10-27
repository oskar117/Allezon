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

    public String register() {

        if(registerRequest.getPassword().equals(registerRequest.getPassword2())) {
            Users users = new Users();
            users.registerUser(registerRequest.getUsername(), registerRequest.getSurname(), registerRequest.getEmail(), registerRequest.getPassword(), registerRequest.getName(), registerRequest.getDate());
            return "login.xhtml";
        } else {
            System.out.println("zle haslo w sensie powtorzone");
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("Password must match confirm password");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage("password", msg);
            FacesMessage facesMessage = new FacesMessage("lalalalala");
            FacesContext.getCurrentInstance().addMessage("username",  facesMessage);
        }
        return "register.xhtml";
    }

}
