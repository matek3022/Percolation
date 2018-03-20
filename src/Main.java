import model.Table;

public class Main {

    public static void main(String[] argv) {
        Table table = new Table();
        table.printTable(false);
        System.out.println();
        table.printTable(true);
        System.out.println("Cluster count: " + table.getClusterCount());
    }

}
