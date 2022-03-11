package ca.nait.dmit.dmit2015.yjang.assignment04.resource;

import ca.nait.dmit.dmit2015.yjang.assignment04.entity.EdmontonPropertyAssessment;
import ca.nait.dmit.dmit2015.yjang.assignment04.repository.EdmontonPropertyAssessmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@ApplicationScoped
@Path("EdmontonPropertyAssessments")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class EdmontonPropertyAssessmentResource {

    @Inject
    private EdmontonPropertyAssessmentRepository _edmontonPropertyAssessmentRepository;

    @GET
    @Path("/query/within")
    public Response within(
            @QueryParam("longitude") double longitude,
            @QueryParam("latitude") double latitude,
            @QueryParam("distance") double distanceMetre
    ) {
        List<EdmontonPropertyAssessment> queryResultList = _edmontonPropertyAssessmentRepository
                .findWithinDistance(longitude, latitude, distanceMetre);
        return Response.ok(queryResultList).build();
    }

    @GET
    @Path("/query/byHouseNumberAndStreetName")
    public Response findByHouseNumberAndStreetName(
            @QueryParam("houseNumber") String houseNumber,
            @QueryParam("streetName") String streetName) {
        EdmontonPropertyAssessment querySingleResult = _edmontonPropertyAssessmentRepository
                .findByHouseNumberAndStreetName(houseNumber, streetName)
                .orElseThrow(NotFoundException::new);
        return Response.ok(querySingleResult).build();
    }

    @GET
    @Path("/query/byNeighbourhood")
    public Response byNeighbourhood(
            @QueryParam("neighbourhood") String neighbourhood) {
        EdmontonPropertyAssessment querySingleResult = _edmontonPropertyAssessmentRepository.ByNeighbourhood(neighbourhood)
                .orElseThrow(NotFoundException::new);
        return Response.ok(querySingleResult).build();
    }


}
