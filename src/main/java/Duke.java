import java.util.ArrayList;
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
        ArrayList<String> toDoList = new ArrayList<>();

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
            } else if (temp.toLowerCase().equals("list")) {
                // user indicates to list items, iterate through toDoList and print
                int listCount = 1;
                System.out.println(LINE_BREAK);
                for (String output : toDoList) {
                    System.out.println(listCount + ". " + output);
                    listCount += 1;
                }
                System.out.println(LINE_BREAK);
            } else {
                // user is not exiting duke, indicate item has been added
                toDoList.add(temp);
                System.out.println(LINE_BREAK);
                System.out.println("added: " + temp);
                System.out.println(LINE_BREAK);
            }
        }
    }
}
