package model;

import java.util.ArrayList;

/**
 * объект дорога, который характеризует связь между собой 2 кластера таблицы
 * создал {@link utils.Person#SEMENOV}
 */
public class Road {
    /**
     * первый и второй кластеры
     */
    private Cluster first;
    private Cluster second;

    private Point idealFirstPoint;
    private Point idealSecondPoint;

    /**
     * список точек пути
     */
    private ArrayList<Point> listPoints;

    /**
     * кратчайшая длина пути между кластерами (напрямик)
     */
    private int roadLength;


    public Road(Cluster first, Cluster second) {
        this.first = first;
        this.second = second;
        processRoad();
    }

    public ArrayList<Point> getListPoints() {
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
     * построение маршрута между точками кластера
     * создал {@link utils.Person#KOROLEV}
     */
    public void processRoadPoints(Table table) {
        int x = idealFirstPoint.getCoordX() - idealSecondPoint.getCoordX();
        int y = idealFirstPoint.getCoordY() - idealSecondPoint.getCoordY();
        listPoints = new ArrayList<>();
        for (int i = 0; i != x; ) {
            if (x > 0) {
                i++;
            } else {
                i--;
            }
            listPoints.add(table.getPoint(idealFirstPoint.getCoordX() - i, idealFirstPoint.getCoordY()));
        }
        for (int i = 0; i != y; ) {
            if (y > 0) {
                i++;
            } else {
                i--;
            }
            listPoints.add(table.getPoint(idealSecondPoint.getCoordX(), idealSecondPoint.getCoordY() + i));
        }
    }

    /**
     * вычисляем наименьшую длину пути между кластерами
     * создал {@link utils.Person#KOROLEV}
     */
    private void processRoad() {
        idealFirstPoint = first.getPoints().get(0);
        idealSecondPoint = second.getPoints().get(0);
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

    /**
     * @param first
     * @param second
     * @return длину между точками
     * создал {@link utils.Person#KOROLEV}
     */
    private int getLength(Point first, Point second) {
        int res;
        int neededStepVertical = Math.abs(second.getCoordY() - first.getCoordY());
        int neededStepHorizontal = Math.abs(second.getCoordX() - first.getCoordX());
        res = neededStepHorizontal + neededStepVertical - 1;
        if (res < 0) res = 0;
        return res;
    }

    public Point getIdealFirstPoint() {
        return idealFirstPoint;
    }

    public Point getIdealSecondPoint() {
        return idealSecondPoint;
    }
}
