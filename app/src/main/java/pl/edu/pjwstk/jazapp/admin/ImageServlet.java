package pl.edu.pjwstk.jazapp.admin;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getPathInfo().substring(1);
        File file = new File("/home/olek/Projects/jazzapp/app/content/auctionPhotos/", filename);
        if(!file.isFile()) {
            file = new File("/home/olek/Projects/jazzapp/app/content/auctionPhotos/", "nophoto.jpg");
        }
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
