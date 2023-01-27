package com.amirkenesbay.controller;

import com.amirkenesbay.model.Station;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonCsvSearcher {
    private final Map<String, Station> listStations = new HashMap<>();

    public void fileReader(String path) throws IOException {
        File doc = new File(path);
        if (doc.isFile()) {
            if (doc.getName().endsWith(".json")) {
                getDatesFromJson(doc);
            }
            if (doc.getName().endsWith(".csv")) {
                getDatesFromCsv(doc);
            }
        } else {
            File[] files = doc.listFiles();
            assert files != null;
            for (File file : files) {
                fileReader(file.getAbsolutePath());
            }
        }

    }

    public void getDatesFromJson(File doc) {
        try {
            byte[] mapData = Files.readAllBytes(Paths.get(doc.getAbsolutePath()));
            ObjectMapper mapper = new ObjectMapper();

            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<Station> myObjects = mapper.readValue(mapData, new TypeReference<>() {
            });

            for (Station st : myObjects) {
                String stationName = st.getName();
                if (!listStations.containsKey(stationName)) {
                    listStations.put(st.getName(), new Station(st.getName()));
                }
                if (doc.getName().startsWith("depth")) {
                    String depth = st.getDepth();
                    listStations.get(stationName).setDepth(depth);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void getDatesFromCsv(File doc) throws FileNotFoundException {
        String filePath = doc.getAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try {
            String splitBy = ",";
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(splitBy, 2);
                for (int i = 0; i < lines.length; i++) {
                    if (i % 2 == 0) {
                        String stationName = lines[i];
                        if (!listStations.containsKey(stationName)) {
                            listStations.put(stationName, new Station(stationName));
                        }
                        if (doc.getName().startsWith("dates")) {
                            listStations.get(stationName).setDate(lines[i + 1]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Station> getListStations() {
        return listStations;
    }
}
