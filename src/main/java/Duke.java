import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Duke {
    private static List<Task> tasks = new ArrayList<>();

    private static Storage storage = new Storage("tasks.dat");

    private static void printIndented(String qn) {
        System.out.println("    " + qn);
    }

    private static void printHR() {
        printIndented("____________________________________________________________");
    }

    private static void printNumTasksMessage() {
        printIndented("Now you have "
                + tasks.size()
                + (tasks.size() > 1 ? " tasks" : " task")
                + " in the list.");
    }

    private static void printTaskAddedMessage(Task task) {
        printIndented("Got it . I've added this task:");
        printIndented("  " + task);
        printNumTasksMessage();
    }

    private static void eval(String line) throws DukeException {
        List<String> words = Arrays.asList(line.split(" "));

        // list all tasks
        if (words.get(0).equals("list") && words.size() == 1) {
            printIndented("Here are the tasks in your list:");
            int counter = 1;
            for (Task task : tasks) {
                printIndented(counter++ + ". " + task);
            }

            // Delete tasks
        } else if (words.get(0).equals("delete")) {
            try {
                int taskNo = Integer.parseInt(words.get(1));
                Task toRemove = tasks.get(taskNo - 1);
                tasks.remove(toRemove);

                printIndented("Noted. I've removed this task:");
                printIndented("  " + toRemove);
                printNumTasksMessage();
            } catch (NumberFormatException e) {
                throw new DukeException("Please supply a number. Eg: done 2");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Please supply a valid number.");
            }

            // Mark tasks as done
        } else if (words.get(0).equals("done")) {
            try {
                int taskNo = Integer.parseInt(words.get(1));
                tasks.get(taskNo - 1).markAsDone();
                printIndented("Nice! I've marked this task as done:");
                printIndented("  " + tasks.get(taskNo - 1));
            } catch (NumberFormatException e) {
                throw new DukeException("Please supply a number. Eg: done 2");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Please supply a valid number.");
            }

            // Add todo tasks
        } else if (words.get(0).equals("todo")) {
            Task task = new Todo(words.subList(1, words.size()));
            tasks.add(task);
            printTaskAddedMessage(task);

            // Add deadline tasks
        } else if (words.get(0).equals("deadline")) {
            Task task = new Deadline(words.subList(1, words.size()));
            tasks.add(task);
            printTaskAddedMessage(task);

            // Add event task
        } else if (words.get(0).equals("event")) {
            Task task = new Event(words.subList(1, words.size()));
            tasks.add(task);
            printTaskAddedMessage(task);

        } else {
            throw new DukeException("Please enter a valid command.");
        }

        storage.save(tasks);
    }

    private static void repl() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                return;
            } else {
                printHR();
                try {
                    eval(input);
                } catch (DukeException e) {
                    printIndented(e.getMessage());
                }
                printHR();
            }
        }
    }

    public static void main(String[] args) {
        printHR();
        printIndented("Hello! I'm Duke");
        printIndented("What can I do for you?");
        printHR();

        tasks = storage.load();

        repl();

        printHR();
        printIndented("Bye. Hope to see you again soon!");
        printHR();
    }
}
