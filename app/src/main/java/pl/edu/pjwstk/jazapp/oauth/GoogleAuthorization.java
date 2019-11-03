package pl.edu.pjwstk.jazapp.oauth;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "googleAuthorization", urlPatterns = "/googleAuthorization")
public class GoogleAuthorization extends HttpServlet {

    private static final String CLIENT_ID = "876953897435-e8v2rvrn3pfj83rdct5qeup54rt62n0j.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "3mCZoZ6Gi5fogBilWzJ2W1Gc";
    private static final String REDIRECT_URL = "http://localhost:8080/app/callback";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession sess = req.getSession();
        sess.setAttribute("oauth2", "test");

        String authURL = "http://accounts.google.com/o/oauth2/v2/auth" + "?response_type=code"
                + "&client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URL
                + "&scope=" + "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuser.birthday.read+https%3A%2F%2F"
                   + "www.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fplus.me+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fplus.login+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fplus.me+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fplus.login+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fplus.me+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fcontacts+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fcontacts.readonly+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuser.addresses.read+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuser.birthday.read+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuser.emails.read+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuser.phonenumbers.read+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2F"
                    +"www.googleapis.com%2Fauth%2Fuserinfo.email"
                + "&state=" + "test";

        resp.sendRedirect(authURL);
    }
}
