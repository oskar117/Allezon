package pl.edu.pjwstk.jazapp.admin;

import pl.edu.pjwstk.jazapp.entity.CategoryEntity;
import pl.edu.pjwstk.jazapp.entity.TestRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.List;

@Named
@RequestScoped
public class CategoryController {

    @Inject
    private CategoryRequest categoryRequest;

    @Inject
    private TestRepository testRepository;

    public void addSection() {
        testRepository.addCategory(categoryRequest.getName(), categoryRequest.getSection());
        categoryRequest.setName(null);
    }

    public List<CategoryEntity> getCategories() {
        return testRepository.getCategories();

    }

    public void delete(Long id) {
        testRepository.deleteCategory(id);
    }

    public String edit() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        testRepository.editCategory((Long) session.getAttribute("editId"), categoryRequest.getName(), categoryRequest.getSection());
        categoryRequest.setName(null);
        return "adminCategory.xhtml";
    }
}
