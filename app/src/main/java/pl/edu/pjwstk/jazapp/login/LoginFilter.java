package pl.edu.pjwstk.jazapp.login;

import javax.faces.context.FacesContext;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/index.xhtml")
public class LoginFilter extends HttpFilter {

    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if(!userIsLogged(req)) {
            System.out.println("test1");
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        } else {
            System.out.println("test2");
            chain.doFilter(req, res);
        }
    }

    private boolean userIsLogged(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession(false);

            if(session != null && session.getAttribute("username") != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}