import model.Point;
import model.Table;
import utils.ExcelUtils;
import utils.Setup;

import java.util.ArrayList;


public class Main {

    public static int currThreads = 0;
    public static int currIter = 0;

    public static void main(String[] argv) {
//        startNewThread(75, 75, 0.1d);
//        startNewThread(75, 75, 0.2d);
//        startNewThread(50, 50, 0.05d);
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
        newProcessIterations();
    }

    public static void processIterations() {
        currIter = 0;
        while (currIter != Setup.MAX_ITERATION) {
            if (currThreads < Setup.MAX_THREADS * 2) {
                currIter++;
                startNewThread(Setup.M, Setup.N, Setup.P, currIter);
            }
        }
    }

    public static void newProcessIterations() {
        for (int i = 0; i < Setup.MAX_THREADS; i++) {
            startNewThread(Setup.M, Setup.N, Setup.P, i, Setup.MAX_THREADS, Setup.MAX_ITERATION);
        }
    }

    public static void startNewThread(int m, int n, double p) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                Table table = new Table(m, n, p);
                time = System.currentTimeMillis() - time;
                System.out.println();
                System.out.println("Cluster count: " + table.getClusterCount());
                System.out.println("Cluster middle size: " + table.getClusterMiddleSize());
                System.out.println("Table red count: " + table.getRedCount());
                System.out.println("Table road length: " + table.getRoadLength());
                System.out.println("Table road width: " + table.getRoadWidth());
                System.out.println("Time: " + time);
                System.out.println(String.format("Params: m = %s, n = %s, p = %s", m, n, p));
                table.printTable(false);
            }
        }).start();
    }

    public static void startNewThread(int m, int n, double p, int currIter) {
        String fileName = "m-" + m + "n-" + n + "p-" + p + ".xls";
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                currThreads++;
                long time = System.currentTimeMillis();
                Table table = new Table(m, n, p);
                time = System.currentTimeMillis() - time;
                System.out.println("Текущая выполненная операция = " + currIter);
                ExcelUtils.writeTableToFile(fileName, table, currIter, time);
                currThreads--;
            }
        });
        thread.start();
    }

    public static void startNewThread(int m, int n, double p, int threadNumber, int threads, int itterations) {
        String fileName = "m-" + m + "n-" + n + "p-" + p + ".xls";
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < itterations/threads; i++) {
                    long time = System.currentTimeMillis();
                    Table table = new Table(m, n, p);
                    time = System.currentTimeMillis() - time;
                    int currIter = i + threadNumber*(itterations/threads);
                    System.out.println("Текущая выполненная операция = " + currIter);
                    ExcelUtils.writeTableToFile(fileName, table, currIter, time);
                }
            }
        });
        thread.start();
    }

    public static void startNewThread(ArrayList<ArrayList<Point>> points) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                Table table = new Table(points);
                System.out.println();
                System.out.println("Cluster count: " + table.getClusterCount());
                System.out.println("Table minLength: " + table.getRedCount());
                System.out.println("Table road width: " + table.getRoadWidth());
                System.out.println("Time: " + (System.currentTimeMillis() - time));
                System.out.println(String.format("Params: m = %s, n = %s, p = %s", table.getM(), table.getN(), table.getP()));
                table.printTable(false);
            }
        }).start();
    }

}
