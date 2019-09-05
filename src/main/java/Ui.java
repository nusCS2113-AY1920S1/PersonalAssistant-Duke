import java.util.Scanner;
import java.util.ArrayList;

public class Ui {

    public String readCommand() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public void printDash() {
        String str = "";
        for (int i = 0; i < 75; i++) {
            str += "_";
        }
        printSpaces(str);
    }

    public void printSpaces(String printStr) {
        System.out.println("    " + printStr);
    }

    public void showWelcome() {
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
    }

    public void printList(ArrayList<Task> printData) {
        printSpaces(" Here are the tasks in your list:");
        showList(printData);
    }

    public void printMarkAsDone(String printStr) {
        printSpaces(" Nice! I have marked this task as done:");
        printSpaces(" " + printStr);
        printDash();
    }

    public void printAddTask(ArrayList<Task> listOfTasks, String taskA) {
        printSpaces(" Got it. I have added this task:");
        printSpaces("  " + taskA);
        showNumTasks(listOfTasks);
    }

    private void showNumTasks(ArrayList<Task> listOfTasks) {
        printSpaces(" Now you have " + listOfTasks.size()
                + ((listOfTasks.size() == 1) ? " task in the list." : " tasks in "
                + "the list"));
        printDash();
    }

    private void showList(ArrayList<Task> listOfTasks) {
        for (int i = 0; i < listOfTasks.size(); i++) {
            printSpaces(" " + (i + 1) + ". " + listOfTasks.get(i).toString());
        }
        printDash();
    }

    public void exitDuke() {
        printSpaces(" Bye.Hope to see you again soon.");
        printDash();
    }

    public void showError(String errorMessage) {
        printSpaces(errorMessage);
        printDash();
    }

    public void deleteMessage(ArrayList<Task> taskList, String taskA) {
        printSpaces(" Noted. I have removed this task:");
        printSpaces("  " + taskA);
        showNumTasks(taskList);
    }

    public void keywordPrint(ArrayList<Task> taskList) {
        printSpaces(" Here are the matching tasks in your list:");
        showList(taskList);
    }

    public void showLoadingError(String message) {
        printDash();
        printSpaces(message);
        printDash();
    }
}