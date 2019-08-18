import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    private static List<String> tasks = new ArrayList<>();

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
            for (String task : tasks) {
                printIndented(counter++ + ". " + task);
            }
        } else {
            tasks.add(line);
            printIndented("added: " + line);
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
