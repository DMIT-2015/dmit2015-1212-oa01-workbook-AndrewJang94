package ca.nait.dmit.service;

import ca.nait.dmit.domain.AlbertaCovid19SummaryData;
import lombok.Getter;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AlbertaCovid19SummaryDataService {

    @Getter
    private List<AlbertaCovid19SummaryData> dataList;

    public AlbertaCovid19SummaryDataService() throws IOException {
        dataList = loadCsvData();
    }

    private List<AlbertaCovid19SummaryData> loadCsvData() throws IOException {
        List<AlbertaCovid19SummaryData> dataList = new ArrayList<>();

        try (var reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/Data/covid-19-alberta-statistics-summary-data.csv")))) {
            final var delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
            String line;
            // Skip the first line as it contains column headings
            reader.readLine();

            var dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(delimiter, -1);
                AlbertaCovid19SummaryData lineData = new AlbertaCovid19SummaryData();
                lineData.setId(Integer.parseInt(values[0].replaceAll("\"", "")));
                lineData.setDateReported(LocalDate.parse(values[1], dataFormatter));
                lineData.setNumberOfLabTests(Integer.parseInt(values[2]));
                lineData.setCumulativeNumberOfLabTests(Integer.parseInt(values[3]));
                lineData.setNumberOfCases(Integer.parseInt(values[4]));
                lineData.setCumulativeNumberOfCases(Integer.parseInt(values[5]));
                lineData.setActiveCases(Integer.parseInt(values[6]));
                lineData.setCurrentlyHospitalized(Integer.parseInt(values[7]));
                lineData.setCurrentlyInICU(Integer.parseInt(values[8]));
                lineData.setCumulativeNumberOfDeaths(Integer.parseInt(values[9]));
                lineData.setNumberOfDeaths(Integer.parseInt(values[10]));
                lineData.setNumberOfVariantsOfConcern(Integer.parseInt(values[11]));
                lineData.setPercentPositivity(Double.parseDouble(values[12]));

                dataList.add(lineData);
            }
        }
        return dataList;
    }





}

