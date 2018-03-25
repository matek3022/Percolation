package model;

import javax.annotation.Nonnull;
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
    private int clusterCount = 0;

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

    /**
     * массив кластеров таблицы
     */
    private LinkedList<Cluster> clusters;

    /**
     * массив точек последней строки, не включенные в кластеры
     */
    private LinkedList<Point> nonClusterPoints;

    /**
     * белые кластеры из точек последней строки для алгоритма дейкстры
     */
    private LinkedList<Cluster> whiteClusters;

    public Table() {
        n = DEFAULT_N;
        m = DEFAULT_M;
        p = DEFAULT_P;
        generateTable();
        processClusters();
        processRoad();
    }

    public Table(int n, int m, double p) {
        this.n = n;
        this.m = m;
        this.p = p;
        generateTable();
        processClusters();
        processRoad();
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

    public int getClusterCount() {
        return clusterCount;
    }

    private String getNormalizeClusterSize(int clusterSize) {
        if (clusterSize / 10 > 0) return String.valueOf(clusterSize);
        return String.valueOf(clusterSize) + " ";
    }

    public Point getPoint(int x, int y) {
        return points.get(y).get(x);
    }

    private void generateTable() {
        points = new LinkedList<>();
        points.add(new LinkedList<>());
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
            points.add(new LinkedList<Point>());
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
        points.add(new LinkedList<Point>());
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

    private void processRoad() {

    }

    /**
     * просчет кластеров всей губки
     */
    private void processClusters() {
        for (int y = 1; y < m + 1; y++) {
            for (int x = 1; x < n + 1; x++) {
                if (getPoint(x, y).getValue() == Point.BLACK_POINT) {
//                    iter = 0;
                    /**
                     * если у точки ещё не вычислены соседи по кластеру, то запускаем
                     * процедуру кластеризации для этой точки
                     */
                    if (getPoint(x, y).getClusterSize() == 0) {
                        getPoint(x, y).addClusterFriend(currClusterProcess(null, x, y));
                        /**
                         * для ускорения работы нет смысла просчитывать один и тот же кластер
                         * для всех точек в нем, поэтому присваиваем всем точкам в кластере
                         * один и тот же список соседей
                         */
                        for (Point iterator : getPoint(x, y).getClusterFriends()) {
                            iterator.setClusterFriends(getPoint(x, y).getClusterFriends());
                            iterator.setClusterNumber(clusterCount);
                        }
                        clusterCount++;
                        /**
                         * запоминаем кластер
                         */
                        if (clusters == null) clusters = new LinkedList<>();
                        clusters.add(new Cluster(this, getPoint(x, y).getClusterFriends()));
                    }
                }
            }
        }

        /**
         * ищем все точки последней строки, которые не вошли в кластеры
         */
        nonClusterPoints = new LinkedList<>();
        for (int i = 1; i < n + 1; i++) {
            if (points.get(m).get(i).getValue() == Point.WHITE_POINT) {
                nonClusterPoints.add(points.get(m).get(i));
            }
        }

        /**
         * создаем кластеры для белых точек, не вошедших в кластеры (т.к. они белые :))
         */
        whiteClusters = new LinkedList<>();
        for (Point point : nonClusterPoints) {
            whiteClusters.add(new Cluster(this, point));
        }

        /**
         * поиск путей между всеми черными кластерами кластерами, включая пути до белых кластеров
         */
        for (Cluster cluster : clusters) {
            cluster.addRoadsToClusters(clusters);
            cluster.addRoadsToClusters(whiteClusters);
        }

        /**
         * поиск путей между всеми белыми кластерами кластерами, включая пути до черных кластеров
         */
        for (Cluster cluster : whiteClusters) {
            cluster.addRoadsToClusters(clusters);
            cluster.addRoadsToClusters(whiteClusters);
        }
    }

//    private long iter = 0;

    /**
     * Рекурсивная функция просчета соседних точек в кластере по 4м направлениям
     * условие добавления в checkList и результирующий список в том, чтобы
     * проверямая точка была {@link Point#BLACK_POINT}
     *
     * @param checkList список точек, уже учтеных при просчете кластера
     * @param x координата текущей точки по оси Х
     * @param y координата текущей точки по оси У
     * @return список всех элементов в кластере с точкой с координатами (Х, У)
     */
    @Nonnull
    private LinkedList<Point> currClusterProcess(LinkedList<Point> checkList, int x, int y) {

        if (checkList == null) checkList = new LinkedList<>();
        LinkedList<Point> res = new LinkedList<>();

        if (isNewPointInCluster(checkList, getPoint(x, y))) {
            res.add(getPoint(x, y));
            checkList.add(getPoint(x, y));
        }

        /**
         * идем от черной точки по 4м направлениям от текущих координат и смотрим на точки
         * затем добавляем их в результирующий список (res) и общий список проверки уникальности (checkList)
         * для всех итераций рекурсии
         */
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
        /**
         * создаем список крайних точек (от текущей)
         * чтобы запустить процедуру на них, за исключением
         * текущей точки (т.к. мы уже её обработали)
         */
        LinkedList<Point> currRes = new LinkedList<>();
        currRes.addAll(res);
        currRes.remove(getPoint(x, y));
//        /**
//         * просто вывод итераций, чтобы не думать что все повисло в случае
//         * длительных вычислений
//         */
//        System.out.println("Iteration: " + String.valueOf(iter++));
        for (Point curr : currRes) {
            /**
             * запускаем алгоритм на все точки, прилегающие к текущей
             */
            LinkedList<Point> newPoints = currClusterProcess(checkList, curr.getCoordX(), curr.getCoordY());

            /**
             * точка выхода из итеративного алгоритма, дописываем в результирующий список
             * все точки, просчитанные в предыдущих итерациях
             */
            for (Point newCurr : newPoints) {
                if (isNewPointInCluster(res, newCurr)) {
                    res.add(newCurr);
                }
            }
        }
        return res;
    }

    /**
     * процедура для проверки вхождения точки в список
     * @param cluster список подлежащий проверке
     * @param point точка, которую планируется внести в список
     * @return true если точки нет в списке, если присутствует то false
     */
    private boolean isNewPointInCluster(LinkedList<Point> cluster, Point point) {
        if (cluster == null) return true;
        for (Point curr : cluster) {
            if (curr == point) return false;
        }
        return true;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public double getP() {
        return p;
    }
}
