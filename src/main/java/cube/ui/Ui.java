//@@author LL-Pengfei
package cube.ui;

import cube.logic.command.CommandResult;
import java.util.Scanner;

/**
 * This class is used as an user interface. It supports
 * interactions with the users, including data and instructions
 * input and output.
 */
public class Ui {
	private Scanner in = new Scanner(System.in);

	/**
	 * Return the next line of user input being read.
	 *
	 * @return next line of user input.
	 */
	public String readCommand() {
		return in.nextLine();
	}

	/**
	 * Print the welcome screen of Cube.
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
	 * Prints a dotted line in anew line.
	 */
	public void showLine() {
		System.out.println("------------------------------------------------------------------------------------------------------");
	}

	public void showError(String e) {
		System.out.println(e);
	}

	/**
	 * Print the error message of loading error.
	 * @param path the filepath that the user intends to use to load.
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