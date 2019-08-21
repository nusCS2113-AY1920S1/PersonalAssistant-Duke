import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    /**
     * Main class.
     * Level 1 Completed
     *
     * @param args Refers to CLI arguments
     */
    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo);
        ArrayList<String> library = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");

        while (true) {
            String command = sc.nextLine();
            if (command.equals("list")) {
                for (int i = 0; i < library.size(); i++) {
                    System.out.print(i + 1);
                    System.out.println(". " + library.get(i));
                }
            } else if (command.equals("bye")) {
                break;
            } else {
                library.add(command);
                System.out.println("added: " + command);
            }
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}
