package utils;

import model.Point;
import model.Table;

import java.util.ArrayList;
import java.util.Random;

public abstract class Tests {

    /**
     * если isVertical == false
     * ████████████████████
     *
     * ████████████████████
     *
     * ████████████████████
     *
     * ████████████████████
     * если isVertical == true то вертикальные линии
     * 50x50 + белая окантовка
     * @return
     */
    public static ArrayList<ArrayList<Point>> getGradMatrix50x50(boolean isVertical) {
        int m = 50;
        int n = 50;
        ArrayList<ArrayList<Point>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        /**
         * добавляем белую строку в начале
         */
        for (int i = 0; i < m + 2; i++) {
            res.get(0).add(Table.getWhitePoint(i, 0));
        }
        /**
         * бежим по строкам
         */
        for (int i = 1; i < m + 1; i++) {
            res.add(new ArrayList<Point>());
            /**
             * добавляем белую точку в начале строки
             */
            res.get(i).add(Table.getWhitePoint(0, i));
            /**
             * бежим по столбцам
             */
            for (int j = 1; j < n + 1; j++) {
                res.get(i).add((isVertical ? j : i) % 2 == 1 ? Table.getBlackPoint(j, i) : Table.getWhitePoint(j, i));
            }
            /**
             * добавляем белую точку в конце строки
             */
            res.get(i).add(Table.getWhitePoint(res.get(i).size() - 1, i));
        }
        /**
         * добавляем белую строку в конце
         */
        res.add(new ArrayList<Point>());
        for (int i = 0; i < m + 2; i++) {
            res.get(res.size() - 1).add(Table.getWhitePoint(i, res.size() - 1));
        }
        return res;
    }

    /**
     * дождь (прирывистые вертикальные линии)
     * @return
     */
    public static ArrayList<ArrayList<Point>> getRainMatrix50x50() {
        int m = 50;
        int n = 50;
        ArrayList<ArrayList<Point>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        /**
         * добавляем белую строку в начале
         */
        for (int i = 0; i < m + 2; i++) {
            res.get(0).add(Table.getWhitePoint(i, 0));
        }
        /**
         * бежим по строкам
         */
        for (int i = 1; i < m + 1; i++) {
            res.add(new ArrayList<Point>());
            /**
             * добавляем белую точку в начале строки
             */
            res.get(i).add(Table.getWhitePoint(0, i));
            /**
             * бежим по столбцам
             */
            for (int j = 1; j < n + 1; j++) {
                res.get(i).add(j % 2 == 1 ? new Random().nextInt(1000) > 500 ? Table.getBlackPoint(j, i) : Table.getWhitePoint(j, i) : Table.getWhitePoint(j, i));
            }
            /**
             * добавляем белую точку в конце строки
             */
            res.get(i).add(Table.getWhitePoint(res.get(i).size() - 1, i));
        }
        /**
         * добавляем белую строку в конце
         */
        res.add(new ArrayList<Point>());
        for (int i = 0; i < m + 2; i++) {
            res.get(res.size() - 1).add(Table.getWhitePoint(i, res.size() - 1));
        }
        return res;
    }

    /**
     * шахматная доска
     * @return
     */
    public static ArrayList<ArrayList<Point>> getChessMatrix50x50() {
        int m = 50;
        int n = 50;
        ArrayList<ArrayList<Point>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        /**
         * добавляем белую строку в начале
         */
        for (int i = 0; i < m + 2; i++) {
            res.get(0).add(Table.getWhitePoint(i, 0));
        }
        /**
         * бежим по строкам
         */
        for (int i = 1; i < m + 1; i++) {
            res.add(new ArrayList<Point>());
            /**
             * добавляем белую точку в начале строки
             */
            res.get(i).add(Table.getWhitePoint(0, i));
            /**
             * бежим по столбцам
             */
            for (int j = 1; j < n + 1; j++) {
                res.get(i).add(((j + i) % 2 == 1) ? Table.getBlackPoint(j, i) : Table.getWhitePoint(j, i));
            }
            /**
             * добавляем белую точку в конце строки
             */
            res.get(i).add(Table.getWhitePoint(res.get(i).size() - 1, i));
        }
        /**
         * добавляем белую строку в конце
         */
        res.add(new ArrayList<Point>());
        for (int i = 0; i < m + 2; i++) {
            res.get(res.size() - 1).add(Table.getWhitePoint(i, res.size() - 1));
        }
        return res;
    }
}
