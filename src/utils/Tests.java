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

    /**
     * вложенные квадратики
     * @return
     */
    public static LinkedList<LinkedList<Point>> getSquareMatrix50x50() {
        int m = 50;
        int n = 50;
        LinkedList<LinkedList<Point>> res = new LinkedList<>();
        res.add(new LinkedList<>());
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
            res.add(new LinkedList<Point>());
            /**
             * добавляем белую точку в начале строки
             */
            res.get(i).add(Table.getWhitePoint(0, i));
            /**
             * бежим по столбцам
             */
            for (int j = 1; j < n + 1; j++) {
                if (i<26) {
                    if ((i - j >= 0) && (n - i >= j)) {
                        if (j % 2 == 0) res.get(i).add(Table.getWhitePoint(j, i));
                        else res.get(i).add(Table.getBlackPoint(j, i));
                    } else {
                        if ((n - i >= j)) {
                            if (i % 2 == 0) res.get(i).add(Table.getWhitePoint(j, i));
                            else res.get(i).add(Table.getBlackPoint(j, i));
                        } else {
                            if (j % 2 == 0) res.get(i).add(Table.getWhitePoint(j, i));
                            else res.get(i).add(Table.getBlackPoint(j, i));
                        }
                    }
                } else {
                    if ((j - i >= 0) && (n - i <= j)) {
                        if (j % 2 == 0) res.get(i).add(Table.getWhitePoint(j, i));
                        else res.get(i).add(Table.getBlackPoint(j, i));
                    } else {
                        if ((n - i <= j)) {
                            if (i % 2 == 0) res.get(i).add(Table.getWhitePoint(j, i));
                            else res.get(i).add(Table.getBlackPoint(j, i));
                        } else {
                            if (j % 2 == 0) res.get(i).add(Table.getWhitePoint(j, i));
                            else res.get(i).add(Table.getBlackPoint(j, i));
                        }
                    }
                }

                }
            /**
             * добавляем белую точку в конце строки
             */
            res.get(i).add(Table.getWhitePoint(res.get(i).size() - 1, i));
        }
        /**
         * добавляем белую строку в конце
         */
        res.add(new LinkedList<Point>());
        for (int i = 0; i < m + 2; i++) {
            res.get(res.size() - 1).add(Table.getWhitePoint(i, res.size() - 1));
        }
        return res;
    }

    public static LinkedList<LinkedList<Point>> getXBlackMatrix50x50() {
        int m = 50;
        int n = 50;
        LinkedList<LinkedList<Point>> res = new LinkedList<>();
        res.add(new LinkedList<>());
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
            res.add(new LinkedList<Point>());
            /**
             * добавляем белую точку в начале строки
             */
            res.get(i).add(Table.getWhitePoint(0, i));
            /**
             * бежим по столбцам
             */
            for (int j = 1; j < n + 1; j++) {
                if ((i==j)||(n+1-i==j)||(j==i+1)||(n-i==j)||(n-i-24==j)||(n-i-23==j)||(n-j-23==i)||(i==j-24)||(i==j-25)||(n-i-24==j)||(i==j+5)||(i==j+6)||(j==i+8)||(j==i+9)||(j==i+16)||(j==i+17)||(j==i+24)||(j==i+25)
                        ||(j==i+32)||(j==i+33)||(j==i+40)||(j==i+41)||(i==j+13)||(i==j+14)||(i==j+21)||(i==j+22)||(i==j+29)||(i==j+30)||(i==j+37)||(i==j+38)||(i==j+45)||(i==j+46)
                        ||(n-i-32==j)||(n-i-31==j)||(n-i-40==j)||(n-i-39==j)||(n-i-16==j)||(n-i-15==j)||(n-i-8==j)||(n-i-7==j)
                        ||(n+6-i==j)||(n+7-i==j)||(n+14-i==j)||(n+15-i==j)||(n+22-i==j)||(n+23-i==j)||(n+30-i==j)||(n+31-i==j)||(n+38-i==j)||(n+39-i==j)||(n+46-i==j)||(n+47-i==j)||((i==2)&&(j==1))||((i==2)&&(j==50)))
                res.get(i).add(Table.getWhitePoint(j, i));
                else res.get(i).add(Table.getBlackPoint(j, i));
            }
            /**
             * добавляем белую точку в конце строки
             */
            res.get(i).add(Table.getWhitePoint(res.get(i).size() - 1, i));
        }
        /**
         * добавляем белую строку в конце
         */
        res.add(new LinkedList<Point>());
        for (int i = 0; i < m + 2; i++) {
            res.get(res.size() - 1).add(Table.getWhitePoint(i, res.size() - 1));
        }
        return res;
    }

    public static LinkedList<LinkedList<Point>> getXWhiteMatrix50x50() {
        int m = 50;
        int n = 50;
        LinkedList<LinkedList<Point>> res = new LinkedList<>();
        res.add(new LinkedList<>());
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
            res.add(new LinkedList<Point>());
            /**
             * добавляем белую точку в начале строки
             */
            res.get(i).add(Table.getWhitePoint(0, i));
            /**
             * бежим по столбцам
             */
            for (int j = 1; j < n + 1; j++) {
                if ((i==j)||(n+1-i==j)||(j==i+1)||(n-i==j)||(n-i-24==j)||(n-i-23==j)||(n-j-23==i)||(i==j-24)||(i==j-25)||(n-i-24==j)||(i==j+5)||(i==j+6)||(j==i+8)||(j==i+9)||(j==i+16)||(j==i+17)||(j==i+24)||(j==i+25)
                        ||(j==i+32)||(j==i+33)||(j==i+40)||(j==i+41)||(i==j+13)||(i==j+14)||(i==j+21)||(i==j+22)||(i==j+29)||(i==j+30)||(i==j+37)||(i==j+38)||(i==j+45)||(i==j+46)
                        ||(n-i-32==j)||(n-i-31==j)||(n-i-40==j)||(n-i-39==j)||(n-i-16==j)||(n-i-15==j)||(n-i-8==j)||(n-i-7==j)
                        ||(n+6-i==j)||(n+7-i==j)||(n+14-i==j)||(n+15-i==j)||(n+22-i==j)||(n+23-i==j)||(n+30-i==j)||(n+31-i==j)||(n+38-i==j)||(n+39-i==j)||(n+46-i==j)||(n+47-i==j)||((i==2)&&(j==1))||((i==2)&&(j==50)))
                    res.get(i).add(Table.getBlackPoint(j, i));
                else res.get(i).add(Table.getWhitePoint(j, i));
            }
            /**
             * добавляем белую точку в конце строки
             */
            res.get(i).add(Table.getWhitePoint(res.get(i).size() - 1, i));
        }
        /**
         * добавляем белую строку в конце
         */
        res.add(new LinkedList<Point>());
        for (int i = 0; i < m + 2; i++) {
            res.get(res.size() - 1).add(Table.getWhitePoint(i, res.size() - 1));
        }
        return res;
    }

    public static LinkedList<LinkedList<Point>> getXSmallWhiteMatrix50x50() {
        int m = 50;
        int n = 50;
        LinkedList<LinkedList<Point>> res = new LinkedList<>();
        res.add(new LinkedList<>());
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
            res.add(new LinkedList<Point>());
            /**
             * добавляем белую точку в начале строки
             */
            res.get(i).add(Table.getWhitePoint(0, i));
            /**
             * бежим по столбцам
             */
            for (int j = 1; j < n + 1; j++) {
                if ((i==j)||(n+1-i==j)||(n-j-23==i)||(i==j+5)||(j==i+8)||(j==i+16)||(j==i+24)
                        ||(j==i+32)||(j==i+40)||(i==j+13)||(i==j+21)||(i==j+29)||(i==j+37)||(i==j+45)
                        ||(n-i-31==j)||(n-i-39==j)||(n-i-15==j)||(n-i-7==j)
                        ||(n+6-i==j)||(n+14-i==j)||(n+22-i==j)||(n+30-i==j)||(n+38-i==j)||(n+46-i==j)||((i==2)&&(j==1))||((i==2)&&(j==50))||((i==1)&&(j==2))||((i==1)&&(j==49)))
                    res.get(i).add(Table.getBlackPoint(j, i));
                else res.get(i).add(Table.getWhitePoint(j, i));
            }
            /**
             * добавляем белую точку в конце строки
             */
            res.get(i).add(Table.getWhitePoint(res.get(i).size() - 1, i));
        }
        /**
         * добавляем белую строку в конце
         */
        res.add(new LinkedList<Point>());
        for (int i = 0; i < m + 2; i++) {
            res.get(res.size() - 1).add(Table.getWhitePoint(i, res.size() - 1));
        }
        return res;
    }

    public static LinkedList<LinkedList<Point>> getHMatrix50x50() {
        int m = 50;
        int n = 50;
        LinkedList<LinkedList<Point>> res = new LinkedList<>();
        res.add(new LinkedList<>());
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
            res.add(new LinkedList<Point>());
            /**
             * добавляем белую точку в начале строки
             */
            res.get(i).add(Table.getWhitePoint(0, i));
            /**
             * бежим по столбцам
             */
            for (int j = 1; j < n + 1; j++) {
                if (((j==2)&&(i>1)&&(i<33))||((j==8)&&(i>1)&&(i<33))||((j==10)&&(i>1)&&(i<33))||((j==16)&&(i>1)&&(i<33))||((j==18)&&(i>1)&&(i<33))||((j==24)&&(i>1)&&(i<33))
                        ||((j==26)&&(i>1)&&(i<33))||((j==32)&&(i>1)&&(i<33))||((j==34)&&(i>1)&&(i<33))||((j==40)&&(i>1)&&(i<33))||((j==42)&&(i>1)&&(i<33))
                        ||((j==48)&&(i>1)&&(i<33))||((j==50)&&(i>1)&&(i<33))||((j==4)&&(i>18)&&(i<50))||((j==6)&&(i>18)&&(i<50))||((j==12)&&(i>18)&&(i<50))
                        ||((j==14)&&(i>18)&&(i<50))||((j==20)&&(i>18)&&(i<50))||((j==22)&&(i>18)&&(i<50))||((j==28)&&(i>18)&&(i<50))||((j==30)&&(i>18)&&(i<50))
                        ||((j==36)&&(i>18)&&(i<50))||((j==38)&&(i>18)&&(i<50))||((j==44)&&(i>18)&&(i<50))||((j==46)&&(i>18)&&(i<50))
                        ||((i==17)&&(j>2)&&(j<8))||((i==17)&&(j>10)&&(j<16))||((i==17)&&(j>18)&&(j<24))||((i==17)&&(j>26)&&(j<32))||((i==17)&&(j>34)&&(j<40))||((i==17)&&(j>42)&&(j<48))
                        ||((i==34)&&(j>0)&&(j<5))||((i==34)&&(j>6)&&(j<12))||((i==34)&&(j>14)&&(j<20))||((i==34)&&(j>22)&&(j<28))||((i==34)&&(j>30)&&(j<36))||((i==34)&&(j>38)&&(j<44))||((i==34)&&(j>46)&&(j<51)))
                    res.get(i).add(Table.getBlackPoint(j, i));
                else res.get(i).add(Table.getWhitePoint(j, i));
            }
            /**
             * добавляем белую точку в конце строки
             */
            res.get(i).add(Table.getWhitePoint(res.get(i).size() - 1, i));
        }
        /**
         * добавляем белую строку в конце
         */
        res.add(new LinkedList<Point>());
        for (int i = 0; i < m + 2; i++) {
            res.get(res.size() - 1).add(Table.getWhitePoint(i, res.size() - 1));
        }
        return res;
    }

    public static LinkedList<LinkedList<Point>> getSnakeMatrix50x50() {
        int m = 50;
        int n = 50;
        LinkedList<LinkedList<Point>> res = new LinkedList<>();
        res.add(new LinkedList<>());
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
            res.add(new LinkedList<Point>());
            /**
             * добавляем белую точку в начале строки
             */
            res.get(i).add(Table.getWhitePoint(0, i));
            /**
             * бежим по столбцам
             */
            for (int j = 1; j < n + 1; j++) {
                if ((((j+1)%2==1)&&(i>3)&&(i<48))||((i==2)&&(j>0)&&(j<5))||((i==2)&&(j>7)&&(j<17))||((i==2)&&(j>19)&&(j<29))||((i==2)&&(j>31)&&(j<41))||((i==2)&&(j>43)&&(j<51))
                        ||((i==3)&&(j==4))||((i==3)&&(j==8))||((i==3)&&(j==12))||((i==3)&&(j==16))||((i==3)&&(j==20))||((i==3)&&(j==24))||((i==3)&&(j==28))||((i==3)&&(j==32))||((i==3)&&(j==36))||((i==3)&&(j==40))||((i==3)&&(j==44))||((i==3)&&(j==48))
                        ||((i==48)&&(j==2))||((i==48)&&(j==6))||((i==48)&&(j==10))||((i==48)&&(j==14))||((i==48)&&(j==18))||((i==48)&&(j==22))||((i==48)&&(j==26))||((i==48)&&(j==30))||((i==48)&&(j==34))||((i==48)&&(j==38))||((i==48)&&(j==42))||((i==48)&&(j==46))||((i==48)&&(j==50))
                        ||((i==49)&&(j>1)&&(j<11))||((i==49)&&(j>13)&&(j<23))||((i==49)&&(j>25)&&(j<35))||((i==49)&&(j>37)&&(j<47))||((i==49)&&(j==50)))
                    res.get(i).add(Table.getBlackPoint(j, i));
                else res.get(i).add(Table.getWhitePoint(j, i));
            }
            /**
             * добавляем белую точку в конце строки
             */
            res.get(i).add(Table.getWhitePoint(res.get(i).size() - 1, i));
        }
        /**
         * добавляем белую строку в конце
         */
        res.add(new LinkedList<Point>());
        for (int i = 0; i < m + 2; i++) {
            res.get(res.size() - 1).add(Table.getWhitePoint(i, res.size() - 1));
        }
        return res;
    }

}
