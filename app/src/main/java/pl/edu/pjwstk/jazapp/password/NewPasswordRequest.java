package pl.edu.pjwstk.jazapp.password;

import javax.enterprise.inject.Model;

@Model
public class NewPasswordRequest {

    private String password;
    private String password2;
    private String token;

    public NewPasswordRequest() {
    }

    public NewPasswordRequest(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
