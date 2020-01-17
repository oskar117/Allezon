package pl.edu.pjwstk.jazapp.login.oauth;

import org.json.JSONObject;
import pl.edu.pjwstk.jazapp.login.auth.ProfileRepository;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

@WebServlet("callback")
public class Callback extends HttpServlet {

    private static final String CLIENT_ID = "876953897435-e8v2rvrn3pfj83rdct5qeup54rt62n0j.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "3mCZoZ6Gi5fogBilWzJ2W1Gc";
    private static String token;

    @Inject
    private ProfileRepository profileRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

       // resp.setStatus(200);
        PrintWriter writer = resp.getWriter();
        if (req.getParameter("code") == null) {
            writer.println("Nic nie podano");
        } else {
            String code =  req.getParameter("code");

            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target("https://oauth2.googleapis.com/token");

            Form form = new Form();
            form.param("grant_type", "authorization_code");
            form.param("code", code);
            form.param("redirect_uri", "http://localhost:8080/app/callback");
            form.param("client_id", CLIENT_ID);
            form.param("client_secret", CLIENT_SECRET);

            Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);

            if(response.getStatus() != 200) {
                resp.sendRedirect(req.getContextPath() + "/login.xhtml");
            } else {
                JSONObject json = new JSONObject(response.readEntity(String.class));
                token = json.getString("access_token");

                json = getSimpleUserDataJson();

                HttpSession session=req.getSession();
                String currUser = json.getString("name");
                session.setAttribute("id", currUser);
                session.setAttribute("username", currUser);
                session.setAttribute("name", json.getString("given_name"));
                session.setAttribute("surname", json.getString("family_name"));

                if(profileRepository.userExists(currUser)) {
                    resp.sendRedirect(req.getContextPath() + "/index.xhtml");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/oauthRegister.xhtml");
                }
            }
        }
    }

    public static JSONObject getSimpleUserDataJson() {
        Client clientApi = ClientBuilder.newClient();
        WebTarget webTargetApi = clientApi.target("https://www.googleapis.com/userinfo/v2/me?access_token=" + token);
        Invocation.Builder invocationBuilder = webTargetApi.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.header("authorization", token).get(Response.class);
        JSONObject userJson = new JSONObject(response.readEntity(String.class));
        return userJson;
    }

    public static String getBirthDate() {
        JSONObject json = getUserDataJson("birthdays");
        DecimalFormat formatter = new DecimalFormat("00");
        String data = "err";

        for(int x = 0; x < json.getJSONArray("birthdays").length(); x++) {
            try{
                json = json.getJSONArray("birthdays").getJSONObject(x).getJSONObject("date");
                data = formatter.format(json.getInt("day")) + "/" + formatter.format(json.getInt("month")) + "/" + json.getInt("year");
            } catch(Exception e) {
                System.out.println("continue: " + e);
                continue;
            }
            break;
        }
        return data;
    }

    public static JSONObject getUserDataJson(String field) {
        Client clientApi = ClientBuilder.newClient();
        WebTarget webTargetApi = clientApi.target("https://people.googleapis.com/v1/people/me?access_token=" + token + "&personFields="+field);
        Invocation.Builder invocationBuilder = webTargetApi.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.header("authorization", token).get(Response.class);
        JSONObject json = new JSONObject(response.readEntity(String.class));
        return json;
    }

}
