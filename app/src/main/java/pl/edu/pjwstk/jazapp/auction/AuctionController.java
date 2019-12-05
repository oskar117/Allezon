package pl.edu.pjwstk.jazapp.auction;

import pl.edu.pjwstk.jazapp.admin.CategoryRequest;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.entity.AuctionEntity;
import pl.edu.pjwstk.jazapp.entity.PhotoEntity;
import pl.edu.pjwstk.jazapp.entity.TestRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class AuctionController {

    @Inject
    private TestRepository testRepository;

    private AuctionRequest auctionRequest;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private HttpServletRequest request;


    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

    public AuctionRequest getAuctionRequest() {
        if(auctionRequest == null) {
            auctionRequest = createAuctionRequest();
        }
        return auctionRequest;
    }

    private AuctionRequest createAuctionRequest() {
        if (request.getParameter("id") != null) {
            var id = request.getParameter("id");
            var auction = testRepository.getAuction(Long.parseLong(id));
            Map<String, String> params = new LinkedHashMap<String, String>();
            for(var x : auction.getParams()) {
                params.put(x.getParameterId().getKey(), x.getValue());
            }
            return new AuctionRequest(auction.getId(), auction.getTitle(), auction.getDescription(), auction.getSectionId().getId(), String.valueOf(auction.getPrice()), auction.getCategoryId().getId(), params);
        }
        return new AuctionRequest();
    }

    public String add() throws ServletException, IOException {

        List<String> photos = new ArrayList<String>();

        Random random = new Random();
//        for(Part x : getAllParts(auctionRequest.getPhotos())){
//            try (InputStream input = x.getInputStream()) {
//                String url = auctionRequest.getTitle()+"_"+random.nextInt()+".jpg";
//                Files.copy(input, new File("/home/olek/Projects/jazzapp/app/content/auctionPhotos", url).toPath());
//                photos.add(url);
//            }
//            catch (IOException e) {
//                System.out.println("error: " + e.getMessage());
//            }
//        }
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String owner = (String) session.getAttribute("username");
        if(auctionRequest.getId() != null) {
            testRepository.addAuction(auctionRequest.getId() ,auctionRequest.getTitle(), auctionRequest.getDescription(), auctionRequest.getPrice(), auctionRequest.getSection(), auctionRequest.getCategory(), photos, auctionRequest.getParams(), profileRepository.getId(owner));
            return "myAuctions.xhtml";
        } else {
            testRepository.addAuction(null ,auctionRequest.getTitle(), auctionRequest.getDescription(), auctionRequest.getPrice(), auctionRequest.getSection(), auctionRequest.getCategory(), photos, auctionRequest.getParams(), profileRepository.getId(owner));
            return "myAuctions.xhtml";
        }
    }

    public void delete(Long id) {
        List<PhotoEntity> pe = testRepository.getAuctionPhotos(id);

        for(PhotoEntity x : pe) {
            File file = new File("/home/olek/Projects/jazzapp/app/content/auctionPhotos/", x.getUrl());
            file.delete();
        }

        testRepository.deleteAuction(id);
    }

    public void addParam() {
        Map<String, String> asd = auctionRequest.getParams();
        asd.put(auctionRequest.getKey(), auctionRequest.getValue());
        auctionRequest.setParams(asd);

    }

    public List<AuctionEntity> getAuctions() {
        return testRepository.getAuctions();
    }


    public List<AuctionEntity> getMyAuctions() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return testRepository.getMyAuctions(profileRepository.getId((String)session.getAttribute("username")));
    }

    public Boolean getIsUserAdmin() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session.getAttribute("username").equals("admin")) {
            return true;
        }
        return false;
    }
}
