package pl.edu.pjwstk.jazapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("Hello")
public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//h:outputlink h:commandlink zamiast a href + c:foreach
        resp.setStatus(200);
        PrintWriter writer = resp.getWriter();
        if (req.getParameter("average") == null) {
            writer.println("Nic nie podano");
        } else {
            String[] avg =  req.getParameter("average").split(",");
            double result = 0;

            for(int x = 0; x<avg.length; x++) {
                result += Double.parseDouble(avg[x]);
            }

            result = result / avg.length;

            writer.println("Srednia wynosi: " + result);
        }
    }
}
