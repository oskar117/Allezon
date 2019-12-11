package pl.edu.pjwstk.jazapp.entity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Map;

@ApplicationScoped
public class ParameterRepository {

    @PersistenceContext
    private EntityManager em;

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

}
