package pl.edu.pjwstk.jazapp.password;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/setNewPassword.xhtml")
public class NewPasswordFilter extends HttpFilter {

    @Inject
    private ForgotPasswordRepository forgotPasswordRepository;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println(req.getHeader("Faces-Request"));
        if (req.getParameter("token") != null) {
            if (forgotPasswordRepository.hasTokenExpiredByToken(req.getParameter("token"))) {
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            }
        } else {
            if(req.getHeader("Faces-Request") == null) {
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            } else if((req.getHeader("Faces-Request") != null && !req.getHeader("Faces-Request").equals("partial/ajax"))) {
                chain.doFilter(req, res);
            }
        }
        chain.doFilter(req, res);
    }
}
