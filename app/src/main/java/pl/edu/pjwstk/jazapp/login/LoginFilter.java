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

@WebFilter("*")
public class LoginFilter extends HttpFilter {

    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String currUrl = req.getRequestURI();

        if(!currUrl.contains("javax.faces.resource")) {
            //System.out.println(currUrl.indexOf("/app/index.xhtml") + " " + currUrl + " " + currUrl.contains("javax.faces.resource"));
            if(((currUrl.indexOf("/app/index.xhtml") >= 0) || (currUrl.indexOf("/app/myAuctions.xhtml")>= 0) || (currUrl.indexOf("/app/addAuction.xhtml")>= 0)) && !userIsLogged(req)) {
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            } else {
                if((currUrl.indexOf("/app/admin.xhtml") >= 0 || currUrl.indexOf("/app/adminCategory.xhtml") >=0 || currUrl.indexOf("/app/adminSection.xhtml") >= 0) && !idLoggedUserAdmin(req)) {
                    res.sendRedirect(req.getContextPath() + "/index.xhtml");
                }
            }
        }
        chain.doFilter(req, res);
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

    private boolean idLoggedUserAdmin (HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session.getAttribute("username").equals("admin")) {
            return true;
        }
        return false;
    }
}