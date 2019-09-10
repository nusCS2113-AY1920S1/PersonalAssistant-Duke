package util;

import java.util.Hashtable;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import command.*;
import ui.Message;
import exception.DukeException;

public class Parser {
	public enum Parts {
	    COMMAND, DESCRIPTION, DATE
	}

	public static Command parse(String fullCommand) throws DukeException {
		Hashtable<Parts, String> dict= parseToDict(fullCommand);
		String command = dict.get(Parts.COMMAND);
		String description = dict.get(Parts.DESCRIPTION);
		Date date = parseStringToDate(dict.get(Parts.DATE));
		switch (command) {
			case "todo":
				return new TodoCommand(description);
			case "event":
				return new EventCommand(description, date);
			case "deadline":
				return new DeadlineCommand(description, date);
			case "list":
				return new ListCommand();
			case "done":
				return new DoneCommand(description);
			case "delete":
				return new DeleteCommand(description);
			case "help":
				return new HelpCommand();
			case "bye":
			case "exit":
			case "quit":
			case "fuck":
				return new ExitCommand();
			default:
				throw new DukeException(Message.INVALID_COMMAND);
		} 
	}

	private static Hashtable<Parts, String> parseToDict(String fullCommand) {
		Hashtable<Parts, String> dict = new Hashtable<>();
		String[] inputs = fullCommand.split(" ");
		String command = inputs[0];
		dict.put(Parts.COMMAND, command.trim().toLowerCase());
		if (inputs.length >= 2) {
			// have description
			int descriptionIndex = fullCommand.indexOf(" ") + 1;
		    int dateIndex = fullCommand.indexOf("/at ") > fullCommand.indexOf("/by ") ? 
		    	fullCommand.indexOf("/at ") : fullCommand.indexOf("/by ");
		    if (dateIndex != -1) {
		    	// have date
		    	String description = fullCommand.substring(descriptionIndex, dateIndex -1);
		    	dict.put(Parts.DESCRIPTION, description);
		    	String date = fullCommand.substring(dateIndex + 3).trim();            
		    	dict.put(Parts.DATE, date);
		    } else {
				String description = fullCommand.substring(descriptionIndex).trim();
				dict.put(Parts.DESCRIPTION, description);
			}
		}
		return dict;
	}

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

	public static String parseDateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
		return formatter.format(date);
	}
}