package model;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Table {
    public static final double DEFAULT_P = 0.4d;
    public static final int DEFAULT_N = 60;
    public static final int DEFAULT_M = 60;
    private static final String ANSI_RESET = "\033[0m";
    private static final String ANSI_BLACK = "\033[47m";
    private static final String ANSI_WHITE = "\033[40m";
    private static final String ANSI_RED = "\033[41m";
    private static final String TEXTURE = "  " + ANSI_RESET;
    /**
     * количство кластеров
     */
    private int clasterCount;

    /**
     * ширина губки
     */
    private int n;
    /**
     * высота губки
     */
    private int m;
    /**
     * вероятность появления дырок
     */
    private double p;

    /**
     * матрица точек
     */
    private List<List<Point>> points;

    public Table() {
        n = DEFAULT_N;
        m = DEFAULT_M;
        p = DEFAULT_P;
        generateTable();
        processClusters();
    }

    public Table(int n, int m, double p) {
        this.n = n;
        this.m = m;
        this.p = p;
        generateTable();
        processClusters();
    }

    public void printTable(boolean withClusterSize) {
        for (List<Point> row : points) {
            for (Point point : row) {
                System.out.print(point.getValue() == Point.BLACK_POINT
                        ? ANSI_BLACK + (withClusterSize ? getNormalizeClusterSize(point.getClusterSize()) : TEXTURE)
                        : point.getValue() == Point.WHITE_POINT ? ANSI_WHITE + TEXTURE : ANSI_RED + TEXTURE);
            }
            System.out.println();
        }
    }

    private String getNormalizeClusterSize(int clusterSize) {
        if (clusterSize / 10 > 0) return String.valueOf(clusterSize);
        return String.valueOf(clusterSize) + " ";
    }

    public Point getPoint(int x, int y) {
        return points.get(y).get(x);
    }

    private void generateTable() {
        points = new ArrayList<>();
        points.add(new ArrayList<>());
        /**
         * добавляем белую строку в начале
         */
        for (int i = 0; i < m + 2; i++) {
            points.get(0).add(getWhitePoint(i, 0));
        }
        /**
         * бежим по строкам
         */
        for (int i = 1; i < m + 1; i++) {
            points.add(new ArrayList<Point>());
            /**
             * добавляем белую точку в начале строки
             */
            points.get(i).add(getWhitePoint(0, i));
            /**
             * бежим по столбцам
             */
            for (int j = 1; j < n + 1; j++) {
                points.get(i).add(getPointForGenerateTable(j, i));
            }
            /**
             * добавляем белую точку в конце строки
             */
            points.get(i).add(getWhitePoint(points.get(i).size() - 1, i));
        }
        /**
         * добавляем белую строку в конце
         */
        points.add(new ArrayList<Point>());
        for (int i = 0; i < m + 2; i++) {
            points.get(points.size() - 1).add(getWhitePoint(i, points.size() - 1));
        }
    }

    private Point getPointForGenerateTable(int x, int y) {
        return new Point((new Random().nextInt(1000) <= p * 1000)
                ? Point.BLACK_POINT : Point.WHITE_POINT,
                x, y);
    }

    private Point getBlackPoint(int x, int y) {
        return new Point(Point.BLACK_POINT, x, y);
    }

    private Point getWhitePoint(int x, int y) {
        return new Point(Point.WHITE_POINT, x, y);
    }

    private void processClusters() {
        for (int y = 1; y < m + 1; y++) {
            for (int x = 1; x < n + 1; x++) {
                if (getPoint(x, y).getValue() == Point.BLACK_POINT) {
                    iter = 0;
                    getPoint(x, y).addClusterFriend(currClusterProcess(null, x, y));
                }
            }
        }
    }

    private long iter = 0;
    @Nonnull
    private LinkedList<Point> currClusterProcess(LinkedList<Point> checkList, int x, int y) {
        /**
         * идем от черной точки по 4м направлениям
         */
        if (checkList == null) checkList = new LinkedList<>();
        LinkedList<Point> res = new LinkedList<>();

        if (isNewPointInCluster(checkList, getPoint(x, y))) {
            res.add(getPoint(x, y));
            checkList.add(getPoint(x, y));
        }

        if (getPoint(x - 1, y).getValue() == Point.BLACK_POINT) {//влево
            if (isNewPointInCluster(checkList, getPoint(x - 1, y))) {
                res.add(getPoint(x - 1, y));
                checkList.add(getPoint(x - 1, y));
            }
        }
        if (getPoint(x + 1, y).getValue() == Point.BLACK_POINT) {//вправо
            if (isNewPointInCluster(checkList, getPoint(x + 1, y))) {
                res.add(getPoint(x + 1, y));
                checkList.add(getPoint(x + 1, y));
            }
        }
        if (getPoint(x, y - 1).getValue() == Point.BLACK_POINT) {//вверх
            if (isNewPointInCluster(checkList, getPoint(x, y - 1))) {
                res.add(getPoint(x, y - 1));
                checkList.add(getPoint(x, y - 1));
            }
        }
        if (getPoint(x, y + 1).getValue() == Point.BLACK_POINT) {//вниз
            if (isNewPointInCluster(checkList, getPoint(x, y + 1))) {
                res.add(getPoint(x, y + 1));
                checkList.add(getPoint(x, y + 1));
            }
        }
        LinkedList<Point> currRes = new LinkedList<>();
        currRes.addAll(res);
        currRes.remove(getPoint(x, y));
        System.out.println("Iteration: " + String.valueOf(iter++));
        for (Point curr : currRes) {
            LinkedList<Point> newPoints = currClusterProcess(checkList, curr.getCoordX(), curr.getCoordY());
            for (Point newCurr : newPoints) {
                if (isNewPointInCluster(res, newCurr)) {
                    res.add(newCurr);
                }
            }
        }
        return res;
    }

    private boolean isNewPointInCluster(LinkedList<Point> cluster, Point point) {
        if (cluster == null) return true;
        for (Point curr : cluster) {
            if (curr == point) return false;
        }
        return true;
    }
}
