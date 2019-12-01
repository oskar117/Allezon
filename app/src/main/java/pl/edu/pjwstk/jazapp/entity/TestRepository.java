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

    @Transactional
    public void addAuction(String title, String description, String price, Long section, Long category, List<String> photos, Map<String, String> params) {
        SectionEntity se = em.getReference(SectionEntity.class, section);
        CategoryEntity ce = em.getReference(CategoryEntity.class, category);
        AuctionEntity ae = new AuctionEntity(title, description, Double.parseDouble(price), ce, se);
        em.persist(ae);
        em.flush();

        addPhoto(ae.getId(), photos);
        addParams(ae.getId(), params);

        List<PhotoEntity> pe = em.createQuery("from PhotoEntity ", PhotoEntity.class).getResultList();

        em.detach(ae);
        ae.setPhotos(pe);
        em.merge(ae);
    }

    @Transactional
    public void addParams(Long auctionId, Map<String, String> params) {

        List<ParameterEntity> paramKeys = em.createQuery("from ParameterEntity ", ParameterEntity.class).getResultList();


        for(var x : params.keySet()) {
            Object qw = em.createQuery("select count(*) from ParameterEntity where key = :usr").setParameter("usr", x).getSingleResult();

            ParameterEntity pe;

            if((Long) qw == 0) {
                pe = new ParameterEntity(x);
                em.persist(pe);
                em.flush();
            } else {
                pe = (ParameterEntity) em.createQuery("from ParameterEntity where key = :usr").setParameter("usr", x).getSingleResult();
            }

            AuctionEntity ae = em.getReference(AuctionEntity.class, auctionId);
            AuctionParameterEntity ape = new AuctionParameterEntity(ae, pe, params.get(x));
            ape.setAuctionParameterId(new AuctionParameterId(1L, pe.getId()));
            em.persist(ape);
        }

    }

    @Transactional
    public void addPhoto(Long auctionId, List<String> urls) {
        AuctionEntity ae = em.getReference(AuctionEntity.class, auctionId);
        for(var x : urls) {
            PhotoEntity pe = new PhotoEntity(ae, x);
            em.persist(pe);
        }
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

    public List<AuctionEntity> getAuctions() {
        List<AuctionEntity> list = em.createQuery("from AuctionEntity ", AuctionEntity.class).getResultList();
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
