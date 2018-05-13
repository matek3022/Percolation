package utils;

public class Setup {
    /**
     * максимальное количество потоков доступное для программы
     * выбирайте на 1 меньше чем у вас есть (можно глянуть в диспетчере задач)
     * чтобы могли пользоваться компом, но если он вам не нужен на какое - то время,
     * то можете ставить сколько у вас есть
     */
    public static final int MAX_THREADS = 4;

    /**
     * количество повторений (сколько записей будет в xml файле)
     */
    public static final int MAX_ITERATION = 1000;

    /**
     * ширина матрицы
     */
    public static final int N = 50;

    /**
     * высота матрицы
     */
    public static final int M = 50;

    /**
     * вероятность появления черных точек
     */
    public static final double P = 0.95;

    /**
     * с данными о ширине и длине пути
     */
    public static final boolean WITH_LENGTH_WIDTH_AND_RED_COUNT = false;

    /**
     * p.s. выходные файлы будут лежать в папке проекта, называться будет так:
     * m-50n-50p-0.5.xls (примерно)
     */
}
