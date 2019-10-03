package duke.ui;

import duke.task.Task;

import java.util.Scanner;

/**
 * Class duke.ui.Ui that deals with user interactions
 */
public class Ui {
	private Scanner scanner;

	/**
	 * Constructor of class duke.ui.Ui
	 * creates a new instance of duke.ui.Ui by initialising a new instance of a Scanner object
	 * enables the reading of user input and hence the actions that can be taken as responses to the user input
	 */
	public Ui() {
		scanner = new Scanner(System.in);
	}

	/**
	 * Greets the user upon initialisation of the task manager
	 */
	public void showWelcome() {
		System.out.println("Hello! I'm Duke");
		System.out.println("What can I do for you?");
	}

	/**
	 * Reads the user input and returns it in a String form to be parsed by the duke.parser.Parser class
	 * @return String the use input
	 */
	public String readCommand() {
		return scanner.nextLine();
	}

	/**
	 * shows the user an error message
	 * @param message
	 */
	public void showError(String message) {
		System.out.println(message);
	}

	/**
	 * shows the user the loading error when there is a FileNotFoundException
	 */
	public void showLoadingError() {
		System.out.println("No saved data was found. Generating new task list.");
	}

	/**
	 * shows the user a parsing error when the wrong input format for datetime is entered
	 * shows the user how the format should be written
	 */
	public void showParsingError() {
		System.out.println("Please enter the date in the format: ddMMyyyy HHmm");
	}

	/**
	 * shows the user a message
	 * @param message the message which should be shown to the user
	 */
	public void showLine(String message) {
		System.out.println(message);
	}

	/**
	 * shows the user each duke.task.Task object entry in the duke.tasklist.TaskList
	 * @param i the index of the duke.task.Task object
	 * @param task the duke.task.Task Object
	 */
	public void showEntry(int i, Task task) {
		System.out.println(i + "." + task);
	}

	/**
	 * bids the user farewell and shuts the program down
	 */
	public void exit() {
		System.out.println("Bye! Hope to see you again soon.");
		System.exit(0);
	}
}
