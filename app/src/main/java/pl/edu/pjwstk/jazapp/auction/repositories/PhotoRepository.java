package pl.edu.pjwstk.jazapp.auction.repositories;

import pl.edu.pjwstk.jazapp.auction.entities.AuctionEntity;
import pl.edu.pjwstk.jazapp.auction.entities.PhotoEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PhotoRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addPhoto(Long auctionId, List<String> urls) {
        AuctionEntity ae = em.getReference(AuctionEntity.class, auctionId);

        for(var x : urls) {
            Object qw = em.createQuery("select count(*) from PhotoEntity where url = :usr").setParameter("usr", x).getSingleResult();

            if((Long) qw == 0) {
                PhotoEntity pe = new PhotoEntity(ae, x);
                em.persist(pe);
            }
        }
    }

    @Transactional
    public void deletePhoto(String url) {
        var photo = em.createQuery("From PhotoEntity where url = :url").setParameter("url", url).getSingleResult();
        System.out.println("usuwanko: "+url);
        em.remove(photo);
    }

    public List<PhotoEntity> getAuctionPhotos(Long id) {
        return em.createQuery("from PhotoEntity where auctionId = :id order by id", PhotoEntity.class).setParameter("id", em.getReference(AuctionEntity.class, id)).getResultList();
    }
}
