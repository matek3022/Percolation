import model.Table;
import utils.ExcelUtils;
import utils.Setup;
import utils.Tests;

public class Main {

    public static void main(String[] argv) {
        if (findHelp(argv) == 1) return;
        if (findM(argv) == 1) return;
        if (findN(argv) == 1) return;
        if (findP(argv) == 1) return;
        if (findIterations(argv) == 1) return;
        if (findThreads(argv) == 1) return;
        if (findMoreParams(argv) == 1) return;
        if (findClusterNumeric(argv) == 1) return;
        if (findShow(argv) == 1) return;
        if (findShowWithRoad(argv) == 1) return;
        int test = findTests(argv);
        if (test == 1) {
            return;
        } else if (test == 0) {
            if (Setup.SHOW_DISPLAY) {
                startNewThread(Setup.M, Setup.N, Setup.P);
            } else {
                newProcessIterations();
            }
        }
    }

    /**
     * поиск флага помощи в аргументах
     * @param argv
     * @return
     */
    private static int findHelp(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-h") || argv[i].equals("help")) {
                System.out.println("-help помощь");
                System.out.println("-h помощь");
                System.out.println("-tr количество потоков (целое число), по умолчанию 1");
                System.out.println("-te тесты, возможные варианты: grad_vertical, grad_horizontal, rain, chess, square, x_black, x_white, x_small_white, h_matrix, snake, перекрывает собой n, m, p и i");
                System.out.println("-n количество столбцов генерируемой таблицы (целое число) по умолчанию 50");
                System.out.println("-m количество строк генерируемой таблицы (целое число) по умолчанию 50");
                System.out.println("-p вероятность появления черных в генерируемой таблице (дробное число) по умолчанию 0.5");
                System.out.println("-i количество итераций для записи в excel файл (целое число) по умолчанию 10");
                System.out.println("-wlwarc с вычислением числа красных, длины и ширины пути (true или false) отключение этого параметра позволяет ускорить работу программы, по умолчанию false");
                System.out.println("-wcn отрисовка таблицы с нумерацией кластеров (true или false), по умолчанию false");
                System.out.println("-show рисовать таблицу или нет (true или false) перекрывает собой количество итераций, по умолчанию true");
                System.out.println("-shwr рисовать таблицу с отображением кратчайшего пути (true или false), зависит от параметра wlwarc (с ним будет отображен путь внутри кластера), по умолчанию false");
                return 1;
            }
        }
        return 0;
    }

    /**
     * поиск флага количества потоков в аргументах
     * @param argv
     * @return
     */
    private static int findThreads(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-tr")) {
                try {
                    Setup.MAX_THREADS = Integer.valueOf(argv[i + 1]);
                    if (Setup.MAX_THREADS < 1 || Setup.MAX_THREADS > Setup.MAX_ITERATION) throw new Exception();
                    return 0;
                } catch (Exception e) {
                    if (Setup.MAX_THREADS > Setup.MAX_ITERATION) System.out.println("Количество потоков не может быть больше числа итераций");
                    System.out.println("Ошибка параметра -tr");
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * поиск флага ширины таблицы в аргументах
     * @param argv
     * @return
     */
    private static int findN(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-n")) {
                try {
                    Setup.N = Integer.valueOf(argv[i + 1]);
                    if (Setup.N < 0) throw new Exception();
                    return 0;
                } catch (Exception e) {
                    System.out.println("Ошибка параметра -n");
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * поиск флага высоты таблицы в аргументах
     * @param argv
     * @return
     */
    private static int findM(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-m")) {
                try {
                    Setup.M = Integer.valueOf(argv[i + 1]);
                    if (Setup.M < 0) throw new Exception();
                    return 0;
                } catch (Exception e) {
                    System.out.println("Ошибка параметра -m");
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * поиск флага вероятности появления черных в аргументах
     * @param argv
     * @return
     */
    private static int findP(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-p")) {
                try {
                    Setup.P = Double.valueOf(argv[i + 1]);
                    if (Setup.P < 0 || Setup.P > 1) throw new Exception();
                    return 0;
                } catch (Exception e) {
                    System.out.println("Ошибка параметра -p");
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * поиск флага количества итераций в аргументах
     * @param argv
     * @return
     */
    private static int findIterations(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-i")) {
                try {
                    Setup.MAX_ITERATION = Integer.valueOf(argv[i + 1]);
                    if (Setup.MAX_ITERATION < 0) throw new Exception();
                    return 0;
                } catch (Exception e) {
                    System.out.println("Ошибка параметра -i");
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * поиск флага бОльшего количества парамтеров в аргументах
     * @param argv
     * @return
     */
    private static int findMoreParams(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-wlwarc")) {
                try {
                    Setup.WITH_LENGTH_WIDTH_AND_RED_COUNT = Boolean.valueOf(argv[i + 1]);
                    return 0;
                } catch (Exception e) {
                    System.out.println("Ошибка параметра -wlwarc");
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * поиск флага индексирования точек кластера (номер кластера, в который входит точка) в аргументах
     * @param argv
     * @return
     */
    private static int findClusterNumeric(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-wcn")) {
                try {
                    Setup.WITH_CLUSTER_NUMBER = Boolean.valueOf(argv[i + 1]);
                    return 0;
                } catch (Exception e) {
                    System.out.println("Ошибка параметра -wcn");
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * поиск флага отображения матрицы в аргументах
     * @param argv
     * @return
     */
    private static int findShow(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-show")) {
                try {
                    Setup.SHOW_DISPLAY = Boolean.valueOf(argv[i + 1]);
                    return 0;
                } catch (Exception e) {
                    System.out.println("Ошибка параметра -show");
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * поиск флага отображения пути в аргументах
     * @param argv
     * @return
     */
    private static int findShowWithRoad(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-shwr")) {
                try {
                    Setup.SHOW_WITH_ROAD = Boolean.valueOf(argv[i + 1]);
                    return 0;
                } catch (Exception e) {
                    System.out.println("Ошибка параметра -shwr");
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * поиск флага тестов
     * @param argv
     * @return
     */
    private static int findTests(String[] argv) {
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-te")) {
                try {
                    String test = argv[i + 1];
                    if (test.equals(Tests.TestsEnum.CHESS.getTitle())) {
                        startNewThread(Tests.TestsEnum.CHESS);
                    } else if (test.equals(Tests.TestsEnum.GRAD_HORIZONTAL.getTitle())) {
                        startNewThread(Tests.TestsEnum.GRAD_HORIZONTAL);
                    } else if (test.equals(Tests.TestsEnum.GRAD_VERTICAL.getTitle())) {
                        startNewThread(Tests.TestsEnum.GRAD_VERTICAL);
                    } else if (test.equals(Tests.TestsEnum.H_MATRIX.getTitle())) {
                        startNewThread(Tests.TestsEnum.H_MATRIX);
                    } else if (test.equals(Tests.TestsEnum.RAIN.getTitle())) {
                        startNewThread(Tests.TestsEnum.RAIN);
                    } else if (test.equals(Tests.TestsEnum.SNAKE_MATRIX.getTitle())) {
                        startNewThread(Tests.TestsEnum.SNAKE_MATRIX);
                    } else if (test.equals(Tests.TestsEnum.SQUARE.getTitle())) {
                        startNewThread(Tests.TestsEnum.SQUARE);
                    } else if (test.equals(Tests.TestsEnum.X_BLACK.getTitle())) {
                        startNewThread(Tests.TestsEnum.X_BLACK);
                    } else if (test.equals(Tests.TestsEnum.X_SMALL_WHITE.getTitle())) {
                        startNewThread(Tests.TestsEnum.X_SMALL_WHITE);
                    } else if (test.equals(Tests.TestsEnum.X_WHITE.getTitle())) {
                        startNewThread(Tests.TestsEnum.X_WHITE);
                    } else throw new Exception();

                    return 2;
                } catch (Exception e) {
                    System.out.println("Ошибка параметра -te");
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * процесс для итерационной работы программы (для сбора статистики)
     */
    public static void newProcessIterations() {
        for (int i = 0; i < Setup.MAX_THREADS; i++) {
            startNewThread(Setup.M, Setup.N, Setup.P, i, Setup.MAX_THREADS, Setup.MAX_ITERATION);
        }
    }

    /**
     * старт потока для отображения таблицы со сгенерированными параметрами
     * @param m
     * @param n
     * @param p
     */
    public static void startNewThread(int m, int n, double p) {
        startNewThread(m, n, p, null);
    }

    /**
     * старт потока для отображения тестовой таблицы
     * @param test
     */
    public static void startNewThread(Tests.TestsEnum test) {
        startNewThread(0, 0, 0, test);
    }

    /**
     * старт потока для отображения
     * @param m
     * @param n
     * @param p
     * @param test
     */
    public static void startNewThread(int m, int n, double p, Tests.TestsEnum test) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                Table table;
                if (test == null) table = new Table(m, n, p);
                else table = new Table(test.getTable());
                time = System.currentTimeMillis() - time;
                System.out.println();
                System.out.println("Cluster count: " + table.getClusterCount());
                System.out.println("Cluster middle size: " + table.getClusterMiddleSize());
                System.out.println("Table red count: " + table.getRedCount());
                System.out.println("Table road length: " + table.getRoadLength());
                System.out.println("Table road width: " + table.getRoadWidth());
                System.out.println("Table red road count: " + table.getRoadCount());
                System.out.println("Table red road middle length: " + table.getMiddleRoadLenght());
                System.out.println("Time: " + time);
                if (test == null) System.out.println(String.format("Params: m = %s, n = %s, p = %s", m, n, p));
                else System.out.println("Type is " + test.getDescription());
                table.printTable(Setup.WITH_CLUSTER_NUMBER);
            }
        }).start();
    }

    /**
     * старт потока для сбора статистики в excel файл
     * @param m
     * @param n
     * @param p
     * @param threadNumber
     * @param threads
     * @param itterations
     */
    public static void startNewThread(int m, int n, double p, int threadNumber, int threads, int itterations) {
        String fileName = "m-" + m + "n-" + n + "p-" + p + ".xls";
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < itterations / threads; i++) {
                    long time = System.currentTimeMillis();
                    Table table = new Table(m, n, p);
                    time = System.currentTimeMillis() - time;
                    int currIter = i + threadNumber * (itterations / threads);
                    System.out.println("Текущая выполненная операция = " + currIter);
                    ExcelUtils.writeTableToFile(fileName, table, currIter, time);
                }
            }
        });
        thread.start();
    }

}
