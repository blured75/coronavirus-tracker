package com.blured.coronavirustracker.services;

import com.blured.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron="* 30 * * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());

        // Line CSV
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setState(record.get("Province/State"));

            // 0 -> D, 1 -> D-1, 2 -> D-2, 3 -> D-3 ...
            List<Integer> llNewCasesPerDay = new ArrayList<>();
            // 0 -> D - D-1, 1 -> D-1 - D-2 ...
            List<Integer> llDeltaCasesDayBefore = new ArrayList<>();

            int recordSize = record.size();

            // For each column with dimension
            for (int idxRecord = recordSize - 1; idxRecord >= 4 ; idxRecord--) {
                int newCasesByDay = Integer.parseInt(record.get(idxRecord));
                int newCasesByDayBefore = idxRecord == 4 ? 0 : Integer.parseInt(record.get(idxRecord - 1));
                int deltaCasesDayBefore = Integer.parseInt(record.get(idxRecord)) - newCasesByDayBefore;
                llNewCasesPerDay.add(newCasesByDay);

                llDeltaCasesDayBefore.add(deltaCasesDayBefore);

                if (idxRecord == recordSize - 91) break;
            }

            locationStats.setNewCasesPerDay(llNewCasesPerDay);
            locationStats.setDeltaCasesDayBefore(llDeltaCasesDayBefore);

            int lastTotalCases = Integer.parseInt(record.get(record.size() - 1));
            locationStats.setLatestTotalCases(lastTotalCases);
            newStats.add(locationStats);
        }

        this.allStats = newStats;
    }

    public List<LocationStats> getAllStats() {
        return allStats;
    }
}
