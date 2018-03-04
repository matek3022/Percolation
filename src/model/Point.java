package model;

import javafx.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;

public class Point {
    /**
     * характеризует тип ячейки
     * 0 - белая
     * 1 - черная
     * вероятны другие значения в будущем...
     */
    private byte value;
    /**
     * координаты в матрице
     */
    private Pair<Integer, Integer> coord;

    /**
     * точки в общем кластере
     */
    private LinkedList<Point> clusterFriends;

    public Point(byte value, @Nonnull Pair<Integer, Integer> coord) {
        this.value = value;
        this.coord = coord;
    }

    public Point(byte value, int coordX, int coordY) {
        this.value = value;
        this.coord = new Pair<>(coordX, coordY);
    }

    public void addClasterFriend(@Nonnull Point friend) {
        if (clusterFriends == null) {
            clusterFriends = new LinkedList<>();
        }
        clusterFriends.add(friend);
    }

    @Nullable
    public LinkedList<Point> getClusterFriends() {
        return clusterFriends;
    }

    public byte getValue() {
        return value;
    }

    public Pair<Integer, Integer> getCoord() {
        return coord;
    }

    public int getCoordX() {
        return coord.getKey();
    }

    public int getCoordY() {
        return coord.getValue();
    }
}
