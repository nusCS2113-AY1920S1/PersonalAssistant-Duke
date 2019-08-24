import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String separator = "____________________________________________________________";
        System.out.println("Hello from\n" + logo);
        System.out.println(separator);
        System.out.println("Hello! I'm Duke"); //introduction
        System.out.println("What can I do for you?");
        System.out.println(separator);

        String myString = inputCommand();
        String

        // as long as input is not bye, keep running
        while (!myString.equals("bye")) {
            System.out.println(separator);
            System.out.println("Added: " + myString);
            System.out.println(separator);
            myString = inputCommand();
            if (myString.equals("bye")) { //end duke
                System.out.println(separator);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            }
            else if (myString.equals("list")) { //request for list, output the storage
                System.out.println(separator);
                for (int i = 0; i < length)
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(separator);
            }
        }
    }

    private static String inputCommand() {
        Scanner input = new Scanner(System.in);

        return input.next();
    }
}


