package pl.edu.pjwstk.jazapp.auction.services;

import pl.edu.pjwstk.jazapp.auction.entities.AuctionEntity;
import pl.edu.pjwstk.jazapp.auction.entities.AuctionParameterEntity;
import pl.edu.pjwstk.jazapp.auction.entities.ParameterEntity;
import pl.edu.pjwstk.jazapp.auction.entities.PhotoEntity;
import pl.edu.pjwstk.jazapp.auction.repositories.AuctionRepository;
import pl.edu.pjwstk.jazapp.auction.repositories.ParameterRepository;
import pl.edu.pjwstk.jazapp.auction.repositories.PhotoRepository;
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
        if(params != null) addParams(ae.getId(), params);

        List<PhotoEntity> pe = photoRepository.getAuctionPhotos(editId);
        List<AuctionParameterEntity> ape = parameterRepository.getAuctionParams(editId);

        auctionRepository.addPhotosAndParams(editId, pe, ape);
    }

    public void addParams(Long auctionId, Map<String, String> params) {

        for (var x : params.keySet()) {

            if (parameterRepository.areParamsEmpty(x) == 0) {
                parameterRepository.addParamKey(x);
            }
            ParameterEntity pe = parameterRepository.getParamKey(x);

            if(parameterRepository.auctionParamsExist(auctionId, pe.getId()) == 0) {
                parameterRepository.insertParam(auctionId, pe.getId(), params.get(x));
            } else {
                parameterRepository.updateParam(auctionId, pe.getId(), params.get(x));
            }
        }
    }

}
