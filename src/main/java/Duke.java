import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Duke {
    private static ArrayList<Task> storeList = new ArrayList<>();
    private static FileHandling dataStorage = new FileHandling("storeData.txt");

    private static void printDash() {
        String str = "";
        for (int i = 0; i < 75; i++) {
            str += "_";
        }
        printSpaces(str);
    }

    private static void printSpaces(String printStr) {
        System.out.println("    " + printStr);
    }

    private static void printList() {
        printSpaces(" Here are the tasks in your list:");
        for (int i = 0; i < storeList.size(); i++) {
            printSpaces(" " + (i + 1) + ". " + storeList.get(i).toString());
        }
        printDash();
    }

    private static void printTaskDone(int num) {
        storeList.get(num).markAsDone();
        printSpaces(" Nice! I have marked this task as done:");
        printSpaces(" " + storeList.get(num).toString());
        printDash();
    }

    private static void addTask(Task taskA) {
        printSpaces(" Got it. I have added this task:");
        printSpaces("  " + taskA.toString());
        printSpaces(" Now you have " + storeList.size()
               + ((storeList.size() == 1) ? " task in the list." : " tasks in "
                + "the list"));
        printDash();
    }

    private static void takeInput() {
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.nextLine();
            if (str.equals("bye")) {
                printDash();
                printSpaces(" Bye.Hope to see you again soon.");
                printDash();
                break;
            }
            try {
                processInput(str);
            } catch (DukeException e) {
                printSpaces(e.getMessage());
                printDash();
            }
        }
    }

    private static void processInput(String str) throws DukeException {
        //To process the various forms on input given to DUKE
        printDash();
        List<String> splitInput = new ArrayList<String>(
                Arrays.asList(str.split(" ")));
        if (str.equals("list")) { //To print the list
            printList();
        } else if (splitInput.get(0).equals("done")) { //marking task as done
            try {
                int temp = Integer.parseInt(splitInput.get(1));
                printTaskDone(temp - 1);
            } catch (NumberFormatException obj) {
                throw new DukeException(" OOPS! Enter a positive integer after \"done\"");
            } catch (IndexOutOfBoundsException obj) {
                throw new DukeException(" OOPS! Enter a number that is present in the list");
            }
        } else if (splitInput.get(0).equals("todo")) { //adding a task that has no timing constraints
            try {
                storeList.add(new Todo(str.substring(5)));
                addTask(storeList.get(storeList.size() - 1));
            } catch (StringIndexOutOfBoundsException e) {
                throw new DukeException(" OOPS! The description of a todo list cannot be empty");
            }
        } else if (splitInput.get(0).equals("deadline")) { //adding a task that has a deadline
            int i;
            int k = 0;
            String split1 = "";
            String split2 = "";
            if (splitInput.size() == 1) {
                throw new DukeException(" OOPS! The description of deadline cannot be empty");
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
                throw new DukeException(" Please make sure that you have entered \"/by\" "
                        + "to separate task and time");
            }
            storeList.add(new Deadline(split1.trim(), split2.trim()));
            addTask(storeList.get(storeList.size() - 1));
        } else if (splitInput.get(0).equals("event")) { //adding event to the list
            int i;
            int k = 0;
            String split1 = "";
            String split2 = "";
            if (splitInput.size() == 1) {
                throw new DukeException(" OOPS! the description for an event cannot be empty");
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
                throw new DukeException(" Please make sure you have used \"/at\" to separate"
                        + " task and time");
            }
            storeList.add(new Event(split1.trim(), split2.trim()));
            addTask(storeList.get(storeList.size() - 1));
        } else if (splitInput.get(0).equals("delete")) { //To delete a task
            try {
                int temp = Integer.parseInt(splitInput.get(1));
                String del = storeList.get(temp - 1).toString();
                printSpaces(" Noted. I have removed this task:");
                printSpaces( "  " + del);
                storeList.remove(temp - 1);
                printSpaces(" Now you have " + storeList.size()
                       + ((storeList.size() == 1) ? " task in the list." : " tasks in "
                        + "the list"));
                printDash();
            } catch (NumberFormatException obj) {
                throw new DukeException(" OOPS! Enter a positive integer after \"delete\"");
            } catch (IndexOutOfBoundsException obj) {
                throw new DukeException(" OOPS! Enter a number that is present in the list");
            }
        } else if (splitInput.get(0).equals("find")) { // can check for a word, letter or a part of a word
            if (splitInput.size() == 1) {
                throw new DukeException(" Please enter a keyword after join");
            }
            String keyword = String.join("", splitInput.subList(1, splitInput.size()));
            ArrayList<Task> containsKeyword = new ArrayList<Task>();
            for (int i = 0; i < storeList.size(); i++) {
                if (storeList.get(i).checkKeyword(keyword)) {
                    containsKeyword.add(storeList.get(i));
                }
            }
            printDash();
            printSpaces(" Here are the matching tasks in your list:");
            for (int i = 0; i < containsKeyword.size(); i++) {
                printSpaces(" " + (i + 1) + ". " + containsKeyword.get(i).toString());
            }
            printDash();
        } else {
            throw new DukeException(" Please enter a valid command");
        }
        dataStorage.saveData(storeList);
    }

    /**
     * Duke is a chat bot which can store/respond to specific commands
     */
    public static void main(String[] args)throws DukeException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        printSpaces(" Hello from\n" + logo);
        printDash();
        printSpaces(" Hello! I am Duke");
        printSpaces(" What can I do for you?");
        printDash();
        storeList = dataStorage.retrieveData();
        takeInput();

    }
}
