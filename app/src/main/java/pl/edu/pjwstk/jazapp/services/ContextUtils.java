package pl.edu.pjwstk.jazapp.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ApplicationScoped
public class ContextUtils {

    public HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public void setMessage(String tagName, String message) {
        FacesContext.getCurrentInstance().addMessage(tagName, new FacesMessage(message));
    }
}
