package com.amirkenesbay.model;

import java.util.Objects;
import java.util.TreeSet;

public class Connections implements Comparable<Connections> {
    private final TreeSet<Station> connections;

    public Connections() {
        this.connections = new TreeSet<>();
    }

    public void addStation(Station station) {
        connections.add(station);
    }

    public TreeSet<Station> getConnectionStations() {
        return connections;
    }

    @Override
    public int compareTo(Connections stationsConnection) {
        if (connections.size() == stationsConnection.getConnectionStations().size()) {
            if (connections.containsAll(stationsConnection.getConnectionStations())) {
                return 0;
            } else {
                return -1;
            }
        }
        if (connections.size() < stationsConnection.getConnectionStations().size()) {
            return -1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connections that = (Connections) o;
        return Objects.equals(connections, that.connections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connections);
    }
}
