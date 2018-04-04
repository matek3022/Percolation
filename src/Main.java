import model.Point;
import model.Table;
import utils.ExcelUtils;

import java.util.LinkedList;


public class Main {
    public static final int MAX_THREADS = 4;
    public static final int MAX_ITERATION = 20;

    public static int currThreads = 0;
    public static int currIter = 0;

    public static void main(String[] argv) {
//        startNewThread(75, 75, 0.1d);
//        startNewThread(75, 75, 0.2d);
//        startNewThread(75, 75, 0.3d);
//        startNewThread(75, 75, 0.4d);
//        startNewThread(75, 75, 0.5d);
//        startNewThread(75, 75, 0.6d);
//        startNewThread(75, 75, 0.7d);
//        startNewThread(75, 75, 0.8d);
//        startNewThread(75, 75, 0.9d);
//
//        startNewThread(100, 100, 0.1d);
//        startNewThread(100, 100, 0.2d);
//        startNewThread(100, 100, 0.3d);
//        startNewThread(100, 100, 0.4d);
//        startNewThread(100, 100, 0.5d);
//        startNewThread(100, 100, 0.6d);
//        startNewThread(100, 100, 0.7d);
//        startNewThread(100, 100, 0.8d);
//        startNewThread(100, 100, 0.9d);
//        startNewThread(150, 150, 0.4d);
//        startNewThread(20, 20, 0.1d);
//        startNewThread(Tests.getChessMatrix50x50());
//        startNewThread(Tests.getGradMatrix50x50(true));
//        startNewThread(Tests.getGradMatrix50x50(false));
//        startNewThread(Tests.getRainMatrix50x50());
//        new MainForm();
//        processIterations();
//        startNewThread(Tests.getSquareMatrix50x50());
//        startNewThread(Tests.getXBlackMatrix50x50());
//        startNewThread(Tests.getXWhiteMatrix50x50());
//        startNewThread(Tests.getXSmallWhiteMatrix50x50());

    }

    public static void processIterations() {
        currIter = 0;
        while (currIter != MAX_ITERATION) {
            if (currThreads < MAX_THREADS) {
                currIter++;
                startNewThread(50, 50, 0.4d, currIter);
            } else {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void startNewThread(int m, int n, double p) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis() / 1000L;
                Table table = new Table(m, n, p);
                time = System.currentTimeMillis() / 1000L - time;
                System.out.println();
                System.out.println("Cluster count: " + table.getClusterCount());
                System.out.println("Table minLength: " + table.getMinLength());
                System.out.println("Table road width: " + table.getRoadWidth());
                System.out.println("Time: " + time);
                System.out.println(String.format("Params: m = %s, n = %s, p = %s", m, n, p));
                table.printTable(false);
            }
        }).start();
    }

    public static void startNewThread(int m, int n, double p, int currIter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                currThreads++;
                long time = System.currentTimeMillis() / 1000L;
                Table table = new Table(m, n, p);
                time = System.currentTimeMillis() / 1000L - time;
                System.out.println("Текущая выполненная операция = " + currIter);
                ExcelUtils.writeTableToFile("test.xls", table, currIter, time);
                currThreads--;
            }
        }).start();
    }

    public static void startNewThread(LinkedList<LinkedList<Point>> points) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis() / 1000L;
                Table table = new Table(points);
                System.out.println();
                System.out.println("Cluster count: " + table.getClusterCount());
                System.out.println("Table minLength: " + table.getMinLength());
                System.out.println("Table road width: " + table.getRoadWidth());
                System.out.println("Time: " + (System.currentTimeMillis() / 1000L - time));
                System.out.println(String.format("Params: m = %s, n = %s, p = %s", table.getM(), table.getN(), table.getP()));
                table.printTable(false);
            }
        }).start();
    }

}
