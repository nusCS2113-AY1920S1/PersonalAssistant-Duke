import java.util.List;

public class Ui {
    public static void print_line() {
        for (int i = 0; i < 55; ++i) {
            System.out.print("\u2500");
        }
        System.out.print("\n");
    }

    public static void printMarkDone(Task currTask) {
        print_line();
        System.out.println(" Nice! I've marked this task as done:\n" + currTask.toString());
        print_line();
    }

    public static void printDeleted(Task currTask, List<Task> taskList) {
        print_line();
        System.out.println("Noted. I've removed this task:\n" + currTask.toString() + "\nNow you have " + (taskList.size() - 1) + " tasks in the list.");
        print_line();
    }

    public static void printBye() {
        print_line();
        System.out.println("Bye. Hope to see you again soon!");
        print_line();
    }

    public static void printList(List<Task> taskList) {
        print_line();
        System.out.println("Here are the tasks in your list:");
        int counter = 1;
        for (Task pastTask : taskList) {
            System.out.println(counter + "." + pastTask.toString());
            ++counter;
        }
        print_line();
    }

    public static void printTodo(Todo todo, List<Task> taskList) {
        print_line();
        System.out.println("Got it. I've added this task:\n" + todo.toString() + "\nNow you have " + taskList.size() + " tasks in the list.\n");
        print_line();
    }

    public static void printDeadline(Deadline deadline, List<Task> taskList) {
        print_line();
        System.out.println("Got it. I've added this task:\n" + deadline.toString() + "\nNow you have " + taskList.size() + " tasks in the list.\n");
        print_line();
    }

    public static void printEvent(Event event, List<Task> taskList) {
        Ui.print_line();
        System.out.println("Got it. I've added this task:\n" + event.toString() + "\nNow you have " + taskList.size() + " tasks in the list.\n");
        Ui.print_line();

    }
}
