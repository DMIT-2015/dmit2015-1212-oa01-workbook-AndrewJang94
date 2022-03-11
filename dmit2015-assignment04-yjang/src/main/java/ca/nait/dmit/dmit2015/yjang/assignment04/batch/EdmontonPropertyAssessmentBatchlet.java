package ca.nait.dmit.dmit2015.yjang.assignment04.batch;


import ca.nait.dmit.dmit2015.yjang.assignment04.entity.EdmontonPropertyAssessment;
import jakarta.batch.api.AbstractBatchlet;
import jakarta.batch.api.BatchProperty;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.logging.Logger;


/**
 * Batchlets are task oriented step that is called once.
 * It either succeeds or fails. If it fails, it CAN be restarted and it runs again.
 */
@Named("baconAndEggs")
public class EdmontonPropertyAssessmentBatchlet extends AbstractBatchlet {

    @PersistenceContext//(unitName = "mssql-jpa-pu")
    private EntityManager _entityManager;

    @Inject
    private JobContext _jobContext;

    private Logger _logger = Logger.getLogger(EdmontonPropertyAssessmentBatchlet.class.getName());

    @Inject
    @BatchProperty(name = "input_file")
    private String inputFile;

    /**
     * Perform a task and return "COMPLETED" if the job has successfully completed
     * otherwise return "FAILED" to indicate the job failed to complete.
     * If this method throws an exception, the batchlet step ends with a batch status of FAILED.
     */
    @Transactional
    @Override
    public String process() throws Exception {
//        Properties jobParameters = _jobContext.getProperties();
//        String inputFile = jobParameters.getProperty("input_file");

        // For reading external files outside of the project use the code below:
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(inputFile).toFile())))	{
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(_inputFile))))	{
            String line;
            final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
            // Skip the first line as it is containing column headings
            reader.readLine();
            while ((line = reader.readLine()) != null) {
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

                _entityManager.persist(currentEdmontonPropertyAssessment);
            }
        }

        return "COMPLETED";     // The job has successfully completed
    }
}