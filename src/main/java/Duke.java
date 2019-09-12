import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        greeting();
        userCmd();
    }

    public static void greeting() {
        String line = "_____________________________________________\n";
        System.out.println("Hello I'm Duke\n" + "What can I do for you?\n" + line);
    }

    public static void userCmd() {
        Scanner input = new Scanner(System.in);
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HHmm");
        formatDate.setLenient(false);

        while (true) {
            String userCmd = input.nextLine();
            int indexSpace = userCmd.indexOf(" ");
            String command = (indexSpace != -1) ? userCmd.substring(0, indexSpace) : userCmd;

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                System.out.println("I do not recogise this command. Please try again");
                continue;
            }
        }
    }
}
