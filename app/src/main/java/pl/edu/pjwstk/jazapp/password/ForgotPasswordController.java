package pl.edu.pjwstk.jazapp.password;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ForgotPasswordController {

    @Inject
    private ForgotPasswordRequest forgotPasswordRequest;

    public String sendPasswordChangeLink() {



        return "login.xhtml";
    }

}
