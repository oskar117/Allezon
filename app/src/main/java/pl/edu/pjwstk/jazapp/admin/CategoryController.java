package pl.edu.pjwstk.jazapp.admin;

import pl.edu.pjwstk.jazapp.entity.CategoryEntity;
import pl.edu.pjwstk.jazapp.entity.TestRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Named
@RequestScoped
public class CategoryController {

    private CategoryRequest categoryRequest;

    @Inject
    private TestRepository testRepository;

    @Inject
    private HttpServletRequest request;

    public CategoryRequest getCategoryRequest() {
        if(categoryRequest == null) {
            categoryRequest = createCategoryRequest();
        }
        return categoryRequest;
    }

    private CategoryRequest createCategoryRequest() {
        if (request.getParameter("id") != null) {
            var id = request.getParameter("id");
            var auction = testRepository.getCategory(Long.parseLong(id));
            return new CategoryRequest(auction.getId(), auction.getName(), auction.getSectionId().getId());
        }
        return new CategoryRequest();
    }

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
        testRepository.editCategory(categoryRequest.getId(), categoryRequest.getName(), categoryRequest.getSection());
        categoryRequest.setName(null);
        return "adminCategory.xhtml";
    }
}
