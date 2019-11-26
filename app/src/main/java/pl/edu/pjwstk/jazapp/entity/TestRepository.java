package pl.edu.pjwstk.jazapp.entity;

import jdk.jfr.Percentage;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TestRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addSection(String name) {
        SectionEntity se = new SectionEntity(name);
        em.persist(se);
    }

    @Transactional
    public void addCategory(String name, Long section) {
        SectionEntity se = em.getReference(SectionEntity.class, section);
        CategoryEntity ce = new CategoryEntity(name, se);
        em.persist(ce);
    }

    public Map<Long, String> getSections() {
        Map<Long, String> res = new HashMap<Long, String>();
        List<SectionEntity> list = em.createQuery("from SectionEntity ", SectionEntity.class).getResultList();
        for(SectionEntity x : list) {
            res.put(x.getId(), x.getName());
        }
        return res;
    }

    public List<CategoryEntity> getCategories() {
        List<CategoryEntity> list = em.createQuery("from CategoryEntity ", CategoryEntity.class).getResultList();
        return list;
    }

    @Transactional
    public void deleteSection(Long id) {
        var section = em.find(SectionEntity.class, id);
        em.remove(section);
    }

    @Transactional
    public void deleteCategory(Long id) {
        var category = em.find(CategoryEntity.class, id);
        em.remove(category);
    }

    public SectionEntity findSection(Long id) {
        SectionEntity section = em.find(SectionEntity.class, id);
        return section;
    }

    @Transactional
    public void editSection(Long id, String name) {
        var section = em.find(SectionEntity.class, id);
        em.detach(section);
        section.setName(name);
        em.merge(section);
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


  /*  @Transactional
    public void testPhotos3() {
        SectionEntity se = new SectionEntity("testowy_dzial");
        em.persist(se);
    }

    @Transactional
    public void testPhotos4() {
        SectionEntity se = em.getReference(SectionEntity.class, Long.parseLong("10"));
        CategoryEntity ce = new CategoryEntity("testowa_kategoria", se);
        em.persist(ce);
    }

    @Transactional
    public void testPhotos() {
        SectionEntity se = em.getReference(SectionEntity.class, Long.parseLong("10"));
        CategoryEntity ce = em.getReference(CategoryEntity.class, Long.parseLong("15"));
        AuctionEntity am = new AuctionEntity("super", "auto", 21.37, ce, se);
        em.persist(am);
    }

    public void testPhotos2() {
        List<AuctionEntity> user = em.createQuery("from AuctionEntity", AuctionEntity.class).getResultList();
        for(AuctionEntity x : user) {
            System.out.println(x.getTitle());
        }
    }*/
}
