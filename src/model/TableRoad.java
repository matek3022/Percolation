package model;

public class TableRoad {
    private Cluster startCluster;
    private Cluster endCluster;
    private int roadLength;

    public TableRoad(Cluster endCluster, int roadLength) {
        this.endCluster = endCluster;
        this.roadLength = roadLength;
    }

    public Cluster getEndCluster() {
        return endCluster;
    }

    public int getRoadLength() {
        return roadLength;
    }

    public void setStartCluster(Cluster cluster) {
        startCluster = cluster;
    }

    public void setRedColor() {
        for (Point point : startCluster.getPoints()) {
            processColor(point);
        }
        for (Point point : endCluster.getPoints()) {
            processColor(point);
        }
    }

    private void processColor(Point point) {
        if (point.getValue() == Point.BLACK_POINT) {
            point.setValue(Point.GREEN_POINT);
        } else if (point.getValue() == Point.WHITE_POINT) {
            point.setValue(Point.RED_POINT);
        }
    }
}
