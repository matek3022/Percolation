package model;

import java.util.LinkedList;

/**
 * объект дорога, который характеризует связь между собой 2 кластера таблицы
 */
public class Road {
    /**
     * первый и второй кластеры
     */
    private Cluster first;
    private Cluster second;

    /**
     * список точек пути
     */
    private LinkedList<Point> listPoints;

    private int roadLength;

    public Road(Cluster first, Cluster second) {
        this.first = first;
        this.second = second;
        processRoad();
    }

    public LinkedList<Point> getListPoints() {
        return listPoints;
    }

    public int getRoadLength() {
        return roadLength;
    }

    public Cluster getFirst() {
        return first;
    }

    public Cluster getSecond() {
        return second;
    }

    /**
     * вычисляем наименьшую длину пути между кластерами
     */
    private void processRoad() {
        Point idealFirstPoint = first.getPoints().getFirst();
        Point idealSecondPoint = second.getPoints().getFirst();
        int currLength = Integer.MAX_VALUE;
        for (Point firstIterator : first.getPoints()) {
            for (Point secondIterator : second.getPoints()) {
                int currIteratorLength = getLength(firstIterator, secondIterator);
                if (currIteratorLength < currLength) {
                    currLength = currIteratorLength;
                    idealFirstPoint = firstIterator;
                    idealSecondPoint = secondIterator;
                }
            }
        }
        roadLength = getLength(idealFirstPoint, idealSecondPoint);
        // TODO: 20.03.2018 заполнить табличку путей из точек idealFirstPoint в idealSecondPoint
    }

    private int getLength(Point first, Point second) {
        int res;
        int neededStepVertical = Math.abs(second.getCoordY() - first.getCoordY());
        int neededStepHorizontal = Math.abs(second.getCoordX() - first.getCoordX());
        res = neededStepHorizontal + neededStepVertical - 1;
        if (res < 0) res = 0;
        return res;
    }
}
