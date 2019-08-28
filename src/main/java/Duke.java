import java.util.Scanner;

public class Duke {
    private static final int LINE_LENGTH = 50;
    private static final int NUM_SPACE = 4;

    private static void printDash() {
        for (int i = 0; i < LINE_LENGTH; i++) {
            System.out.print("_");
        }
        System.out.println();
    }

    private static void printSpaces() {
        for (int i = 0; i < NUM_SPACE; i++) {
            System.out.print(" ");
        }
    }

    private static void takeInput() {
        Scanner sc = new Scanner(System.in);
        String str;
        //To continue taking inputs until the user says bye
        while (true) {
            str = sc.nextLine();
            printSpaces();
            printDash();
            printSpaces();
            if (str.equals("bye")) {
                System.out.println(" Bye. Hope to see you again"
                        + " soon.");
                printSpaces();
                printDash();
                break;
            }
            System.out.println(" " + str);
            printSpaces();
            printDash();
        }
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.print("____");
        printDash();
        System.out.println("Hello! I am Duke \n" +
                "What can I do for you?");
        System.out.print("____");
        printDash();
        takeInput();
    }
}
