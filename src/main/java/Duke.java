import controllers.TaskFactory;
import models.ITask;

import java.io.IOException;
import java.util.Scanner;

public class Duke {
    /**
     * Main class.
     *
     * @param args Refers to CLI arguments
     */
    public static void main(String[] args) throws IOException {
        /*String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);*/
        TaskList taskList = new TaskList();
        TaskFactory taskFactory = new TaskFactory();
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");

        while (true) {
            String command = sc.nextLine();
            if (command.equals("list")) {
                taskList.showAllTasks();
            } else if (command.equals("bye")) {
                break;
            } else if (command.contains("done")) {
                String[] allInputs = command.split(" ");
                System.out.println("Nice! I've marked this task as done:");

                for (String i : allInputs) {
                    if (!i.equals("done")) {
                        int index = Integer.parseInt(i) - 1;
                        ITask chosenToDos = taskList.getTask(index);
                        chosenToDos.markAsDone();
                        System.out.println("[" + chosenToDos.getStatusIcon() + "] " + chosenToDos.getDescription());
                    }
                }
            } else {
                ITask newTask = taskFactory.createTask(command);
                taskList.addToList(newTask);
                System.out.println("Got it. I've added this task:");
                System.out.print("\t");
                System.out.println("[" + newTask.getInitials() + "]"
                        + "[" + newTask.getStatusIcon() + "] "
                        + newTask.getDescription()
                );
                String grammerTasks = taskList.getNumOfTasks() > 1 ? "tasks" : "task";
                System.out.println("Now you have " + taskList.getNumOfTasks() + " " + grammerTasks + " in your list.");
            }
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}
