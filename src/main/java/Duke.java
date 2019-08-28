import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private static final int LINE_LENGTH = 50;
    private static final int NUM_SPACE = 4;
    private static ArrayList<Task> storeList =
             new ArrayList<Task>();

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

    private static void printList() {
        for (int i = 0; i < storeList.size(); i++) {
            System.out.print(" " + (i + 1) + ". " +"["
                    + storeList.get(i).getStatusIcon());
            System.out.println("] "
                    + storeList.get(i).description);
            printSpaces();
        }
        printDash();
    }

    private static void printTaskDone(int num) {
        System.out.println(" Nice! I have marked this "
                + "task as done:");
        printSpaces();
        storeList.get(num).markAsDone();
        System.out.println(" [" + "\u2713"
                + "] " + storeList.get(num).description);
        printSpaces();
        printDash();
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
            } else if (str.equals("list")) {
                printList();
                continue;
            } else if (str.length() > 3 && str.substring(0,4).equals("done")) {
                int temp =  Integer.parseInt(str.substring(5));
                printTaskDone(temp - 1);
                continue;
            }
            storeList.add(new Task(str));
            System.out.println(" added: " + str);
            printSpaces();
            printDash();
        }
    }
    /** This function is responsible for the proper execution
     *  of Duke.
     */

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.print("____");
        printDash();
        System.out.println("Hello! I am Duke \n"
                + "What can I do for you?");
        System.out.print("____");
        printDash();
        takeInput();
    }
}
