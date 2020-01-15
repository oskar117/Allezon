package pl.edu.pjwstk.jazapp.auction.repositories;

import pl.edu.pjwstk.jazapp.admin.category.CategoryEntity;
import pl.edu.pjwstk.jazapp.admin.section.SectionEntity;
import pl.edu.pjwstk.jazapp.auction.entities.AuctionEntity;
import pl.edu.pjwstk.jazapp.auction.entities.AuctionParameterEntity;
import pl.edu.pjwstk.jazapp.auction.entities.PhotoEntity;
import pl.edu.pjwstk.jazapp.login.auth.ProfileEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AuctionRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long insertAuction(String title, String description, double price, long section, long category, long owner) {
        CategoryEntity ce = em.getReference(CategoryEntity.class, category);
        SectionEntity se = em.getReference(SectionEntity.class, section);
        ProfileEntity usr = em.getReference(ProfileEntity.class, owner);
        AuctionEntity ae = new AuctionEntity(title, description, price, ce, se, usr);
        em.persist(ae);
        return ae.getId();
    }

    @Transactional
    public void addPhotosAndParams(long id, List<PhotoEntity> pe, List<AuctionParameterEntity> ape) {
        AuctionEntity ae = getAuction(id);
        ae.setPhotos(pe);
        ae.setParams(ape);
    }

    @Transactional
    public void mergeAuction(Long editId, String title, String description, double price, long section, long category) {
        CategoryEntity ce = em.getReference(CategoryEntity.class, category);
        SectionEntity se = em.getReference(SectionEntity.class, section);
        AuctionEntity ae = em.find(AuctionEntity.class, editId);
        ae.setTitle(title);
        ae.setDescription(description);
        ae.setPrice(price);
        ae.setCategoryId(ce);
        ae.setSectionId(se);
        em.merge(ae);
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
    public void deleteAuction(Long id) {
        var auction = em.find(AuctionEntity.class, id);
        var params = em.createQuery("From AuctionParameterEntity where auctionId = :xd", AuctionParameterEntity.class).setParameter("xd", auction).getResultList();
        for(var x : params) {
            em.remove(x);
        }
        em.remove(auction);
    }

    public AuctionEntity getAuction(Long editId) {
        var auction = em.find(AuctionEntity.class, editId);
        return auction;
    }

}
