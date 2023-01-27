package com.amirkenesbay;

import com.amirkenesbay.controller.JsonCsvSearcher;
import com.amirkenesbay.model.*;
import com.amirkenesbay.parser.HtmlParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

public class Main {
    private static final String SEPARATOR = File.separator;
    private static final String PATH_TO_DATA = "src" + SEPARATOR + "main" + SEPARATOR + "resources" + SEPARATOR;
    private static final String PATH_TO_SERIALIZED_DATA = "src" + SEPARATOR + "main" + SEPARATOR + "resources" + SEPARATOR + "serialized-files" + SEPARATOR;

    public static void main(String[] args) throws Exception {
        HtmlParser parser = new HtmlParser("https://skillbox-java.github.io/");
        Map<String, List<String>> stations = parser.parseStation();
        List<Line> lines = parser.parseLine();
        Metro metro = new Metro(stations, lines);
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(new File(PATH_TO_SERIALIZED_DATA + "map.json"), metro);

        parser.parseStation();
        parser.parseConnection();
        JsonCsvSearcher searcher = new JsonCsvSearcher();
        TreeSet<Connections> connections = parser.getContainerStations().getConnections();
        searcher.fileReader(PATH_TO_DATA + "data-5");

        Map<String, Station> stationMap = searcher.getListStations();
        List<Station> stationList = parser.getContainerStations().getStations();

        setParameterHasConnection(connections, stationMap);
        setParameterLineName(stationList, lines, stationMap);

        Map<String, List<Map<String, Object>>> stationObject = new HashMap<>();
        List<Map<String, Object>> stationArray = new ArrayList<>();
        for (Map.Entry<String, Station> entry : stationMap.entrySet()) {
            Map<String, Object> station = new HashMap<>();
            if (entry.getValue().getName() != null) {
                station.put("name", entry.getValue().getName());
            }
            if (entry.getValue().getLineNumber() != null) {
                station.put("line", entry.getValue().getLineNumber());
            }
            if (entry.getValue().getDate() != null) {
                station.put("date", entry.getValue().getDate());
            }
            if (entry.getValue().getDepth() != null) {
                station.put("depth", entry.getValue().getDepth());
            }
            station.put("hasConnection", entry.getValue().isHasConnection());
            stationArray.add(station);
        }
        stationObject.put("stations", stationArray);
        mapper.writeValue(new File(PATH_TO_SERIALIZED_DATA + "stations.json"), stationObject);
    }

    private static void setParameterHasConnection(TreeSet<Connections> connections, Map<String, Station> stationMap) {
        stationMap.keySet().forEach(k -> {
            for (Connections connection : connections) {
                for (Station station : connection.getConnectionStations()) {
                    if (station.getName().equals(k)) {
                        stationMap.get(k).setHasConnection(true);
                    }
                }
            }
        });
    }

    private static void setParameterLineName(List<Station> stations, List<Line> lines, Map<String, Station> listStations) {
        listStations.keySet().forEach(k -> {
            for (Station station : stations) {
                if (station.getName().equals(k)) {
                    for (Line line : lines) {
                        if (line.getNumber().equals(station.getLineNumber())) {
                            listStations.get(k).setLineNumber(line.getName());
                        }
                    }
                }
            }
        });
    }
}