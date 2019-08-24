import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) throws IOException {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String line = "-------------------------";
        System.out.println("Hello from\n" + logo + "\n" + line + "\n" + "Hello I am " +
                "Duke.");
        System.out.println("How can I help you?");

        while (true) {
            //Enter data using BufferReader
            Scanner in = new Scanner(System.in);

            String s = in.nextLine();

            // Printing the read line
            if (s.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                System.out.println(s);
            }

        }
    }
}
