package pl.edu.pjwstk.jazapp.auction;

import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.entity.AuctionEntity;
import pl.edu.pjwstk.jazapp.entity.PhotoEntity;
import pl.edu.pjwstk.jazapp.entity.PhotoRepository;
import pl.edu.pjwstk.jazapp.entity.AuctionRepository;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class AuctionController implements Serializable {

    @Inject
    private AuctionRepository auctionRepository;

    private AuctionRequest auctionRequest;

    @Inject
    private PhotoRepository photoRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private HttpServletRequest request;

    public Collection<Part> getAllParts(Part part) throws ServletException, IOException {
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
            var auction = auctionRepository.getAuction(Long.parseLong(id));
            Map<String, String> params = new LinkedHashMap<String, String>();
            for(var x : auction.getParams()) {
                params.put(x.getParameterId().getKey(), x.getValue());
            }
            List<String> photos = new LinkedList<String>();
            for(var x : auction.getPhotos()) {
                photos.add(x.getUrl());
            }
            return new AuctionRequest(auction.getId(), auction.getTitle(), auction.getDescription(), auction.getSectionId().getId(), String.valueOf(auction.getPrice()), auction.getCategoryId().getId(), params, photos);
        }
        return new AuctionRequest();
    }

    public String add() throws ServletException, IOException {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String owner = (String) session.getAttribute("username");
        auctionRepository.addAuction(auctionRequest.getId(), auctionRequest.getTitle(), auctionRequest.getDescription(), auctionRequest.getPrice(), auctionRequest.getSection(), auctionRequest.getCategory(), auctionRequest.getPhotos2(), auctionRequest.getParams(), profileRepository.getId(owner));
        auctionRequest = null;
        return "myAuctions.xhtml";
    }

    public void delete(Long id) {
        List<PhotoEntity> pe = photoRepository.getAuctionPhotos(id);

        for(PhotoEntity x : pe) {
            File file = new File("/home/olek/Projects/jazzapp/app/content/auctionPhotos/", x.getUrl());
            file.delete();
        }

        auctionRepository.deleteAuction(id);
    }

    public void addParam() {
        Map<String, String> asd = auctionRequest.getParams();
        asd.put(auctionRequest.getKey().substring(0, 1).toUpperCase() + auctionRequest.getKey().substring(1), auctionRequest.getValue().substring(0, 1).toUpperCase() + auctionRequest.getValue().substring(1));
        auctionRequest.setParams(asd);

    }

    public void addPhotos() throws ServletException, IOException {
        List<String> asd = auctionRequest.getPhotos2();

        if(asd == null) {
            asd = new LinkedList<String>();
        }

        Random random = new Random();
        for(Part x : getAllParts(auctionRequest.getPhotos())){
            try (InputStream input = x.getInputStream()) {
                String url = random.nextInt()+".jpg";
                Files.copy(input, new File("/home/olek/Projects/jazzapp/app/content/auctionPhotos", url).toPath());
                asd.add(url);
            }
            catch (IOException e) {
                System.out.println("error: " + e.getMessage());        }

        }

        auctionRequest.setPhotos2(asd);
    }

    public void deleteParam(String key) {
        Map<String, String> asd = auctionRequest.getParams();
        asd.remove(key);
        auctionRequest.setParams(asd);
    }

    public void deletePhoto(String url) {
        List<String> photos = auctionRequest.getPhotos2();
        photos.remove(url);
        auctionRequest.setPhotos2(photos);
    }

    public List<AuctionEntity> getAuctions() {
        return auctionRepository.getAuctions();
    }


    public List<AuctionEntity> getMyAuctions() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return auctionRepository.getMyAuctions(profileRepository.getId((String)session.getAttribute("username")));
    }

    public Boolean getIsUserAdmin() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session.getAttribute("username").equals("admin")) {
            return true;
        }
        return false;
    }
}
