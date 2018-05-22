package model;

import java.util.ArrayList;

/**
 * класс дорога таблицы (по начальному и конечному кластеру)
 */
public class TableRoad {
    private Cluster startCluster;
    private Cluster endCluster;
    private ArrayList<Road> roads;
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

    /**
     * окрашивание точек таблицы в красный цвет
     * @param table
     */
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
            for (Point point : roads.get(0).getSecond().getPoints()) {
                processColor(point);
                roads.get(0).processRoadPoints(table);
                if (roads.get(0).getListPoints() != null) {
                    for (Point roadPoint : roads.get(0).getListPoints()) {
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

    /**
     * запуск процесса нахождения кратчайших путей внутри кластеров, учавствующих в пути
     * @param table
     */
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
                    if (iter.getSecond().isBottomCluster()) {
                        iter.getSecond().processDeikstraIntoBottomCluster(iter.getIdealSecondPoint(), table);
                    }
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

    /**
     * восстановление дорог между кластерами, принадлежащими пути
     */
    public void processRoads() {
        roads = new ArrayList<>();
        Cluster iterationCluster = endCluster;
        while (iterationCluster != startCluster) {
            roads.add(new Road(iterationCluster.getCurrPrevCluster(), iterationCluster));
            iterationCluster = iterationCluster.getCurrPrevCluster();
        }
    }

    /**
     * процесс просчета параметров пути в таблице (длина и ширина пути, количество красных)
     * @param table
     */
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
        for (ArrayList<Point> row : table.getPoints()) {
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

    public ArrayList<Road> getRoads() {
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

    public void setRedCount(int redCount) {
        this.redCount = redCount;
    }

    public void setRoadLength(int roadLength) {
        this.roadLength = roadLength;
    }

    public void setRoadWidth(int roadWidth) {
        this.roadWidth = roadWidth;
    }

    public int getRoadLength() {
        return roadLength;
    }

    public int getRoadWidth() {
        return roadWidth;
    }

    public float getMiddleRoadLenght() {
        float res = 0f;
        if (roads != null) {
            for (Road road : roads) {
                res += road.getRoadLength();
            }
            return res / roads.size();
        } else return res;
    }

    public int getRoadCount() {
        if (roads != null)
            return roads.size();
        else return 0;
    }

    private void processColor(Point point) {
        if (point.getValue() == Point.WHITE_POINT) {
            point.setValue(Point.RED_POINT);
        }
    }
}
