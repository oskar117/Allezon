package pl.edu.pjwstk.jazapp.rest;

import pl.edu.pjwstk.jazapp.auction.repositories.AuctionRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auction")
public class AuctionResource {

    @Inject
    private AuctionRepository auctionRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getDoor(@PathParam("id") Long auctionId) {
        var doorById = auctionRepository.getAuction(auctionId);
        return Response.ok()
                .entity(doorById)
                .build();
    }
}
