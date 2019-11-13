package diyeats.ui;

//@@author koushireo

/** UserUi is a public class that facilitates the output of Graph data.
 */
public class GraphUi {
    private static final String padding = "     ";
    private static final String boundary = "    ____________________________________________________________";
    private String[][] graph = new String[50][60];
    private String[] month = { "January", "February", "March",
                               "April", "May", "June",
                               "July", "August", "September",
                               "October", "November", "December"};

    /**
     * Output the entirety of a 2d array to command line interface.
     * @param graph contents of the graph in a 2d array
     * @param month month of the data collected
     * @param type type of the data collected
     */
    public void show(String[][] graph, int month, String type, int highest) {
        System.out.println(this.month[month - 1] + " \n" + type);
        int factor = findFactor(highest);
        for (int i = 0; i < graph.length; i += 1) {
            int currentValue = (int)(((20.0 - i)/20.0) * highest);
            for (int k = 0; k < (factor - findFactor(currentValue)); k += 1) {
                System.out.print(" ");
            }
            System.out.print(currentValue);
            System.out.print(" |");
            for (int j = 0; j < graph[i].length; j += 1) {
                System.out.print(graph[i][j]);
            }
            System.out.println();
        }
        printPadding(factor + 2);
        for (int i = 0; i < graph[0].length; i += 1) {
            System.out.print("-");
        }
        System.out.println();
        printPadding(factor + 2);
        System.out.println("1 2 3 4 5 6 7 8 9 1 1 1 1 1 1 1 1 1 1 2 2 2 2 2 2 2 2 2 2 3 3 ");
        printPadding(factor + 2);
        System.out.println("                  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 ");
        printPadding(factor + 2);
        System.out.println("                           days                               ");
    }

    public int findFactor(int highest) {
        if (highest == 0) {
            return 1;
        }
        int factor = 0;
        while (highest != 0) {
            factor += 1;
            highest /= 10;
        }
        return factor;
    }

    private void printPadding(int padding) {
        for (int i = 0; i < padding; i += 1) {
            System.out.print(" ");
        }
    }
}
