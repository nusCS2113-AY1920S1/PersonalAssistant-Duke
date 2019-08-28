package views;

import controllers.ConsoleInputController;
import models.ITask;
import models.TaskList;

import java.io.IOException;
import java.util.Scanner;

public class CLIView {
    private final String LOGO = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
    private final String HORILINE = "\t____________________________________________________________";

    private ConsoleInputController consoleInputController;

    public CLIView() {
        this.consoleInputController = new ConsoleInputController(this);
    }

    public void start() throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println(HORILINE);
        System.out.println("\tHello! I'm Duke");
        System.out.println("\tWhat can I do for you?");
        System.out.println(HORILINE);

        while (true) {
            String command = sc.nextLine();
            consoleInputController.onCommandReceived(command);
        }
    }

    public void end() {
        System.out.println("\t Bye. Hope to see you again soon!");
        System.exit(0);
    }

    public void addMessage(ITask newTask, TaskList taskList) {
        System.out.println(HORILINE);
        System.out.println("\tGot it. I've added this task:");
        System.out.print("\t  ");
        System.out.println("[" + newTask.getInitials() + "]"
                + "[" + newTask.getStatusIcon() + "] "
                + newTask.getDescription()
        );
        String grammerTasks = taskList.getNumOfTasks() > 1 ? "tasks" : "task";
        System.out.println("\tNow you have " + taskList.getNumOfTasks() + " " + grammerTasks + " in your list.");
        System.out.println(HORILINE);
    }

    public void printAllTasks(TaskList taskList) {
        System.out.println(HORILINE);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < taskList.getAllTasks().size(); i++) {
            System.out.print("\t" + (i + 1));
            System.out.println(".[" + taskList.getAllTasks().get(i).getInitials() + "]"
                    + "[" + taskList.getAllTasks().get(i).getStatusIcon() + "] "
                    + taskList.getAllTasks().get(i).getDescription()
            );
        }
        System.out.println(HORILINE);
    }

    public void markDone(TaskList taskList, String input) {
        System.out.println(HORILINE);
        String[] allInputs = input.split(" ");
        System.out.println("\tNice! I've marked this task as done:");
        System.out.print("\t  ");
        for (String i : allInputs) {
            if (!i.equals("done")) {
                int index = Integer.parseInt(i) - 1;
                ITask chosenToDos = taskList.getTask(index);
                chosenToDos.markAsDone();
                System.out.println("[" + chosenToDos.getStatusIcon() + "] " + chosenToDos.getDescription());
            }
        }
        System.out.println(HORILINE);
    }
}
