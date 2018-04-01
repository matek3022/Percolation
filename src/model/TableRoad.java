package model;

import java.util.LinkedList;

public class TableRoad {
    private Cluster startCluster;
    private Cluster endCluster;
    private LinkedList<Road> roads;
    /**
     * длина пути
     */
    private int roadLength;
    /**
     * ширина пути
     */
    private int roadWidth;

    public TableRoad(Cluster endCluster, int roadLength) {
        this.endCluster = endCluster;
        this.roadLength = roadLength;
    }

    public void setStartCluster(Cluster cluster) {
        startCluster = cluster;
    }

    public void setRedColor(Table table) {
        if (roads != null) {
            /**
             * окрашивание всех кластеров, кроме последнего
             */
            for (Road road : roads) {
                road.processRoadPoints(table);
                for (Point point : road.getFirst().getPoints()) {
                    processColor(point);
                }
                if (road.getListPoints() != null) {
                    for (Point point : road.getListPoints()) {
                        processColor(point);
                    }
                }
            }
            /**
             * для окрашивания конечного кластера
             */
            for (Point point : roads.getFirst().getSecond().getPoints()) {
                processColor(point);
                roads.getFirst().processRoadPoints(table);
                if (roads.getFirst().getListPoints() != null) {
                    for (Point roadPoint : roads.getFirst().getListPoints()) {
                        processColor(roadPoint);
                    }
                }
            }
        } else {
            /**
             * сюда проваливаемся в случае, когда кластер полностью идет сверху вниз (один)
             */
            if (endCluster != null)
            for (Point point : endCluster.getPoints()) {
                processColor(point);
            }
            if (startCluster != null)
                for (Point point : startCluster.getPoints()) {
                    processColor(point);
                }
        }
    }

    public void processRoads() {
        roads = new LinkedList<>();
        Cluster iterationCluster = endCluster;
        while (iterationCluster != startCluster) {
            roads.add(new Road(iterationCluster.getCurrPrevCluster(), iterationCluster));
            iterationCluster = iterationCluster.getCurrPrevCluster();
        }
    }

    public void processRoadWidth() {
        int left = Integer.MAX_VALUE;
        int right = 0;
        if (roads != null) {
            for (Road road : roads) {
                for (Point point : road.getFirst().getPoints()) {
                    if (point.getCoordX() < left) left = point.getCoordX();
                    if (point.getCoordX() > right) right = point.getCoordX();
                }
                for (Point point : road.getSecond().getPoints()) {
                    if (point.getCoordX() < left) left = point.getCoordX();
                    if (point.getCoordX() > right) right = point.getCoordX();
                }
                for (Point point : road.getListPoints()) {
                    if (point.getCoordX() < left) left = point.getCoordX();
                    if (point.getCoordX() > right) right = point.getCoordX();
                }
            }
        } else {
            if (endCluster != null)
                for (Point point : endCluster.getPoints()) {
                    if (point.getCoordX() < left) left = point.getCoordX();
                    if (point.getCoordX() > right) right = point.getCoordX();
                }
            if (startCluster != null)
                for (Point point : startCluster.getPoints()) {
                    if (point.getCoordX() < left) left = point.getCoordX();
                    if (point.getCoordX() > right) right = point.getCoordX();
                }
        }
        roadWidth = right - left + 1;
    }

    public LinkedList<Road> getRoads() {
        return roads;
    }

    public Cluster getEndCluster() {
        return endCluster;
    }

    public Cluster getStartCluster() {
        return startCluster;
    }

    public int getRoadLength() {
        return roadLength;
    }

    public int getRoadWidth() {
        return roadWidth;
    }

    private void processColor(Point point) {
        if (point.getValue() == Point.BLACK_POINT) {
            point.setValue(Point.GREEN_POINT);
        } else if (point.getValue() == Point.WHITE_POINT) {
            point.setValue(Point.RED_POINT);
        }
    }
}
