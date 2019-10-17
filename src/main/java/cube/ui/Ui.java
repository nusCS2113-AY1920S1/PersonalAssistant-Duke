/**
 * This class is used as interface with the user. It could reads from user and print message to the user.
 *
 * @author tygq13
 */
package cube.ui;

import cube.logic.parser.AddCommandParser;
import cube.logic.parser.Parser;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.task.TaskList;
import cube.task.Task;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.Date;
import java.util.TimeZone;


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
	 * Prints the information of the food added.
	 *
	 * @param list the list of food stored in Cube.
	 */
	//to be deleted----
	public void showAdd(TaskList list) {
		System.out.println("Got it. I've added this task:");
		Task t = list.get(list.size() -1);
		System.out.println("  " + t);
		System.out.println("Now you have " + list.size() + " tasks in the list.");
	}
	//----to be deleted

	public void showAddFood(FoodList list) {
		System.out.println("Got it. I've added this food:");
		Food food = list.get(list.size() -1);
		System.out.println("  " + food);
		System.out.println("Now you have " + list.size() + " food in the list.");
	}

	/**
	 * Prints each food in the food list.
	 *
	 * @param list the list of food stored in Cube.
	 */
	//to be deleted----
	public void showList(TaskList list) {
		System.out.println("Here are the tasks in your list: ");
		for(int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "." + list.get(i));
		}
	}
	//----to be deleted

	public void showListFood(FoodList list) {
		System.out.println("Here are the food in your list: ");
		for(int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "." + list.get(i));
		}
		System.out.println("The total revenue so far is $" + Food.getRevenue());
	}

	/**
	 * Prints the list of food with specified keyword.
	 *
	 * @param list the list of food stored in Cube.
	 */
	//to be deleted----
	public void showFind(TaskList list) {
		System.out.println("Here are the matching tasks in your list:");
		for(int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "." + list.get(i));
		}
	}
	//----to be deleted

	public void showFindFood(FoodList list) {
		System.out.println("Here are the matching food in your list:");
		for(int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "." + list.get(i));
		}
	}

	//to be deleted----
	/**
	 * Prints the message of task being marked as done.
	 *
	 * @param finished the task being marked as done.
	 */
	public void showDone(Task finished) {
		System.out.println("Nice! I've marked this task as done:");
		System.out.println(finished);
	}
	//----to be deleted

	//to be deleted----
	/**
	 * Show Snooze Message.
	 *
	 * @param snoozed the task snoozed.
	 */
	public void showSnooze(Task snoozed) {
		System.out.println("Here is the task snoozed for 24 hours:");
		System.out.println(snoozed);
	}
	//----to be deleted

	/**
	 * Prints the list of food products whose expiry is in the next 7 days.
	 *
	 * @param list the list of food products nearing expiry.
	 */
	public void showExpiryReminder(FoodList list) {
		System.out.println("Here are the upcoming expiry dates:");
		for(int i = 0; i < list.size(); i++) {
			Food food = list.get(i);
			System.out.println(food.getName() + " due in " + parseDateToString(food.getExpiryDate()));
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
	 * Prints the message of a food being removed.
	 *
	 * @param removed the food being removed.
	 * @param list the list of food stored in Cube.
	 */
	//to be deleted----
	public void showRemove(Task removed, TaskList list) {
		System.out.println("Nice! I've removed this task:");
		System.out.println(removed);
		System.out.println("Now you have " + list.size() + " tasks in the list.");
	}
	//----to be deleted

	public void showRemoveFood(Food removed, FoodList list) {
		System.out.println("Nice! I've removed this food:");
		System.out.println(removed);
		System.out.println("Now you have " + list.size() + " food in the list.");
	}

	/**
	 * Prints the food to be sold.
	 *
	 * @param foodName the food being sold.
	 * @param quantity the quantity sold.
	 * @param profit the profit earned.
	 * @param revenue total profit earned.
	 */
	public void showSold(String foodName, int quantity, double profit, double revenue) {
		String display = String.format("%d of %s sold\n", quantity, foodName);
		display += String.format("you have earn %f, the total revenue is %f", profit, revenue);
		System.out.println(display);
	}

	//to be deleted----
	/**
	 * Prints the message of a free day.
	 *
	 * @param date date of the free day.
	 */
	public void showFreeDay(Date date) {
		System.out.println("Your next free day is: " + parseDateToString(date));
	}
	//----to be deleted

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
	 * @param path the path that the user intended to load.
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
				+ "Show task list:\n"
				+ String.format("%1$-50s", "  'list'") + "shows the list of food products\n"
				+ String.format("%1$-50s", "  'reminder'") + "show the list of food products that are low on stock and/or are approaching its expiry date\n"
				+ "Miscellaneous:\n"
				+ String.format("%1$-50s", "  'bye' OR 'exit' OR 'quit'") + "to exit the programme\n"
				+ String.format("%1$-50s", "  'help'") + "to show a list of available command\n";
		System.out.print(help);
	}

	/**
	 * Returns the string of date by parsing a date.
	 * @param date the date to be parsed.
	 * @return the string of date.
	 */
	public static String parseDateToString(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
		return formatter.format(date);
	}
}