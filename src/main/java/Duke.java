import java.util.ArrayList;
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
            String[] words = line.split(" ");

            if (words[0].equals("done")) {
                if (words.length == 1) {
                    printIndented("Please supply a number. Eg: done 2");
                } else {
                    try {
                        int taskNo = Integer.parseInt(words[1]);
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
            } else {
                Task task = new Task(line);
                tasks.add(task);
                printIndented("added: " + line);
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
