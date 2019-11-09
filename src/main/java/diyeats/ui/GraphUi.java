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

    public void show(String[][] graph, int month, String type) {
        System.out.println(this.month[month - 1] + " " + type);
        for (int i = 0; i < graph.length; i += 1) {
            System.out.print(" |");
            for (int j = 0; j < graph[i].length; j += 1) {
                System.out.print(graph[i][j]);
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int i = 0; i < graph[0].length; i += 1) {
            System.out.print("-");
        }
        System.out.println();
        System.out.println("  1 2 3 4 5 6 7 8 9 1 1 1 1 1 1 1 1 1 1 2 2 2 2 2 2 2 2 2 2 3 3 ");
        System.out.println("                    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 ");
    }
}
