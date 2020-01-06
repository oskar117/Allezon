package pl.edu.pjwstk.jazapp.password;

import pl.edu.pjwstk.jazapp.entity.ParameterEntity;
import pl.edu.pjwstk.jazapp.services.ContextUtils;

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
        System.out.println(req.getRequestURI());
        String token =  req.getParameter("token");
        if(!req.getRequestURI().contains("javax.faces.resource")) {
            if (req.getParameter("token") != null) {
                if(forgotPasswordRepository.hasTokenExpiredByToken(token)) {
                    res.sendRedirect(req.getContextPath() + "/login.xhtml");
                } else {
                    chain.doFilter(req, res);
                }
            } else {
                System.out.println("kibel");
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            }
        } else {
            chain.doFilter(req, res);
        }
    }
}
