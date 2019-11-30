package pl.edu.pjwstk.jazapp.auction;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class AuctionRequest {

    private String title;
    private String description;
    private Long section;
    private String price;
    private Long category;
    private Part photos;
    private Map<String, String> params;

    private Part testPhoto;

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
