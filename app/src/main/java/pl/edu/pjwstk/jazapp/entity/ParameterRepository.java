package pl.edu.pjwstk.jazapp.entity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ParameterRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insertParam(Long id, Long peid, String x) {
        AuctionEntity ae = em.getReference(AuctionEntity.class, id);
        ParameterEntity pe = em.getReference(ParameterEntity.class, peid);
        AuctionParameterEntity ape = new AuctionParameterEntity(ae, pe, x);
        ape.setAuctionParameterId(new AuctionParameterId(id, pe.getId()));
        em.persist(ape);
    }

    @Transactional
    public void updateParam(Long id, Long peid, String x) {
        AuctionEntity ae = em.getReference(AuctionEntity.class, id);
        ParameterEntity pe = em.getReference(ParameterEntity.class, peid);
        AuctionParameterEntity ape = em.createQuery("from AuctionParameterEntity where parameterId = :usr and auctionId = :auc", AuctionParameterEntity.class).setParameter("usr", pe).setParameter("auc", ae).getSingleResult();
        ape.setValue(x);
        em.merge(ape);
    }

    @Transactional
    public void addParamKey(String val) {
        ParameterEntity param = new ParameterEntity(val);
        em.merge(param);
    }

    public ParameterEntity getParamKey(String x) {
        return em.createQuery("from ParameterEntity where key = :usr", ParameterEntity.class).setParameter("usr", x).getSingleResult();
    }

    public AuctionParameterEntity getParamValue(ParameterEntity pe, AuctionEntity ae) {
        return (AuctionParameterEntity) em.createQuery("from AuctionParameterEntity where parameterId = :usr and auctionId = :auc").setParameter("usr", pe).setParameter("auc", ae).getSingleResult();
    }


    public Long areParamsEmpty(String x) {
        return (Long) em.createQuery("select count(*) from ParameterEntity where key = :usr").setParameter("usr", x).getSingleResult();
    }

    public Long auctionParamsExist(Long id, Long peid) {
        AuctionEntity ae = em.getReference(AuctionEntity.class, id);
        ParameterEntity pe = em.getReference(ParameterEntity.class, peid);
        return (Long) em.createQuery("select count(*) from AuctionParameterEntity where parameterId = :usr and auctionId = :auc").setParameter("usr", pe).setParameter("auc", ae).getSingleResult();
    }

    @Transactional
    public void deleteParam(String key) {
        var param = em.createQuery("From AuctionParameterEntity where parameterId.key = :key").setParameter("key", key).getSingleResult();
        em.remove(param);
    }

    public List<AuctionParameterEntity> getAuctionParams(Long id) {
        return em.createQuery("From AuctionParameterEntity where auctionId.id = :ddd", AuctionParameterEntity.class).setParameter("ddd", id).getResultList();
    }

}
