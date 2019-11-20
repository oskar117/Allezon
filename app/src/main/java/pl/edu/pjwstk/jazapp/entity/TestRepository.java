package pl.edu.pjwstk.jazapp.entity;

import jdk.jfr.Percentage;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TestRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addSection(String name) {
        SectionEntity se = new SectionEntity(name);
        em.persist(se);
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
