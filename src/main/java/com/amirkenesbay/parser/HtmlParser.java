package com.amirkenesbay.parser;

import com.amirkenesbay.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlParser {
    private final Document document;

    private final List<Line> lines;

    private final ContainerStations containerStations;

    private Metro metro;

    public HtmlParser(String url) throws Exception {
        document = getPage(url);
        lines = new ArrayList<>();
        containerStations = new ContainerStations();
    }

    public ContainerStations getContainerStations() {
        return containerStations;
    }

    public List<Line> parseLine() {
        Elements elements = document.getElementsByAttributeValueStarting("class", "js-metro-line t-metrostation-list-header t-icon-metroln ln");
        for (Element element : elements) {
            Line line = new Line(element.attr("data-line"), element.ownText());
            lines.add(line);
        }
        return lines;
    }

    public Map<String, List<String>> parseStation() {
        Elements dataList = document.getElementsByClass("js-metro-stations t-metrostation-list-table");
        Map<String, List<String>> jsonMap = new HashMap<>();
        for (Element element : dataList) {
            List<String> stations = new ArrayList<>();
            Elements stationsList = element.getElementsByClass("name");
            for (Element stationElement : stationsList) {
                stations.add(stationElement.text());
                jsonMap.put(element.attr("data-line"), stations);
                containerStations.addStation(new Station(stationElement.text(), element.attr("data-line")));
            }
        }
        return jsonMap;
    }

    public void parseConnection() {
        Elements dataList = document.getElementsByClass("js-metro-stations t-metrostation-list-table");
        for (Element element : dataList) {
            Elements connectionsList = element.select("p:has(span[title])");
            for (Element connectionElement : connectionsList) {
                String station = connectionElement.text();
                int indexSpace = station.lastIndexOf(";");
                String stationName = station.substring(indexSpace + 1).trim();

                Connections stationsConnection = new Connections();
                stationsConnection.addStation(new Station(stationName, element.attr("data-line")));

                Elements connectionsSpanList = connectionElement.select("span[title]");
                for (Element conSpanElement : connectionsSpanList) {
                    String line = conSpanElement.attr("class");
                    int indexDash = line.lastIndexOf("-");
                    String numberLine = line.substring(indexDash + 1);

                    String text = conSpanElement.attr("title");
                    int indexQuote = text.indexOf("«");
                    int lastIndexQuote = text.lastIndexOf("»");
                    String station1 = text.substring(indexQuote + 1, lastIndexQuote);

                    stationsConnection.addStation(new Station(station1, numberLine));
                }
                containerStations.addConnection(stationsConnection);
            }
        }
    }

    private Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url), 3000);
    }
}
