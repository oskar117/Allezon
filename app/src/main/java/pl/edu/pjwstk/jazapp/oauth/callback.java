package pl.edu.pjwstk.jazapp.oauth;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.json.JSONObject;

import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("callback")
public class callback extends HttpServlet {

    private static final String CLIENT_ID = "876953897435-e8v2rvrn3pfj83rdct5qeup54rt62n0j.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "3mCZoZ6Gi5fogBilWzJ2W1Gc";

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

                Client clientApi = ClientBuilder.newClient();
                WebTarget webTargetApi = clientApi.target("https://www.googleapis.com/userinfo/v2/me?access_token=" + json.getString("access_token"));
                Invocation.Builder invocationBuilder = webTargetApi.request(MediaType.APPLICATION_JSON);
                response = invocationBuilder.header("authorization", json.getString("access_token")).get(Response.class);

                JSONObject userJson = new JSONObject(response.readEntity(String.class));

                HttpSession session=req.getSession();
                session.setAttribute("username", userJson.getString("name"));

                resp.sendRedirect(req.getContextPath() + "/index.xhtml");
            }
        }
    }

}
