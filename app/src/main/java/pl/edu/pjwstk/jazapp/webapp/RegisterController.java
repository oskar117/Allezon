package pl.edu.pjwstk.jazapp.webapp;

import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.oauth.Callback;
import pl.edu.pjwstk.jazapp.register.RegisterRequest;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument;
import javax.xml.rpc.Call;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import static javax.faces.application.FacesMessage.*;

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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                profileRepository.registerUser(registerRequest.getUsername(), registerRequest.getSurname(), registerRequest.getEmail(), encoder.encode(registerRequest.getPassword()), registerRequest.getName(), LocalDate.parse(registerRequest.getDate(), formatter));
                FacesContext.getCurrentInstance().addMessage("loginForm:test", new FacesMessage("Zarejestrowano pomyślnie"));
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            try {
                profileRepository.registerUser(json.getString("name"), json.getString("family_name"), json.getString("email"), encoder.encode(registerRequest.getPassword()), json.getString("given_name"), LocalDate.parse(Callback.getBirthDate(), formatter));
            } catch (Exception e){
                //FacesContext.getCurrentInstance().addMessage("loginForm:password", new FacesMessage("Rejestracja z Google nie powiodła się"));
                //return "login.xhtml";
                profileRepository.registerUser(json.getString("name"), json.getString("family_name"), json.getString("email"), encoder.encode(registerRequest.getPassword()), json.getString("given_name"), LocalDate.parse(registerRequest.getDate(), formatter));
            }

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("username", json.getString("name"));
            session.setAttribute("name", json.getString("given_name"));
            session.setAttribute("surname", json.getString("family_name"));
            return "index.xhtml";

        } else {
            System.out.println("zle haslo w sensie powtorzone");
            FacesContext.getCurrentInstance().addMessage("formOauth:password", new FacesMessage("Hasła się nie zgadzają"));
        }
        return "oauthRegister.xhtml";
    }

    public String getformDateCheck() {
        try {
            Callback.getBirthDate();
        } catch(Exception e) {
            return "display: block;";
        }
        registerRequest.setDate("01/01/1970");
        return "display: none;";
    }

}
