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

    /**
     * количество красных
     */
    private int redCount;

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

    private void processRoadsInCluster(Table table) {
        if (endCluster == startCluster) {
            endCluster.processDeikstraIntoTopAndBottomCluster(table);
        } else if (endCluster.isTopAndBottomCluster()) {
            endCluster.processDeikstraIntoTopAndBottomCluster(table);
        } else if (startCluster.isTopAndBottomCluster()) {
            startCluster.processDeikstraIntoTopAndBottomCluster(table);
        } else {
            for (int i = roads.size() - 1; i >= 0; i--) {
                Road iter = roads.get(i);
                if (iter.getFirst().isTopCluster()) {
                    iter.getFirst().processDeikstraIntoTopCluster(iter.getIdealFirstPoint());
                } else {
                    if (iter.getSecond().isBottomCluster()) {
                        iter.getSecond().processDeikstraIntoBottomCluster(iter.getIdealSecondPoint(), table);
                    }
                    iter.getFirst().processDeikstraIntoRoad(
                            roads.get(i + 1).getIdealSecondPoint(),
                            iter.getIdealFirstPoint(),
                            true
                    );
                }
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

    public void processRoadParams(Table table) {
        processRoadsInCluster(table);
        int left = Integer.MAX_VALUE;
        int right = 0;
        if (roads != null) {
            for (Road road : roads) {
                for (Point point : road.getFirst().getDeikstraPoints()) {
                    if (point.getCoordX() < left) left = point.getCoordX();
                    if (point.getCoordX() > right) right = point.getCoordX();
                }
                for (Point point : road.getSecond().getDeikstraPoints()) {
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
                for (Point point : endCluster.getDeikstraPoints()) {
                    if (point.getCoordX() < left) left = point.getCoordX();
                    if (point.getCoordX() > right) right = point.getCoordX();
                }
            if (startCluster != null)
                for (Point point : startCluster.getDeikstraPoints()) {
                    if (point.getCoordX() < left) left = point.getCoordX();
                    if (point.getCoordX() > right) right = point.getCoordX();
                }
        }
        roadWidth = right - left + 1;
        roadLength = 0;
        redCount = 0;
        for (LinkedList<Point> row : table.getPoints()) {
            for (Point point : row) {
                if (point.getValue() == Point.RED_POINT) {
                    roadLength++;
                    redCount++;
                }
                if (point.getValue() == Point.GREEN_POINT) {
                    roadLength++;
                }
            }
        }
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

    public int getRedCount() {
        return redCount;
    }

    public int getRoadLength() {
        return roadLength;
    }

    public int getRoadWidth() {
        return roadWidth;
    }

    private void processColor(Point point) {
        if (point.getValue() == Point.WHITE_POINT) {
            point.setValue(Point.RED_POINT);
        }
    }
}
