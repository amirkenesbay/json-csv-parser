package com.amirkenesbay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Station implements Comparable<Station> {

    @JsonProperty
    private String lineNumber;

    @JsonProperty
    @JsonSetter("station_name")
    private String name;

    @JsonProperty
    private String depth;

    @JsonProperty
    private String date;

    @JsonProperty
    private boolean hasConnection;

    public Station(String name) {
        this.name = name;
    }

    public Station() {
    }

    public Station(String name, String lineNumber) {
        this.name = name;
        this.lineNumber = lineNumber;
    }

    public Station(String lineNumber, String name, String depth, String date, boolean hasConnection) {
        this.lineNumber = lineNumber;
        this.name = name;
        this.depth = depth;
        this.date = date;
        this.hasConnection = hasConnection;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    @Override
    public String toString() {
        return "Station{" +
                "lineNumber='" + lineNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Station station) {
        int lineComparison = lineNumber.compareToIgnoreCase(station.getLineNumber());
        if (lineComparison != 0) {
            return lineComparison;
        }
        return name.compareToIgnoreCase(station.getName());
    }
}
