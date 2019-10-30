package pl.edu.pjwstk.jazapp.webapp;

import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.register.RegisterRequest;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.text.html.HTMLDocument;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

@Named
@RequestScoped
public class RegisterController {
    @Inject
    private RegisterRequest registerRequest;

    @Inject
    private ProfileRepository profileRepository;

    public String register() {

        if(registerRequest.getPassword().equals(registerRequest.getPassword2())) {
            if(profileRepository.userExists(registerRequest.getUsername())) {
                FacesContext.getCurrentInstance().addMessage("form:username", new FacesMessage("Nick zajęty"));
            } else {
                profileRepository.registerUser(registerRequest.getUsername(), registerRequest.getSurname(), registerRequest.getEmail(), registerRequest.getPassword(), registerRequest.getName(), registerRequest.getDate());
                return "login.xhtml";
            }
        } else {
            System.out.println("zle haslo w sensie powtorzone");
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("Hasła się nie zgadzają");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage("form:password", msg);

        }
        return "register.xhtml";
    }

}
