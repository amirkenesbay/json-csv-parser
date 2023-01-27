package com.amirkenesbay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Line implements Comparable<Line> {

    private String number;
    private String name;


    public Line(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Line{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Line line) {
        return number.compareToIgnoreCase(line.getNumber());
    }
}
