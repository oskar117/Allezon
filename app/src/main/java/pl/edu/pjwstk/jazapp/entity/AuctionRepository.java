package pl.edu.pjwstk.jazapp.entity;

import jdk.jfr.Percentage;
import pl.edu.pjwstk.jazapp.admin.SectionRequest;
import pl.edu.pjwstk.jazapp.auth.ProfileEntity;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

@ApplicationScoped
public class AuctionRepository {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private PhotoRepository photoRepository;

    @Inject
    private ParameterRepository parameterRepository;

    @Transactional
    public void addAuction(Long editId, String title, String description, String price,Long section, Long category, List<String> photos, Map<String, String> params, Long owner, Long version) {

        CategoryEntity ce = em.getReference(CategoryEntity.class, category);
        SectionEntity se = em.getReference(SectionEntity.class, section);
        ProfileEntity usr = em.getReference(ProfileEntity.class, owner);
        AuctionEntity ae;

        if(editId == null) {
            ae = new AuctionEntity(title, description, Double.parseDouble(price), ce, se, usr);
            em.persist(ae);
        } else {
            ae = em.find(AuctionEntity.class, editId);

            if(version == ae.getVersion()) {
                ae.setTitle(title);
                ae.setDescription(description);
                ae.setPrice(Double.parseDouble(price));
                ae.setCategoryId(ce);
                ae.setSectionId(se);
                em.merge(ae);

                for(var x : ae.getParams()) {
                    if(!params.containsKey(x.getParameterId().getKey())) {
                        parameterRepository.deleteParam(x.getParameterId().getKey());
                    }
                }

                for(var x : ae.getPhotos()) {
                    if(!photos.contains(x.getUrl())) {
                        photoRepository.deletePhoto(x.getUrl());
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("editedMessage", new FacesMessage("Ktoś zedytował już tą aukcję!"));
                return;
            }
        }

        if(photos != null) photoRepository.addPhoto(ae.getId(), photos);
        if(params != null) parameterRepository.addParams(ae.getId(), params);

        List<PhotoEntity> pe = em.createQuery("from PhotoEntity ", PhotoEntity.class).getResultList();


        List<AuctionParameterEntity> ape = em.createQuery("From AuctionParameterEntity ", AuctionParameterEntity.class).getResultList();

        ae.setParams(ape);
        ae.setPhotos(pe);

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
