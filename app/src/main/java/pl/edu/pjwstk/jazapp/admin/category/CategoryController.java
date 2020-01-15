package pl.edu.pjwstk.jazapp.admin.category;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Named
@RequestScoped
public class CategoryController {

    private CategoryRequest categoryRequest;

    @Inject
    private CategoryRepository categoryRepository;

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
            var auction = categoryRepository.getCategory(Long.parseLong(id));
            return new CategoryRequest(auction.getId(), auction.getName(), auction.getSectionId().getId());
        }
        return new CategoryRequest();
    }

    public void addSection() {
        categoryRepository.addCategory(categoryRequest.getName(), categoryRequest.getSection());
        categoryRequest.setName(null);
    }

    public List<CategoryEntity> getCategories() {
        return categoryRepository.getCategories();

    }

    public void delete(Long id) {
        categoryRepository.deleteCategory(id);
    }

    public String edit() {
        categoryRepository.editCategory(categoryRequest.getId(), categoryRequest.getName(), categoryRequest.getSection());
        categoryRequest.setName(null);
        return "adminCategory.xhtml";
    }
}
