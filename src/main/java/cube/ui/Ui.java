/**
 * This class is used as interface with the user. It could reads from user and print message to the user.
 *
 * @author tygq13
 */
package cube.ui;

import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.task.TaskList;
import cube.task.Task;
import cube.util.Parser;
import java.util.Scanner;
import java.util.Date;


public class Ui {
	private Scanner in = new Scanner(System.in);

	/**
	 * Returns the next line of user input being read.
	 *
	 * @return next line of user input.
	 */
	public String readCommand() {
		return in.nextLine();
	}

	//@@author LL-Pengfei
	/**
	 * Prints the welcome screen of Cube.
	 */
	public void showWelcome(){
	    String logo = " ________  ___  ___  ________  _______      \n" +
				"|\\   ____\\|\\  \\|\\  \\|\\   __  \\|\\  ___ \\     \n" +
				"\\ \\  \\___|\\ \\  \\\\\\  \\ \\  \\|\\ /\\ \\   __/|    \n" +
				" \\ \\  \\    \\ \\  \\\\\\  \\ \\   __  \\ \\  \\_|/__  \n" +
				"  \\ \\  \\____\\ \\  \\\\\\  \\ \\  \\|\\  \\ \\  \\_|\\ \\ \n" +
				"   \\ \\_______\\ \\_______\\ \\_______\\ \\_______\\\n" +
				"    \\|_______|\\|_______|\\|_______|\\|_______|\n";
	    System.out.println("Hello from\n" + logo);
	    System.out.println("What can I do for you?");
	}

	/**
	 * Prints a dotted line with new line.
	 */
	public void showLine() {
		System.out.println("------------------------------------------------------------------------------------------------------");
	}

	/**
	 * Prints the exit message of Cube.
	 */
	public void showExit() {
		System.out.println("Bye. Hope to see you again soon!");
	}

	/**
	 * Prints the information of the task added.
	 *
	 * @param list the list of tasks stored in Cube.
	 */
	public void showAdd(TaskList list) {
		System.out.println("Got it. I've added this task:");
		Task t = list.get(list.size() -1);
		System.out.println("  " + t);
		System.out.println("Now you have " + list.size() + " tasks in the list.");
	}

	public void showAddFood(FoodList list) {
		System.out.println("Got it. I've added this food:");
		Food food = list.get(list.size() -1);
		System.out.println("  " + food);
		System.out.println("Now you have " + list.size() + " food in the list.");
	}

	/**
	 * Prints each task in the task list.
	 *
	 * @param list the list of tasks stored in Cube.
	 */
	public void showList(TaskList list) {
		System.out.println("Here are the tasks in your list: ");
		for(int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "." + list.get(i));
		}
	}

	public void showListFood(FoodList list) {
		System.out.println("Here are the food in your list: ");
		for(int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "." + list.get(i));
		}
	}

	/**
	 * Prints the list of task with specified keyword.
	 *
	 * @param list the list of task stored in Cube.
	 */
	public void showFind(TaskList list) {
		System.out.println("Here are the matching tasks in your list:");
		for(int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "." + list.get(i));
		}
	}

	/**
	 * Prints the message of task being marked as done.
	 *
	 * @param finished the task being marked as done.
	 */
	public void showDone(Task finished) {
		System.out.println("Nice! I've marked this task as done:");
		System.out.println(finished);
	}

	//@@author LL-Pengfei
	/**
	 * Show Snooze Message.
	 *
	 * @param snoozed the task snoozed.
	 */
	public void showSnooze(Task snoozed) {
		System.out.println("Here is the task snoozed for 24 hours:");
		System.out.println(snoozed);
	}
	//@@author

	/**
	 * Prints the list of food products whose expiry is in the next 7 days.
	 *
	 * @param list the list of food products nearing expiry.
	 */
	public void showExpiryReminder(FoodList list) {
		System.out.println("Here are the upcoming expiry dates:");
		for(int i = 0; i < list.size(); i++) {
			Food food = list.get(i);
			System.out.println(food.getName() + " due in " + Parser.parseDateToString(food.getExpiryDate()));
		}
	}

	/**
	 * Prints the list of food products that are low on stock.
	 *
	 * @param list the list of food products that are low on stock.
	 */
	public void showStockReminder(FoodList list) {
		System.out.println("Here are the food products that are low in stock:");
		for(int i = 0; i < list.size(); i++) {
			Food food = list.get(i);
			System.out.println(food.getName() + ": " + food.getStock() + " left");
		}
	}

	/**
	 * Prints the message of a task being removed.
	 *
	 * @param removed the task being removed.
	 * @param list the list of task stored in Cube.
	 */
	public void showRemove(Task removed, TaskList list) {
		System.out.println("Nice! I've removed this task:");
		System.out.println(removed);
		System.out.println("Now you have " + list.size() + " tasks in the list.");
	}

	public void showDelete(Food removed, FoodList list) {
		System.out.println("Nice! I've removed this food:");
		System.out.println(removed);
		System.out.println("Now you have " + list.size() + " food in the list.");
	}

	public void showSold(String foodName, int quantity, double profit, double revenue) {
		String display = String.format("%d of %s sold\n", quantity, foodName);
		display += String.format("you have earn %f, the total revenue is %f", profit, revenue);
		System.out.println(display);
	}

	/**
	 * Prints the message of a task being removed.
	 *
	 * @param removed the task being removed.
	 * @param list the list of task stored in Cube.
	 */
	public void showFreeDay(Date date) {
		System.out.println("Your next free day is: " + Parser.parseDateToString(date));
	}

	/**
	 * Prints the error message.
	 *
	 * @param error the error message.
	 */
	public void showError(String error) {
		System.out.println(error);
	}

	/**
	 * Prints the error message of loading error.
	 */
	public void showLoadingError(String path) {
		showLine();
		System.out.println(Message.IO_ERROR + path);
	}

	/**
	 * Prints the list of available command and their usage.
	 */
	public void showHelp() {
		String help = "Currently we support the following commands: (all command ignore cases)\n"
				+ "Manipulate tasks:\n"
				+ String.format("%1$-50s", "  'add -n <food name> -t <food type> -p <price> -s <stock> -e <expiry date>' ") + "add a new food product with all details/description\n"
				+ String.format("%1$-50s", "  'delete -i <number>'") + "delete the nth food product in the list\n"
				+ String.format("%1$-50s", "  'sold -n <food name> -q <quantity>'") + "marks quantity q of product n sold\n"
				//+ String.format("%1$-50s", "  'Delete <number>'") + "delete the nth task in the list\n"
				+ "Show task list:\n"
				+ String.format("%1$-50s", "  'list'") + "shows the list of food products\n"
				+ String.format("%1$-50s", "  'Reminder'") + "show the list of food products that are low on stock and/or are approaching its expiry date\n"
				//+ String.format("%1$-50s", "  'List'") + "show the list of tasks\n"
				//+ String.format("%1$-50s", "  'Find <keywords>'") + "show the list of tasks with specified keywords\n"
				//+ String.format("%1$-50s", "  'FreeTime <number>'") + "show the nearest day with free time of n hours\n"
				//+ String.format("%1$-50s", "  'ViewSchedule /at <dd/mm/yy>'") + "show the list of tasks that falls within specified date\n"
				+ "Miscellaneous:\n"
				+ String.format("%1$-50s", "  'bye' OR 'exit' OR 'quit'") + "to exit the programme\n"
				+ String.format("%1$-50s", "  'help'") + "to show a list of available command\n";
		System.out.print(help);
	}
}