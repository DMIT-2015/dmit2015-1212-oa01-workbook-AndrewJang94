package ca.nait.dmit.dmit2015.yjang.assignment04.batch;

import ca.nait.dmit.dmit2015.yjang.assignment04.entity.EdmontonPropertyAssessment;
import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.inject.Named;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@Named
public class EdmontonPropertyAssessmentItemProcessor implements ItemProcessor {

    @Override
    public EdmontonPropertyAssessment processItem(Object item){
        String line = (String) item;
        final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        String[] tokens = line.split(delimiter, -1);    // The -1 limit allows for any number of fields and not discard trailing empty fields);
        EdmontonPropertyAssessment currentEdmontonPropertyAssessment = new EdmontonPropertyAssessment();
        currentEdmontonPropertyAssessment.setAccountNumber(tokens[0]);
        currentEdmontonPropertyAssessment.setSuite(tokens[1]);
        currentEdmontonPropertyAssessment.setHouseNumber(tokens[2]);
        currentEdmontonPropertyAssessment.setStreetName(tokens[3]);
        currentEdmontonPropertyAssessment.setGarage(tokens[4].contains("Y") ? true : false);
        currentEdmontonPropertyAssessment.setNeighbourhoodId(tokens[5].isEmpty() ? null : Integer.parseInt(tokens[5]));
        currentEdmontonPropertyAssessment.setNeighbourhood(tokens[6]);
        currentEdmontonPropertyAssessment.setWard(tokens[7]);
        currentEdmontonPropertyAssessment.setAssessedValue(Integer.parseInt(tokens[8]));
        currentEdmontonPropertyAssessment.setLatitude(Double.parseDouble(tokens[9]));
        currentEdmontonPropertyAssessment.setLongitude(Double.parseDouble(tokens[10]));
        currentEdmontonPropertyAssessment.setAssessmentClass1(tokens[15]);

             /*   String wktText = "POINT" + tokens[6].replaceAll("[\",]","");
                Point geoLocation = (Point) new WKTReader().read(wktText);
                currentEnforcementZoneCentre.setGeoLocation(geoLocation);*/

        Point geoLocation = new GeometryFactory().createPoint(
                new Coordinate(
                        currentEdmontonPropertyAssessment.getLongitude(), currentEdmontonPropertyAssessment.getLatitude()
                )
        );
        currentEdmontonPropertyAssessment.setPointLocation(geoLocation);
        return currentEdmontonPropertyAssessment;
    }
}
