package pl.edu.pjwstk.jazapp.auction;

import pl.edu.pjwstk.jazapp.entity.AuctionEntity;
import pl.edu.pjwstk.jazapp.entity.TestRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class AuctionRequest {

    private Long id;
    private String title;
    private String description;
    private Long section;
    private String price;
    private Long category;
    private Part photos;
    private Map<String, String> params;
    private String key;
    private String value;

    public AuctionRequest(Long id, String title, String description, Long section, String price, Long category, Map<String, String> params) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.section = section;
        this.price = price;
        this.category = category;
        this.params = params;
    }

    public AuctionRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSection() {
        return section;
    }

    public void setSection(Long section) {
        this.section = section;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Part getPhotos() {
        return photos;
    }

    public void setPhotos(Part photos) {
        this.photos = photos;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

}
