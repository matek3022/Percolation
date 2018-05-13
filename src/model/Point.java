package model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class Point {
    public static final byte GREEN_POINT = 3;
    public static final byte RED_POINT = 2;
    public static final byte WHITE_POINT = 1;
    public static final byte BLACK_POINT = 0;
    /**
     * характеризует тип ячейки
     * 3 - зеленая точка (пройденный путь по кластеру)
     * 2 - крассная точка (добавленный путь)
     * 1 - белая - не дырка
     * 0 - черная - дырка
     * вероятны другие значения в будущем...
     */
    private byte value;
    /**
     * координаты в матрице
     */
    private int x;
    private int y;

    /**
     * точки в общем кластере
     */
    private ArrayList<Point> clusterFriends;

    /**
     * номер кластера
     */
    private int clusterNumber = -1;

    /**
     * вес для деикстры
     */
    private int deikstraValue = Integer.MAX_VALUE;

    public Point(byte value, int coordX, int coordY) {
        this.value = value;
        x = coordX;
        y = coordY;
    }

    public void addClusterFriend(@Nonnull Point friend) {
        if (clusterFriends == null) {
            clusterFriends = new ArrayList<>();
        }
        clusterFriends.add(friend);
    }

    @Nullable
    public ArrayList<Point> getClusterFriends() {
        return clusterFriends;
    }

    public void addClusterFriend(ArrayList<Point> clusterFriends) {
        if (this.clusterFriends == null) {
            this.clusterFriends = new ArrayList<>();
        }
        this.clusterFriends.addAll(clusterFriends);
    }

    public void setClusterFriends(ArrayList<Point> clusterFriends) {
        this.clusterFriends = clusterFriends;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public int getCoordX() {
        return x;
    }

    public int getCoordY() {
        return y;
    }

    public int getClusterNumber() {
        return clusterNumber;
    }

    public int getClusterSize() {
        return clusterFriends == null ? 0 : clusterFriends.size();
    }

    public void setClusterNumber(int clusterNumber) {
        this.clusterNumber = clusterNumber;
    }

    public int getDeikstraValue() {
        return deikstraValue;
    }

    public void setDeikstraValue(int deikstraValue) {
        this.deikstraValue = deikstraValue;
    }
}
