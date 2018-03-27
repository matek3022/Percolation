import model.Table;


public class Main {

    public static void main(String[] argv) {
//        startNewThread(75, 75, 0.1d);
//        startNewThread(75, 75, 0.2d);
//        startNewThread(75, 75, 0.3d);
//        startNewThread(75, 75, 0.4d);
        startNewThread(75, 75, 0.5d);
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
    }

    public static void startNewThread(int m, int n, double p) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis() / 1000L;
                Table table = new Table(m, n, p);
                System.out.println();
                System.out.println("Cluster count: " + table.getClusterCount());
                System.out.println("Table minLength: " + table.getMinLength());
                System.out.println("Time: " + (System.currentTimeMillis() / 1000L - time));
                System.out.println(String.format("Params: m = %s, n = %s, p = %s", m, n, p));
            }
        }).start();
    }

}
