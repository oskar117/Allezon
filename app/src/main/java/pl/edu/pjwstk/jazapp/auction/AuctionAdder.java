package pl.edu.pjwstk.jazapp.auction;

import com.fasterxml.jackson.annotation.JacksonInject;
import pl.edu.pjwstk.jazapp.auth.ProfileRepository;
import pl.edu.pjwstk.jazapp.entity.*;
import pl.edu.pjwstk.jazapp.services.ContextUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class AuctionAdder {

    @Inject
    private AuctionRepository auctionRepository;

    @Inject
    private ParameterRepository parameterRepository;

    @Inject
    private PhotoRepository photoRepository;

    @Inject
    private ContextUtils contextUtils;

    public void addAuction(Long editId, String title, String description, String price, Long section, Long category, List<String> photos, Map<String, String> params, Long owner, Long version) {

        if(editId == null) {
            editId = auctionRepository.insertAuction(title, description, Double.parseDouble(price), section, category, owner);
        } else {

            AuctionEntity ae = auctionRepository.getAuction(editId);

            if(version == ae.getVersion()) {

                auctionRepository.mergeAuction(editId, title, description, Double.parseDouble(price), section, category);

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
                contextUtils.setMessage("msgTest","Ktoś zedytował już tą aukcję!" );
                return;
            }
        }

        AuctionEntity ae = auctionRepository.getAuction(editId);

        if(photos != null) photoRepository.addPhoto(ae.getId(), photos);
        if(params != null) parameterRepository.addParams(ae.getId(), params);

        List<PhotoEntity> pe = photoRepository.getAuctionPhotos(editId);
        List<AuctionParameterEntity> ape = parameterRepository.getAuctionParams(editId);

        auctionRepository.addPhotosAndParams(editId, pe, ape);
    }

}
