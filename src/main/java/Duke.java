import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Duke {
    private static final int LINE_LENGTH = 50;
    private static final int NUM_SPACE = 4;
    private static ArrayList<Task> storeList =
             new ArrayList<>();

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
            System.out.println(" " + (i + 1) + ". "
                    + storeList.get(i).toString());
            printSpaces();
        }
        printDash();
    }

    private static void printTaskDone(int num) {
        System.out.println(" Nice! I have marked this "
                + "task as done:");
        printSpaces();
        storeList.get(num).markAsDone();
        System.out.println(" " + storeList.get(num).toString());
        printSpaces();
        printDash();
    }

    private static void addTask(Task taskA) {
        System.out.println(" Got it. I have added this task:");
        printSpaces();
        System.out.println("  " + taskA.toString());
        printSpaces();
        System.out.println(" Now you have " + storeList.size()
                + " tasks in the list.");
        printSpaces();
        printDash();
    }

    private static void takeInput() {
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.nextLine();
            if (str.equals("bye")) {
                printSpaces();
                printDash();
                printSpaces();
                System.out.println(" Bye. Hope to see you again"
                        + " soon.");
                printSpaces();
                printDash();
                break;
            }
            try {
                processInput(str);
            } catch (DukeException e) {
                System.out.println(e.getMessage());
                printSpaces();
                printDash();
            }
        }
    }

    private static void processInput(String str) throws DukeException {
        Scanner sc = new Scanner(System.in);
        //To process the various forms on input given to DUKE
        printSpaces();
        printDash();
        printSpaces();
        List<String> splitInput = new ArrayList<String>(
                Arrays.asList(str.split(" ")));

        //to print the list
        if (str.equals("list")) {
            printList();
        }

        //marking a task as done
        else if (splitInput.get(0).equals("done")) {
            try {
                int temp = Integer.parseInt(splitInput.get(1));
                printTaskDone(temp - 1);
            } catch (NumberFormatException obj) {
                throw new DukeException("OOPS! Enter a "
                        + "positive integer after \"done\"");
            } catch (IndexOutOfBoundsException obj) {
                throw new DukeException("OOPS! Enter a number that "
                        + "is present in the list");
            }
        }

        //adding a task which has no timing constraints
        else if (splitInput.get(0).equals("todo")) {
            try {
                storeList.add(new Todo(str.substring(5)));
                addTask(storeList.get(storeList.size() - 1));
            }
            catch (StringIndexOutOfBoundsException e) {
                throw new DukeException("OOPS! The description of"
                        + " a todo list cannot be empty");
            }
        }

        //adding a task that has a deadline
        else if (splitInput.get(0).equals("deadline")) {
            int i;
            int k = 0;
            String split1 = "";
            String split2 = "";
            if (splitInput.size() == 1) {
                throw new DukeException("OOPS! The description of deadline"
                       + " cannot be empty");
            }
            for (i = 1; i < splitInput.size(); i++) {
                if (splitInput.get(i).equals("/by")) {
                    k = 1;
                    continue;
                }
                if (k == 0) {
                    split1 += splitInput.get(i) + " ";
                } else {
                    split2 += splitInput.get(i) + " ";
                }
            }
            if (k == 0) {
                throw new DukeException("Please make sure that you have "
                        + "entered \"/by\" to separate task and time");
            }
            storeList.add(new Deadline(split1.trim(), split2.trim()));
            addTask(storeList.get(storeList.size() - 1));
        }

        //adding an event to the list
        else if (splitInput.get(0).equals("event")) {
            int i;
            int k = 0;
            String split1 = "";
            String split2 = "";
            if (splitInput.size() == 1) {
                throw new DukeException("OOPS! the description for an "
                      + "event cannot be empty");
            }
            for (i = 1; i < splitInput.size(); i++) {
                if (splitInput.get(i).equals("/at")) {
                    k = 1;
                    continue;
                }
                if (k == 0) {
                    split1 += splitInput.get(i) + " ";
                } else {
                    split2 += splitInput.get(i) + " ";
                }
            }
            if (k == 0) {
                throw new DukeException("Please make sure you have used "
                       +  "\"at\" to separate task and time");
            }
            storeList.add(new Event(split1.trim(), split2.trim()));
            addTask(storeList.get(storeList.size() - 1));
        } else {
            throw new DukeException("Please enter a valid command");
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
