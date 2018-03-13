import model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final double P = 0.6d;
    private static final int N = 60;
    private static final int M = 60;
    private static final String ANSI_RESET = "\033[0m";
    private static final String ANSI_BLACK = "\033[40m";
    private static final String ANSI_WHITE = "\033[47m";
    private static final String ANSI_RED = "\033[41m";
    private static final String TEXTURE = "  " + ANSI_RESET;

    public static void main(String[] argv) {
        getTable(true);
    }

    private static Point getPoint(int x, int y) {
        return new Point((new Random().nextInt(100) <=  P * 100)
                ? Point.BLACK_POINT : Point.WHITE_POINT,
                x, y);
    }

    private static List<List<Point>> getTable(boolean withPrint) {
        List<List<Point>> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(new ArrayList<Point>());
            for (int j = 0; j < M; j++) {
                points.get(i).add(getPoint(i, j));
                if (withPrint) System.out.print(points.get(i).get(j).getValue() == Point.BLACK_POINT ? ANSI_BLACK + TEXTURE : ANSI_WHITE + TEXTURE);
            }
            if (withPrint) System.out.println();
        }
        return points;
    }
}
