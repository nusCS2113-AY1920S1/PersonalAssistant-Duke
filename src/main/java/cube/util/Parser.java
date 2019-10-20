/**
 * Parser class to parse object. Methods are used in class level.
 *
 * @author tygq13
 */
package cube.util;

import java.util.Hashtable;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import cube.command.*;
import cube.ui.Message;
import cube.exception.DukeException;

public class Parser {
	/**
	 * Enumerator for the different parts of a command.
	 */
	public enum Parts {
		COMMAND, DESCRIPTION, DATE, PARAMETER
	}

	/**
	 * Returns a command with relevant arguments. <br>
	 *
	 * This method recognize the command intended to call, and parse the full command into different parts. Then
	 * pass relevant arguments to the command.
	 *
	 * @param fullCommand string of the full command
	 * @return a command
	 * @throws DukeException exception happens when unable to pass string to date.
	 */
	public static Command parse(String fullCommand) throws DukeException {
		Hashtable<Parts, String> dict= parseToDict(fullCommand);
		String command = dict.get(Parts.COMMAND);
		String description = dict.get(Parts.DESCRIPTION);
		Date date = parseStringToDate(dict.get(Parts.DATE));
		String parameter = dict.get(Parts.PARAMETER);
		switch (command) {
			case "recur":
				return new RecurCommand(description,parameter);
			case "tentativescheduling":
				return new TentSchedCommand(description, date);
			case "snooze":
				return new SnoozeCommand(description);
			case "viewschedule":
				return new ViewCommand(date);
			default:
				throw new DukeException(Message.INVALID_COMMAND);
		}
	}

	/**
	 * Returns a hashtable of relevant parts to the string description of the parts. <br>
	 *
	 * The first word is parsed as COMMAND. The following words before /at or /by is parsed as DESCRIPTION.
	 * The remaining is parsed as DATE.
	 *
	 * @param fullCommand string of the full command.
	 * @return a hashtable of Parts enumerator to string description of the part.
	 */
	private static Hashtable<Parts, String> parseToDict(String fullCommand) {
		Hashtable<Parts, String> dict = new Hashtable<>();
		String[] inputs = fullCommand.split(" ");
		String command = inputs[0];
		dict.put(Parts.COMMAND, command.trim().toLowerCase());
		if (inputs.length >= 2) {
			// have additional description
			int descriptionIndex = fullCommand.indexOf(" ");
			int dateIndex = fullCommand.indexOf(" /");
			if (dateIndex != -1) {
				// have date or string parameter
				String description = fullCommand.substring(descriptionIndex, dateIndex);
				dict.put(Parts.DESCRIPTION, description.trim());
				if (fullCommand.substring(dateIndex,dateIndex+4).equals(" /at")||
						fullCommand.substring(dateIndex,dateIndex+4).equals(" /by")) {
					String date = fullCommand.substring(dateIndex + 5).trim();
					dict.put(Parts.DATE, date);
				} else if (fullCommand.substring(dateIndex,dateIndex+7).equals(" /after")){
					String afterEvent = fullCommand.substring(dateIndex + 7).trim();
					dict.put(Parts.PARAMETER, afterEvent);
				} else {
					String frequency = fullCommand.substring(dateIndex + 2).trim();
					dict.put(Parts.PARAMETER, frequency);
				}
			} else {
				String description = fullCommand.substring(descriptionIndex).trim();
				dict.put(Parts.DESCRIPTION, description);
			}
		}
		return dict;
	}

	/**
	 * Returns a Date object by parsing the date String.
	 * Time zone is set as Singapore time by default.
	 *
	 * @param dateString the String describing the date.
	 * @return the date
	 * @throws DukeException exception occurs when unable to parse.
	 */
	public static Date parseStringToDate(String dateString) throws DukeException {
		if (dateString == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
		try {
			Date date = formatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			throw new DukeException(Message.INVALID_DATE_FORMAT);
		}
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