import java.util.List;

public class Ui {
    private void printIndented(String line) {
        System.out.println("    " + line);
    }

    private void printHR() {
        printIndented("____________________________________________________________");
    }

    public void beginBlock() {
        printHR();
    }

    public void endBlock() {
        printHR();
    }

    public void showWelcome() {
        beginBlock();
        printIndented("Hello! I'm Duke");
        printIndented("What can I do for you?");
        endBlock();
    }

    public void showBye() {
        beginBlock();
        printIndented("Bye. Hope to see you again soon!");
        endBlock();
    }

    public void showError(String message) {
        printIndented(message);
    }

    private void showNumTasks(List<Task> tasks) {
        printIndented("Now you have "
                + tasks.size()
                + (tasks.size() > 1 ? " tasks" : " task")
                + " in the list.");
    }

    private void showTasks(List<Task> tasks) {
        int counter = 1;
        for (Task task : tasks) {
            printIndented(counter++ + ". " + task);
        }
    }

    public void showTaskAdded(List<Task> tasks, Task task) {
        printIndented("Got it . I've added this task:");
        printIndented("  " + task);
        showNumTasks(tasks);
    }

    public void showTaskList(List<Task> tasks) {
        printIndented("Here are the tasks in your list:");
        showTasks(tasks);
    }

    public void showSearchResult(List<Task> tasks) {
        printIndented("Here are the matching tasks in your list:");
        showTasks(tasks);
    }

    public void showDeletedTask(List<Task> tasks, Task task) {
        printIndented("Noted. I've removed this task:");
        printIndented("  " + task);
        showNumTasks(tasks);
    }

    public void showDoneTask(Task task) {
        printIndented("Nice! I've marked this task as done:");
        printIndented("  " + task);
    }
}
