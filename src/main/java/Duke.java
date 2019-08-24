import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
   public static final String LINE_BREAK = "    ___________________________";
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
        ArrayList<Task> toDoList = new ArrayList<>();

        // begin repeated inputs of commands
        while (!isExit) {
            String temp = userInput.nextLine();
            if (temp.toLowerCase().equals("bye")) {
                // user indicated bye to exit duke
                isExit = true;
                System.out.println(LINE_BREAK);
                System.out.println("    " + temp);
                System.out.println(LINE_BREAK);
                userInput.close();
            } else if (temp.toLowerCase().equals("list")) {
                // user indicates to list items, iterate through toDoList and print
                int listCount = 1;
                System.out.println(LINE_BREAK);
                for (Task output : toDoList) {
                    System.out.println("    " + listCount + ". " + output.getStatusIcon() + output.getDescription());
                    listCount += 1;
                }
                System.out.println(LINE_BREAK);
            } else if (temp.toLowerCase().contains("done")) {
                // user indicates item is done, mark item as done
                String[] splitTemp = temp.split(" ");
                int index = Integer.parseInt(splitTemp[1]) - 1; // reduce by 1 to match entries in toDoList
                toDoList.get(index).setDone();
                Task tempTask = toDoList.get(index);
                System.out.println(LINE_BREAK);
                System.out.println("    " + "Nice!, I've marked this task as done");
                System.out.println("    " + tempTask.getStatusIcon() + " " + tempTask.getDescription());
                System.out.println(LINE_BREAK);
            } else {
                // user is not exiting duke, indicate item has been added
                Task newTask = new Task(temp);
                toDoList.add(newTask);
                System.out.println(LINE_BREAK);
                System.out.println("    added: " + temp);
                System.out.println(LINE_BREAK);
            }
        }
    }
}
