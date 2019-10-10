/**
 * The task contain all message used when errors occur.
 *
 * @author tygq13
 */
package cube.ui;

//todo: could create relevant exception classes with the specific message
public class Message {
	public static final String EMPTY_DESCRIPTION
			= "OOPS!!! The description of a task cannot be empty";
	public static final String EMPTY_DATE
			= "OOPS!!! The date cannot be empty. Please Add date after / Character";
	public static final String EMPTY_TASK_NUMBER
			= "OOPS!!! The task number cannot be empty";
	public static final String INVALID_TASK_NUMBER
			= "OOPS!!! The task number is invalid";
	public static final String INVALID_DATE_FORMAT
			= "OOPS!!! The date format is invalid. Please specify date in 'dd/mm/yy'";
	public static final String INVALID_COMMAND
			= "OOPS!!! The command is invalid. Enter 'help' to view the list of command";
	public static final String EXISTING_DATE
			= "OOPS!!! There is an existing task that clashes with the date of the task!";
	public static final String EXISTING_DESCRIPTION
			= "OOPS!!! There is an existing task that contains the exact description!";
	public static final String IO_ERROR
			= "OOPS!!! The data file is deprecated. Unable to read or write in cube.dat in ";
	public static final String EMPTY_DURATION
			= "OOPS!!! The duration cannot be empty. Please provide the duration of the task after \\ Character";
}