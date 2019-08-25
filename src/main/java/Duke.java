import Model_Classes.Deadline;
import Model_Classes.Event;
import Model_Classes.Task;
import Model_Classes.ToDo;

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
            String temp = userInput.next().toLowerCase();
            switch (temp) {
                case "bye" :
                    // user indicated bye to exit duke
                    isExit = true;
                    System.out.println(LINE_BREAK);
                    System.out.println("    " + temp);
                    System.out.println(LINE_BREAK);
                    userInput.close();
                    break;
                case "list" :
                    // user indicates to list items, iterate through toDoList and print
                    int listCount = 1;
                    System.out.println(LINE_BREAK);
                    for (Task output : toDoList) {
                        System.out.println("    " + listCount + ". " + output.getStatusIcon() + output.getDescription());
                        listCount += 1;
                    }
                    System.out.println(LINE_BREAK);
                    break;
                case "done" :
                    String indexString = userInput.next();
                    int index = Integer.parseInt(indexString) - 1;
                    toDoList.get(index).setDone();
                    Task tempTask = toDoList.get(index);
                    System.out.println(LINE_BREAK);
                    System.out.println("    " + "Nice! I've marked this task as done");
                    System.out.println("    " + tempTask.getStatusIcon() + " " + tempTask.getDescription());
                    System.out.println(LINE_BREAK);
                    break;
                case "todo" :
                    temp = userInput.nextLine();
                    ToDo newToDo = new ToDo(temp);
                    toDoList.add(newToDo);
                    System.out.println(LINE_BREAK);
                    System.out.println("    " + "Got it. I've added this task to the list");
                    System.out.println("      " + newToDo.toString());
                    System.out.println("    You now have " + toDoList.size() + " tasks in the list");
                    System.out.println(LINE_BREAK);
                    break;
                case "deadline" :
                    temp = userInput.nextLine();
                    String[] splitDeadline = temp.split("/");
                    Deadline newDeadline = new Deadline(splitDeadline[0], splitDeadline[1]);
                    toDoList.add(newDeadline);
                    System.out.println(LINE_BREAK);
                    System.out.println("    " + "Got it. I've added this task to the list");
                    System.out.println("      " + newDeadline.toString());
                    System.out.println("    You now have " + toDoList.size() + " tasks in the list");
                    System.out.println(LINE_BREAK);
                    break;
                case "event" :
                    temp = userInput.nextLine();
                    String[] splitEvent = temp.split("/");
                    Event newEvent = new Event(splitEvent[0], splitEvent[1]);
                    toDoList.add(newEvent);
                    System.out.println(LINE_BREAK);
                    System.out.println("    " + "Got it. I've added this task to the list");
                    System.out.println("      " + newEvent.toString());
                    System.out.println("    You now have " + toDoList.size() + " tasks in the list");
                    System.out.println(LINE_BREAK);
                    break;
            }
        }
    }
}
