import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Duke {
    private static List<Task> tasks = new ArrayList<>();

    private static void printIndented(String qn) {
        System.out.println("    " + qn);
    }

    private static void printHR() {
        printIndented("____________________________________________________________");
    }

    private static void eval(String line) {
        printHR();
        if (line.equals("list")) {
            int counter = 1;
            for (Task task : tasks) {
                printIndented(counter++ + ". " + task);
            }
        } else {
            List<String> words = Arrays.asList(line.split(" "));

            if (words.get(0).equals("done")) {
                if (words.size() == 1) {
                    printIndented("Please supply a number. Eg: done 2");
                } else {
                    try {
                        int taskNo = Integer.parseInt(words.get(1));
                        if (taskNo > tasks.size() || taskNo <= 0) {
                            printIndented("Please enter a valid task number.");
                        } else {
                            tasks.get(taskNo - 1).markAsDone();
                            printIndented("Nice! I've marked this task as done:");
                            printIndented("  " + tasks.get(taskNo - 1));
                        }
                    } catch (NumberFormatException e) {
                        printIndented("Please supply a number. Eg: done 2");
                    }
                }

            } else if (words.get(0).equals("todo")) {
                Task task = new Todo(words.subList(1, words.size()));
                tasks.add(task);

                printIndented("Got it . I've added this task:");
                printIndented("  " + task);
                printIndented("Now you have "
                        + tasks.size()
                        + (tasks.size() > 1 ? " tasks" : " task")
                        + " in the list.");

            } else {
                printIndented("Please enter a valid command.");
            }
        }
        printHR();
    }

    private static void repl() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                return;
            } else {
                eval(input);
            }
        }
    }

    public static void main(String[] args) {
        printHR();
        printIndented("Hello! I'm Duke");
        printIndented("What can I do for you?");
        printHR();

        repl();

        printHR();
        printIndented("Bye. Hope to see you again soon!");
        printHR();
    }
}
