package com.amirkenesbay.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Metro {
    @JsonProperty
    private Map<String, List<String>> stationMap;
    @JsonProperty
    private List<Line> lines;

    public Metro(Map<String, List<String>> stationMap, List<Line> lines) {
        this.stationMap = stationMap;
        this.lines = lines;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public Map<String, List<String>> getStationMap() {
        return stationMap;
    }

    public void setStationMap(Map<String, List<String>> stationMap) {
        this.stationMap = stationMap;
    }
}
