package ui;

import task.TaskList;
import task.Task;
import java.util.Scanner;

public class Ui {
	private Scanner in = new Scanner(System.in);

	public String readCommand() {
		return in.nextLine();
	}

	public void showWelcome(){
	    String logo = " ____        _        \n"
	            + "|  _ \\ _   _| | _____ \n"
	            + "| | | | | | | |/ / _ \\\n"
	            + "| |_| | |_| |   <  __/\n"
	            + "|____/ \\__,_|_|\\_\\___|\n";
	    System.out.println("Hello from\n" + logo);
	    System.out.println("What can I do for you?");
	}

	public void showLine() {
		System.out.println("------------------------------------------------------------------------------------------------------");
	}

	public void showExit() {
		System.out.println("Bye. Hope to see you again soon!");
	}

	public void showAdd(TaskList list) {
		System.out.println("Got it. I've added this task:");
		Task t = list.get(list.size() -1);
		System.out.println("  " + t);
		System.out.println("Now you have " + list.size() + " tasks in the list.");
	}	

	public void showList(TaskList list) {
		System.out.println("Here are the tasks in your list: ");
		for(int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "." + list.get(i));
		}
	}

	public void showDone(Task finished) {
		System.out.println("Nice! I've marked this task as done:");
		System.out.println(finished);
	}

	public void showRemove(Task finished, TaskList list) {
		System.out.println("Nice! I've removed this task:");
		System.out.println(finished);
		System.out.println("Now you have " + list.size() + " tasks in the list.");
	}

	public void showError(String error) {
		System.out.println(error);
	}

	public void showLoadingError() {
		showLine();
		System.out.println("OOPS!!! IO error encountered when reading from D:\\codes\\java\\duke\\data\\duke.txt");
	}

	public void showHelp() {
		String help = "Currently we support the following commands:\n"
				+ "Manipulate tasks:\n"
				+ String.format("%1$-50s", "  'Todo <description>'") + "add a Todo task with description\n"
				+ String.format("%1$-50s", "  'Deadline <description> /by <dd/mm/yy>'") + "add a Deadline task with description and time\n"
				+ String.format("%1$-50s", "  'Event <description> /by <dd/mm/yy>'") + "add a Event task with description and time\n"
				+ String.format("%1$-50s", "  'Done <number>'") + "mark the nth task as finished\n"
				+ "Show task list:\n"
				+ String.format("%1$-50s", "  'Todo <description>'") + "show the list of tasks\n"
				+ "Miscellanious:\n"
				+ String.format("%1$-50s", "  'bye' OR 'exit' OR 'quit'") + "to exit the programme\n"
				+ String.format("%1$-50s", "  'help'") + "to show a list of available command\n";
		System.out.print(help);
	}
}