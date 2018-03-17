package model;

import java.util.LinkedList;

/**
 * объект дорога, который характеризует связь между собой 2 точки таблицы
 */
public class Road {
    private Table table;

    /**
     * начальная и конченая точки
     */
    private Point startPoint;
    private Point endPoint;

    /**
     * список прошедших белых точек пути
     */
    private LinkedList<Point> listWhitePoints;

    /**
     * список прошедших красных точек пути
     */
    private LinkedList<Point> listRedPoints;

    private int roadLength;

    public Road(Table table, Point startPoint, Point endPoint) {
        this.table = table;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        processRoad();
    }

    /**
     * вычисляем длину пути
     */
    private void processRoad() {
        int neededStepVertical = Math.abs(endPoint.getCoordY() - startPoint.getCoordY());
        int neededStepHorizontal = Math.abs(endPoint.getCoordX() - startPoint.getCoordX());
        roadLength = neededStepHorizontal + neededStepVertical - 1;
        if (roadLength < 0) roadLength = 0;

    }

    public LinkedList<Point> getListWhitePoints() {
        return listWhitePoints;
    }

    public LinkedList<Point> getListRedPoints() {
        return listRedPoints;
    }

    public int getRoadLength() {
        return roadLength;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}
