package model;

import utils.Setup;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.Point.*;

/**
 * класс таблица
 */
public class Table {
    public static final double DEFAULT_P = 0.4d;
    public static final int DEFAULT_N = 75;
    public static final int DEFAULT_M = 75;
    private static final String ANSI_RESET = "\033[0m";
    private static final String ANSI_BLACK = "\033[47m";
    private static final String ANSI_WHITE = "\033[40m";
    private static final String ANSI_RED = "\033[41m";
    private static final String ANSI_GREEN = "\033[42m";
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
    private ArrayList<ArrayList<Point>> points;

    /**
     * массив кластеров таблицы
     */
    private ArrayList<Cluster> clusters;

    /**
     * массив точек последней строки, не включенные в кластеры
     */
    private ArrayList<Point> nonClusterPoints;

    /**
     * белые кластеры из точек последней строки для алгоритма дейкстры
     */
    private ArrayList<Cluster> whiteClusters;

    /**
     * кратчайший путь
     */
    private TableRoad minTableRoad = new TableRoad(null, Integer.MAX_VALUE);

    public Table() {
        n = DEFAULT_N;
        m = DEFAULT_M;
        p = DEFAULT_P;
        init(true);
    }

    public Table(int m, int n, double p) {
        this.n = n;
        this.m = m;
        this.p = p;
        init(true);
    }

    public Table(ArrayList<ArrayList<Point>> points) {
        p = 0;
        m = points.size() - 2;
        n = points.get(0).size() - 2;
        this.points = points;
        init(false);
    }

    private void init(boolean generate) {
        clusters = new ArrayList<>();
        nonClusterPoints = new ArrayList<>();
        whiteClusters = new ArrayList<>();
        if (generate) generateTable();
        processClusters();
        processRoads();
        minTableRoad.setRedColor(this);
        if (Setup.WITH_LENGTH_WIDTH_AND_RED_COUNT) {
            minTableRoad.processRoadParams(this);
        } else {
            minTableRoad.setRedCount(0);
            minTableRoad.setRoadLength(0);
            minTableRoad.setRoadWidth(0);
        }
    }

    /**
     * печать таблицы в консоль с окрашиваниями в соответствующие цвета,
     * принимает параметр - флаг, говорящий о том нужно ли выводить индекс кластера
     * для соответствующей точки
     * @param withClusterNumber
     */
    public void printTable(boolean withClusterNumber) {
        for (List<Point> row : points) {
            for (Point point : row) {
                String text = "";
                switch (point.getValue()) {
                    case WHITE_POINT:
                        text = ANSI_WHITE + TEXTURE;
                        break;
                    case BLACK_POINT:
                        text = ANSI_BLACK + (withClusterNumber ? getNormalizeClusterNumber(point) : TEXTURE);
                        break;
                    case RED_POINT:
                        text = (Setup.SHOW_WITH_ROAD ? ANSI_RED : ANSI_WHITE) + TEXTURE;
                        break;
                    case GREEN_POINT:
                        text = (Setup.SHOW_WITH_ROAD ? ANSI_GREEN : ANSI_BLACK) + (withClusterNumber ? getNormalizeClusterNumber(point) : TEXTURE);
                        break;
                }
                System.out.print(text);
            }
            System.out.println();
        }
    }

    /**
     *
     * @return количество кластеров
     */
    public int getClusterCount() {
        return clusterCount;
    }

    /**
     *
     * @param point
     * @return строку для вставки в консоль для нормальной отрисовки (чтобы не ехала картинка и все выглядело целостно)
     */
    private String getNormalizeClusterNumber(Point point) {
        if (point.getClusterNumber() / 100 > 0) return "XX";
        if (point.getClusterNumber() / 10 > 0) return String.valueOf(point.getClusterNumber());
        return String.valueOf(point.getClusterNumber()) + " ";
    }

    /**
     *
     * @param x
     * @param y
     * @return возвращает точку из таблицы по её координатам
     */
    public Point getPoint(int x, int y) {
        return points.get(y).get(x);
    }

    /**
     * генерируем таблицу исходя из вероятности появления черной
     */
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

    /**
     * @param x
     * @param y
     * @return возвращает черную или белую точку, в зависимости от вероятности с соотв. координатами
     */
    private Point getPointForGenerateTable(int x, int y) {
        return new Point((new Random().nextInt(1000) <= p * 1000)
                ? Point.BLACK_POINT : WHITE_POINT,
                x, y);
    }

    /**
     * @param x
     * @param y
     * @return возвращает черную точку с соотв. координатами
     */
    public static Point getBlackPoint(int x, int y) {
        return new Point(Point.BLACK_POINT, x, y);
    }

    /**
     * @param x
     * @param y
     * @return возвращает белую точку с соотв. координатами
     */
    public static Point getWhitePoint(int x, int y) {
        return new Point(WHITE_POINT, x, y);
    }

    /**
     * функция запуска процесса просчета дорог между кластерами
     */
    private void processRoads() {
        for (int i = 1; i < n + 1; i++) {
            Point currPoint = getPoint(i, 1);
            TableRoad temp = processStartRoadFromPoint(currPoint.getCoordX(), currPoint.getCoordY());
            if (temp.getRoadLength() < minTableRoad.getRoadLength()) {
                minTableRoad = temp;
            }
        }
    }

    /**
     * @param x
     * @param y
     * @return считаем путь в таблице из точки с соответствующими координатами
     */
    private TableRoad processStartRoadFromPoint(int x, int y) {
        boolean startPointIsBlack = false;
        boolean endPointIsBlack = false;
        ArrayList<Cluster> temp = new ArrayList<>();
        temp.addAll(clusters);
        temp.addAll(whiteClusters);
        /**
         * устанавливаем длины путей до кластеров в бесконечность
         */
        for (Cluster cluster : temp) {
            cluster.setCurrMinLength(Integer.MAX_VALUE);
        }
        Point startPoint = getPoint(x, y);
        Cluster startCluster = new Cluster(this, startPoint);
        if (startPoint.getValue() == Point.BLACK_POINT) {
            for (Cluster cluster : clusters) {
                for (Point point : cluster.getPoints()) {
                    if (point == startPoint) {
                        startCluster = cluster;
                        startPointIsBlack = true;
                        break;
                    }
                }
            }
        }
        /**
         * стартовый кластер и будет конечным, т.к. внутри себя имеет кратчайший путь сверху донизу
         */
        if (startCluster.isTopAndBottomCluster()) {
            return new TableRoad(startCluster, 0);
        }
        startCluster.addRoadsToClusters(temp);
        startCluster.setCurrMinLength(0);
        /**
         * инициализируем начальные пути из стартовой точки до всех кластеров
         */
        for (Road road : startCluster.getRoads()) {
            road.getSecond().setCurrMinLength(road.getRoadLength());
            road.getSecond().setCurrPrevCluster(startCluster);
        }
        /**
         * бежим по точкам
         */
        for (Road road : startCluster.getRoads()) {
            processRoads(road.getSecond());
        }
        int res = Integer.MAX_VALUE;
        Cluster clusterRes = null;
        for (Cluster cluster : temp) {
            if (cluster.isBottomCluster() && res > cluster.getCurrMinLength()) {
                res = cluster.getCurrMinLength();
                clusterRes = cluster;
            }
        }
        for (Cluster cluster : clusters) {
            if (cluster == clusterRes) {
                endPointIsBlack = true;
                break;
            }
        }
        if (!startPointIsBlack) res++;
        if (!endPointIsBlack) res++;
        TableRoad resTableRoad = new TableRoad(clusterRes, res);
        resTableRoad.setStartCluster(startCluster);
        resTableRoad.processRoads();
        return resTableRoad;
    }

    /**
     * рекурсивное построений путей между кластерами с записями в них растояний
     * @param cluster
     */
    private void processRoads(Cluster cluster) {
        if (cluster.isBottomCluster()) return;
        /**
         * проверяем и переписываем новую длину пути
         */
        for (Road road : cluster.getRoads()) {
            if (road.getSecond().getCurrMinLength() > cluster.getCurrMinLength() + road.getRoadLength()) {
                road.getSecond().setCurrMinLength(cluster.getCurrMinLength() + road.getRoadLength());
                road.getSecond().setCurrPrevCluster(cluster);
                processRoads(road.getSecond());
            }
        }
    }

    /**
     * просчет кластеров всей губки (считаем все кластеры в таблице)
     */
    private void processClusters() {
        for (int y = 1; y < m + 1; y++) {
            for (int x = 1; x < n + 1; x++) {
                if (getPoint(x, y).getValue() == Point.BLACK_POINT) {
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
                        if (clusters == null) clusters = new ArrayList<>();
                        clusters.add(new Cluster(this, getPoint(x, y).getClusterFriends()));
                    }
                }
            }
        }

        /**
         * ищем все точки последней строки, которые не вошли в кластеры
         */
        nonClusterPoints = new ArrayList<>();
        for (int i = 1; i < n + 1; i++) {
            if (points.get(m).get(i).getValue() == WHITE_POINT) {
                nonClusterPoints.add(points.get(m).get(i));
            }
        }

        /**
         * создаем кластеры для белых точек, не вошедших в кластеры (т.к. они белые :))
         */
        whiteClusters = new ArrayList<>();
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

    /**
     * Рекурсивная функция просчета соседних точек в кластере по 4м направлениям
     * условие добавления в checkList и результирующий список в том, чтобы
     * проверямая точка была {@link Point#BLACK_POINT}
     *
     * @param checkList список точек, уже учтеных при просчете кластера
     * @param x         координата текущей точки по оси Х
     * @param y         координата текущей точки по оси У
     * @return список всех элементов в кластере с точкой с координатами (Х, У)
     */
    @Nonnull
    private ArrayList<Point> currClusterProcess(ArrayList<Point> checkList, int x, int y) {

        if (checkList == null) checkList = new ArrayList<>();
        ArrayList<Point> res = new ArrayList<>();

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
        ArrayList<Point> currRes = new ArrayList<>();
        currRes.addAll(res);
        currRes.remove(getPoint(x, y));
        for (Point curr : currRes) {
            /**
             * запускаем алгоритм на все точки, прилегающие к текущей
             */
            ArrayList<Point> newPoints = currClusterProcess(checkList, curr.getCoordX(), curr.getCoordY());

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
     *
     * @param cluster список подлежащий проверке
     * @param point   точка, которую планируется внести в список
     * @return true если точки нет в списке, если присутствует то false
     */
    private boolean isNewPointInCluster(ArrayList<Point> cluster, Point point) {
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

    public ArrayList<ArrayList<Point>> getPoints() {
        return points;
    }

    /**
     * @return количество красных (добавленных)
     */
    public int getRedCount() {
        return minTableRoad.getRedCount();
    }

    /**
     * @return ширину пути
     */
    public int getRoadWidth() {
        return minTableRoad.getRoadWidth();
    }

    /**
     * @return длинну пути
     */
    public int getRoadLength() {
        return minTableRoad.getRoadLength();
    }

    /**
     * @return средний размер кластера
     */
    public float getClusterMiddleSize() {
        float res = 0f;
        for (Cluster cluster : clusters) {
            res += cluster.getPoints().size();
        }
        return res / clusters.size();
    }

    /**
     * @return средняя длина дороги между кластерами в пути
     */
    public float getMiddleRoadLenght() {
        return minTableRoad.getMiddleRoadLenght();
    }

    /**
     * @return количество дорог в пути
     */
    public float getRoadCount() {
        return minTableRoad.getRoadCount();
    }

}
