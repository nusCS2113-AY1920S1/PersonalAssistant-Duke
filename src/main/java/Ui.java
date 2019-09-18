import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    public String readCommand() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public void showLine() {
        System.out.println("    ____________________________________________________________");
    }

    public void greet() {
        showLine();
        System.out.println(
                "     Hello! I'm" + "      ____        _        \n" +
                        "                    |  _ \\ _   _| | _____ \n" +
                        "                    | | | | | | | |/ / _ \\\n" +
                        "                    | |_| | |_| |   <  __/\n" +
                        "                    |____/ \\__,_|_|\\_\\___|\n\n" +
                        "     What can I do for you?");
        showLine();
    }

    public void goodBye() {
        System.out.println("     Bye. Hope to see you again soon!");
    }

    public void showDone(Task t) {
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("       " + t.toString());
    }

    public void showDeleted(Task t) {
        System.out.println("     Noted. I've removed this task:");
        System.out.println("       " + t.toString());
    }

    public void showFound(ArrayList<Task> foundItems) {
        if (foundItems.size() > 0) {
            System.out.println("     Here are the matching tasks in your list:");
            int count = 0;
            for (Task task : foundItems) {
                count++;
                System.out.println("     " + count + ". " + task.toString());
            }
        } else {
            System.out.println("     No task matching description. Try another keyword!");
        }
    }

    public void showAdded(Task t, TaskList tasks) {
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + t.toString());
        System.out.println("     Now you have " + tasks.getSize() + " task(s) in the list.");
    }

    public void showList(TaskList tasks) {
        System.out.println("     Here are the tasks in your list:");
        int count = 0;
        for (Task task : tasks.getList()) {
            ++count;
            System.out.println("     " + count + "." + task.toString());
        }
    }

    public void showSnooze(Task t){
        System.out.println("     Nice! I've change the date:");
        System.out.println("       " + t.toString());
    }

    public void noSnooze(Task t){
        System.out.println("     Not a deadline/event, no hurry no worry :)");
    }
}

