/**
 * This class is used as interface with the user. It could reads from user and print message to the user.
 *
 * @author tygq13
 */
package cube.ui;

import cube.logic.parser.AddCommandParser;
import cube.logic.parser.Parser;
import cube.logic.parser.ParserUtil;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.task.TaskList;
import cube.task.Task;
import cube.logic.command.CommandResult;
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

	public void showError(String e) {
		System.out.println(e);
	}

	/**
	 * Prints the error message of loading error.
	 * @param path the path that the user intended to load.
	 */
	public void showLoadingError(String path) {
		showLine();
		System.out.println(Message.IO_ERROR + path);
	}

	// temporary use before GUI finish
	public void showCommandResult(CommandResult result) {
		System.out.println(result.getFeedbackToUser());
	}
}