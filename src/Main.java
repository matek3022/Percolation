import model.Table;
import utils.ExcelUtils;
import utils.Setup;


public class Main {

    public static int currThreads = 0;

    public static void main(String[] argv) {
        if (Setup.SHOW_DISPLAY) {
            startNewThread(Setup.M, Setup.N, Setup.P);
        } else {
            newProcessIterations();
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
                table.printTable(Setup.WITH_CLUSTER_NUMBER);
            }
        }).start();
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

}
