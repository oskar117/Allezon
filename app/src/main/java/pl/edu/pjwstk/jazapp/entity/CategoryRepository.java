package pl.edu.pjwstk.jazapp.entity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CategoryRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addCategory(String name, Long section) {
        SectionEntity se = em.getReference(SectionEntity.class, section);
        CategoryEntity ce = new CategoryEntity(name, se);
        em.persist(ce);
    }

    @Transactional
    public void deleteCategory(Long id) {
        var category = em.find(CategoryEntity.class, id);
        em.remove(category);
    }

    @Transactional
    public void editCategory(Long id, String name, Long sectionId) {
        var category = em.find(CategoryEntity.class, id);
        var section = em.find(SectionEntity.class, sectionId);
        em.detach(section);
        category.setName(name);
        category.setSectionId(section);
        em.merge(section);
    }

    public CategoryEntity getCategory(long parseLong) {
        var auction = em.find(CategoryEntity.class, parseLong);
        return auction;
    }

    public List<CategoryEntity> getCategories() {
        List<CategoryEntity> list = em.createQuery("from CategoryEntity ", CategoryEntity.class).getResultList();
        return list;
    }
}
