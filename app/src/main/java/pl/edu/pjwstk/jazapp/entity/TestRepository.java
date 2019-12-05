package pl.edu.pjwstk.jazapp.entity;

import jdk.jfr.Percentage;
import pl.edu.pjwstk.jazapp.admin.SectionRequest;
import pl.edu.pjwstk.jazapp.auth.ProfileEntity;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;

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
    public void addAuction(Long editId,String title, String description, String price, Long section, Long category, List<String> photos, Map<String, String> params, Long owner) {
        SectionEntity se = em.getReference(SectionEntity.class, section);
        CategoryEntity ce = em.getReference(CategoryEntity.class, category);
        ProfileEntity usr = em.getReference(ProfileEntity.class, owner);
        AuctionEntity ae;

        if(editId == null) {
            ae = new AuctionEntity(title, description, Double.parseDouble(price), ce, se, usr);
            em.persist(ae);
        } else {
            ae = em.find(AuctionEntity.class, editId);
            ae.setTitle(title);
            ae.setDescription(description);
            ae.setPrice(Double.parseDouble(price));
            ae.setCategoryId(ce);
            ae.setSectionId(se);
            em.merge(ae);
        }
        em.flush();

        if(photos != null) addPhoto(ae.getId(), photos);
        if(params != null) addParams(ae.getId(), params);

        List<PhotoEntity> pe = em.createQuery("from PhotoEntity ", PhotoEntity.class).getResultList();
        List<AuctionParameterEntity> ape = em.createQuery("From AuctionParameterEntity ", AuctionParameterEntity.class).getResultList();

        ae.setParams(ape);
        ae.setPhotos(pe);

        em.merge(ae);
    }

    @Transactional
    public void addParams(Long auctionId, Map<String, String> params) {

        for (var x : params.keySet()) {
            Object qw = em.createQuery("select count(*) from ParameterEntity where key = :usr").setParameter("usr", x).getSingleResult();

            ParameterEntity pe;

            if ((Long) qw == 0) {
                pe = new ParameterEntity(x);
                em.persist(pe);
            } else {
                pe = (ParameterEntity) em.createQuery("from ParameterEntity where key = :usr").setParameter("usr", x).getSingleResult();
            }


            AuctionEntity ae = em.getReference(AuctionEntity.class, auctionId);
            qw = em.createQuery("select count(*) from AuctionParameterEntity where parameterId = :usr and auctionId = :auc").setParameter("usr", pe).setParameter("auc", ae).getSingleResult();

            if((Long) qw == 0) {
                AuctionParameterEntity ape = new AuctionParameterEntity(ae, pe, params.get(x));
                ape.setAuctionParameterId(new AuctionParameterId(auctionId, pe.getId()));
                em.persist(ape);
            } else {
                AuctionParameterEntity ape = (AuctionParameterEntity) em.createQuery("from AuctionParameterEntity where parameterId = :usr and auctionId = :auc").setParameter("usr", pe).setParameter("auc", ae).getSingleResult();
                ape.setValue(params.get(x));
                em.merge(ape);
            }
        }
    }

    @Transactional
    public void deleteParam(String key) {
        var param = em.createQuery("From AuctionParameterEntity where parameterId.key = :key").setParameter("key", key).getSingleResult();
        em.remove(param);
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

    public List<AuctionEntity> getMyAuctions(Long username) {
        ProfileEntity usr = em.getReference(ProfileEntity.class, username);
        List<AuctionEntity> list = em.createQuery("from AuctionEntity where ownerId = :usr", AuctionEntity.class).setParameter("usr", usr).getResultList();
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

    @Transactional
    public void deleteAuction(Long id) {
        var auction = em.find(AuctionEntity.class, id);
        var params = em.createQuery("From AuctionParameterEntity where auctionId = :xd", AuctionParameterEntity.class).setParameter("xd", auction).getSingleResult();
        em.remove(params);
        em.remove(auction);
    }

    public List<PhotoEntity> getAuctionPhotos(Long id) {
        return em.createQuery("from PhotoEntity where auctionId = :id", PhotoEntity.class).setParameter("id", em.getReference(AuctionEntity.class, id)).getResultList();
    }

    public AuctionEntity getAuction(Long editId) {
        var auction = em.find(AuctionEntity.class, editId);
        return auction;
    }

    public SectionEntity getSection(long parseLong) {
        var auction = em.find(SectionEntity.class, parseLong);
        return auction;
    }

    public CategoryEntity getCategory(long parseLong) {
        var auction = em.find(CategoryEntity.class, parseLong);
        return auction;
    }
}
