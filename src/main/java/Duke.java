import java.util.Scanner;

public class Duke {
   public static final String LINE_BREAK = "___________________________";
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("How may I help you?");
        System.out.println(LINE_BREAK);
        boolean isExit = false;
        Scanner userInput = new Scanner(System.in);

        // begin repeated inputs of commands
        while (!isExit) {
            String temp = userInput.nextLine();
            if (temp.toLowerCase().equals("bye")) {
                // user indicated bye to exit duke
                isExit = true;
                System.out.println(LINE_BREAK);
                System.out.println(temp);
                System.out.println(LINE_BREAK);
                userInput.close();
            } else {
                // user is not exiting duke, echo back the command
                System.out.println(LINE_BREAK);
                System.out.println(temp);
                System.out.println(LINE_BREAK);
            }
        }
    }
}
