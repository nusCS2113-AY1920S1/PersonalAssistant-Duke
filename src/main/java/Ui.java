import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private Scanner sc;

    public Ui() {
        sc = new Scanner(System.in);
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void showError(String e) {
        System.out.println( "☹  OOPS!!!" + e);
    }

    public void taskAdded(Task t, int size) {
        System.out.println("Got it. I've added this task: \n  " + t.toString() + "\nNow you have "
                + size + " tasks in the list.");
    }

    public void markedAsDone(Task t) {
        System.out.println("Nice! I've marked this task as done: \n  " + t.printStatus());
    }

    public void taskRemoved(Task t, int size) {
        System.out.println("Noted. I've removed this task: \n  " + t.toString() + "\nNow you have "
                + size + " tasks in the list.");
    }

    public void taskFound(ArrayList<Task> a, String name){
        System.out.println("Here are the matching tasks in your list:");
        int count = 1;
        for (Task x : a) {
            if (x.getDescription().contains(name)) {
                System.out.println(count + "." + x.toString());
                count++;
            }
        }
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void listTasks(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i <= tasks.getSize() ; i++)
        {
            System.out.println( i + "." + tasks.getTask(i).toString());
        }
    }

    public void exitInformation(){
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Duke\nWhat can I do for you?");
    }

    public void showLoadingError() {
        System.out.println("Failed to Load from local text file!");
    }
}
