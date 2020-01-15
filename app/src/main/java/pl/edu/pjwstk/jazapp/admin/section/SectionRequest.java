package pl.edu.pjwstk.jazapp.admin.section;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.Map;

@Named
@RequestScoped
public class SectionRequest {

    private Long id;
    private String name;

    public SectionRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SectionRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
