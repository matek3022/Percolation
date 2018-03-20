package model;

import java.util.LinkedList;

public class Cluster {
    /**
     * точки кластера
     */
    private LinkedList<Point> points;

    /**
     * кратчайшие пути до других кластеров
     */
    private LinkedList<Road> roads;
    /**
     * этот кластер есть в первой строке
     */
    private boolean isTopCluster = false;
    /**
     * этот кластер есть в последней строке
     */
    private boolean isBottomCluster = false;
    /**
     * этот кластер имеет полный путь с 1 до последней строки
     */
    private boolean isTopAndBottomCluster = false;

    public Cluster(Table table, LinkedList<Point> points) {
        this.points = points;
        processTopAndBottom(table);
    }

    private void processTopAndBottom(Table table) {
        for (Point iterator : points) {
            if (iterator.getCoordY() == 1) {
                isTopCluster = true;
            }
            if (iterator.getCoordY() == table.getM()) {
                isBottomCluster = true;
            }
        }
        isTopAndBottomCluster = isTopCluster && isBottomCluster;
    }

    /**
     * добавляем путь из this кластера до cluster, если он не текущий
     * @param cluster
     */
    public void addRoadToCluster(Cluster cluster) {
        if (roads == null) roads = new LinkedList<>();
        if (this != cluster) {
            roads.add(new Road(this, cluster));
        }
    }

    /**
     * Добавляем пути из this кластера до всех кластеров списка
     * @param clusters
     */
    public void addRoadsToClusters(LinkedList<Cluster> clusters) {
        for (Cluster iterator : clusters) {
            addRoadToCluster(iterator);
        }
    }

    public LinkedList<Point> getPoints() {
        return points;
    }

    public void setPoints(LinkedList<Point> points) {
        this.points = points;
    }

    public LinkedList<Road> getRoads() {
        return roads;
    }

    public boolean isTopCluster() {
        return isTopCluster;
    }

    public boolean isBottomCluster() {
        return isBottomCluster;
    }

    public boolean isTopAndBottomCluster() {
        return isTopAndBottomCluster;
    }
}
