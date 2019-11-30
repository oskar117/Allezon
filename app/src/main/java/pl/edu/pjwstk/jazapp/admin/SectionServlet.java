package pl.edu.pjwstk.jazapp.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("sectionServlet")
public class SectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //h:outputlink h:commandlink zamiast a href + c:foreach

        if (req.getParameter("id") == null || req.getParameter("action") == null) {
            resp.sendRedirect("admin.xhtml");
        } else {

            Long id = Long.valueOf(req.getParameter("id"));
            HttpSession sess = req.getSession();
            sess.setAttribute("editId", id);

            switch (req.getParameter("action")) {
                case "section" : {
                    //new SectionRequest(id);
                    resp.sendRedirect(req.getContextPath() + "/editSection.xhtml");
                    break;
                }
                case "category" : {
                    resp.sendRedirect(req.getContextPath() + "/editCategory.xhtml");
                    break;
                }
            }


        }
    }
}
