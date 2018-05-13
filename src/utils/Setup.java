package utils;

public class Setup {
    /**
     * максимальное количество потоков доступное для программы
     * выбирайте на 1 меньше чем у вас есть (можно глянуть в диспетчере задач)
     * чтобы могли пользоваться компом, но если он вам не нужен на какое - то время,
     * то можете ставить сколько у вас есть
     */
    public static int MAX_THREADS = 4;

    /**
     * количество повторений (сколько записей будет в xml файле)
     */
    public static int MAX_ITERATION = 1000;

    /**
     * ширина матрицы
     */
    public static int N = 50;

    /**
     * высота матрицы
     */
    public static int M = 50;

    /**
     * вероятность появления черных точек
     */
    public static double P = 0.25;

    /**
     * с данными о ширине и длине пути
     */
    public static boolean WITH_LENGTH_WIDTH_AND_RED_COUNT = true;

    /**
     * с индексами кластеров для SHOW_DISPLAY = true
     */
    public static boolean WITH_CLUSTER_NUMBER = true;

    /**
     * показывать таблицу или нет, только один раз
     */
    public static boolean SHOW_DISPLAY = true;

    /**
     * показывать таблицу с дорогой или нет для SHOW_DISPLAY = true
     */
    public static boolean SHOW_WITH_ROAD = true;

    /**
     * p.s. выходные файлы будут лежать в папке проекта, называться будет так:
     * m-50n-50p-0.5.xls (примерно)
     */
}
