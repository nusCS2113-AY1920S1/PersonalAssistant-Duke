//@@author LL-Pengfei
/**
 * Ui.java
 * Support user interaction.
 */
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

	/**
	 * Prints the list of available command and their usage.
	 */
	public void showHelp() {
		String help = "Currently we support the following commands: (all command ignore cases)\n"
				+ "Manipulate tasks:\n"
				+ String.format("%1$-85s", "  'add -n <food name> -t <food type> -p <price> -s <stock> -e <expiry date>' ") + "add a new food product with all details/description\n"
				+ String.format("%1$-85s", "  'delete -i <number>'") + "delete the nth food product in the list\n"
				+ String.format("%1$-85s", "  'sold -n <food name> -q <quantity>'") + "marks quantity q of product n sold\n"
				+ "Show task list:\n"
				+ String.format("%1$-85s", "  'list'") + "shows the list of food products\n"
				+ String.format("%1$-85s", "  'reminder -d <number of days to expiry> -s <low stock variable>'") + "show the list of food products that are low on stock and/or are approaching its expiry date\n"
				+ "Miscellaneous:\n"
				+ String.format("%1$-85s", "  'bye' OR 'exit' OR 'quit'") + "to exit the programme\n"
				+ String.format("%1$-85s", "  'help'") + "to show a list of available command\n";
		System.out.print(help);

	// temporary use before GUI finish
	public void showCommandResult(CommandResult result) {
		System.out.println(result.getFeedbackToUser());
	}
}