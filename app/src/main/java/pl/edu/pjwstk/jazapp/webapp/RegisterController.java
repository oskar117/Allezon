package pl.edu.pjwstk.jazapp.webapp;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.oauth.Callback;
import pl.edu.pjwstk.jazapp.register.RegisterRequest;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.text.html.HTMLDocument;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                profileRepository.registerUser(registerRequest.getUsername(), registerRequest.getSurname(), registerRequest.getEmail(), encoder.encode(registerRequest.getPassword()), registerRequest.getName(), LocalDate.parse(registerRequest.getDate(), formatter));
                return "login.xhtml";
            }
        } else {
            System.out.println("zle haslo w sensie powtorzone");
            FacesContext.getCurrentInstance().addMessage("form:password", new FacesMessage("Hasła się nie zgadzają"));
        }
        return "register.xhtml";
    }

    public String oauthRegister() {
        if(registerRequest.getPassword().equals(registerRequest.getPassword2())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            JSONObject json = Callback.getSimpleUserDataJson();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            profileRepository.registerUser(json.getString("name"), json.getString("family_name"), json.getString("email"), encoder.encode(registerRequest.getPassword()), json.getString("given_name"), LocalDate.parse(Callback.getBirthDate(), formatter));
            return "login.xhtml";
        } else {
            System.out.println("zle haslo w sensie powtorzone");
            FacesContext.getCurrentInstance().addMessage("form:password", new FacesMessage("Hasła się nie zgadzają"));
            return "oauthRegister.xhtml";
        }
    }

}
