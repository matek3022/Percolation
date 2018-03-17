package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Table {
    public static final double DEFAULT_P = 0.1d;
    public static final int DEFAULT_N = 60;
    public static final int DEFAULT_M = 60;
    private static final String ANSI_RESET = "\033[0m";
    private static final String ANSI_BLACK = "\033[47m";
    private static final String ANSI_WHITE = "\033[40m";
    private static final String ANSI_RED = "\033[41m";
    private static final String TEXTURE = "  " + ANSI_RESET;

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
    }

    public Table(int n, int m, double p) {
        this.n = n;
        this.m = m;
        this.p = p;
        generateTable();
    }

    public void printTable() {
        for (List<Point> row : points) {
            for (Point point : row) {
                System.out.print(point.getValue() == Point.BLACK_POINT ? ANSI_BLACK + TEXTURE : ANSI_WHITE + TEXTURE);
            }
            System.out.println();
        }
    }

    public Point getPoint(int x, int y) {
        if (x >= n || y >= m) return null;
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
        return new Point((new Random().nextInt(1000) <=  p * 1000)
                ? Point.BLACK_POINT : Point.WHITE_POINT,
                x, y);
    }
    private Point getBlackPoint(int x, int y) {
        return new Point(Point.BLACK_POINT, x, y);
    }
    private Point getWhitePoint(int x, int y) {
        return new Point(Point.WHITE_POINT, x, y);
    }

    private void processClasters() {

    }
}
