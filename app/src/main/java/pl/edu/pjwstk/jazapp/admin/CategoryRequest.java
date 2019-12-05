package pl.edu.pjwstk.jazapp.admin;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class CategoryRequest {

    private Long id;
    private String name;
    private Long section;

    public CategoryRequest() {
    }

    public CategoryRequest(Long id, String name, Long section) {
        this.id = id;
        this.name = name;
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSection() {
        return section;
    }

    public void setSection(Long section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
