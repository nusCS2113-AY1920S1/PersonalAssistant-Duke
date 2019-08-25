import Model_Classes.Deadline;
import Model_Classes.Event;
import Model_Classes.Task;
import Model_Classes.ToDo;

import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
   public static final String LINE_BREAK = "    -----------------------------------------------------------";
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
                    System.out.println("    Goodbye! See you next time!");
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
                    temp = userInput.nextLine();
                    System.out.println(LINE_BREAK);
                    try {
                        int index = Integer.parseInt(temp.substring(1)) - 1;
                        toDoList.get(index).setDone();
                        Task tempTask = toDoList.get(index);
                        System.out.println("    " + "Nice! I've marked this task as done");
                        System.out.println("    " + tempTask.getStatusIcon() + " " + tempTask.getDescription());
                    } catch (StringIndexOutOfBoundsException S) {
                        System.out.println("    Sorry, please enter the number of the task completed");
                        System.out.println("    Input the description in this manner :\n" + "       done 1 ");
                    }
                    System.out.println(LINE_BREAK);
                    break;
                case "todo" :
                    temp = userInput.nextLine();
                    System.out.println(LINE_BREAK);
                    if (!temp.equals("")) {
                        ToDo newToDo = new ToDo(temp);
                        toDoList.add(newToDo);
                        System.out.println("    " + "Got it. I've added this task to the list");
                        System.out.println("      " + newToDo.toString());
                        System.out.println("    You now have " + toDoList.size() + " tasks in the list");
                    } else {
                        System.out.println("    Sorry, please enter the description of the task");
                        System.out.println("    Input the description in this manner :\n" + "       todo Example Task ");
                    }
                    System.out.println(LINE_BREAK);
                    break;
                case "deadline" :
                    temp = userInput.nextLine();
                    System.out.println(LINE_BREAK);
                    String[] splitDeadline = temp.split("/");
                    try {
                        Deadline newDeadline = new Deadline(splitDeadline[0], splitDeadline[1]);
                        toDoList.add(newDeadline);
                        System.out.println("    " + "Got it. I've added this task to the list");
                        System.out.println("      " + newDeadline.toString());
                        System.out.println("    You now have " + toDoList.size() + " tasks in the list");
                    } catch (ArrayIndexOutOfBoundsException A) {
                        System.out.println("    Sorry, the description of the deadline is wrongly set");
                        System.out.println("    Input the description in this manner :\n" + "       deadline Example Task / Date to be finished");
                    }
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
                default:
                    // invalid command, prompt for a correct command
                    System.out.println(LINE_BREAK);
                    System.out.println("    I'm sorry, I don't understand this command....");
                    System.out.println("    Try typing todo, deadline or event followed by the task description!");
                    System.out.println(LINE_BREAK);
                    break;
            }
        }
    }
}
