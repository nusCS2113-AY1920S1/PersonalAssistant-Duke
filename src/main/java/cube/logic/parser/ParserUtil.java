package cube.logic.parser;

import cube.exception.CubeException;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public class ParserUtil {

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

	/**
	 * Returns a Date object by parsing the date String.
	 * Time zone is set as Singapore time by default.
	 *
	 * @param dateString the String describing the date.
	 * @return the date
	 * @throws CubeException exception occurs when unable to parse.
	 */
	public static Date parseStringToDate(String dateString) throws ParserException {
		if (dateString == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
		try {
			Date date = formatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			throw new ParserException(ParserErrorMessage.INVALID_DATE_FORMAT);
		}
	}

	//@@author ZKathrynx
	/**
	 * Find the full name/type until next parameter/end of input.
	 * @param inputs tokens containing the full string to be found.
	 * @param index starting index.
	 * @return the full name/type until next parameter/end of input.
	 */
	public static String findFullString (String[] inputs, int index) {
		String fullString = "";

		for (int i = index; i < inputs.length; i ++) {
			if(inputs[i].matches("-(.*)")) {
				break;
			}

			if(i != index) {
				fullString += " ";
			}
			fullString += inputs[i];
		}

		return fullString;
	}

	/**
	 * Find the full name/type until next parameter/end of input.
	 * @param inputs tokens containing the full string to be found.
	 * @param params set of possible parameters.
	 * @return true if the input has parameter that is not within possible parameter set.
	 *         false otherwise.
	 */
	public static boolean hasInvalidParameters (String[] inputs, String[] params) {
		boolean flag;
		for (int i = 0; i < inputs.length; i ++) {
			if(inputs[i].matches("-(.*)")) {
				flag = false;
				for(int j = 0; j < params.length; j ++){
					if(inputs[i].equals(params[j])){
						flag = true;
						break;
					}
				}
				if(!flag){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Find the full name/type until next parameter/end of input.
	 * @param inputs tokens containing the full string to be found.
	 * @return true if the input has parameter that is not within possible parameter set.
	 *         false otherwise.
	 */
	public static boolean hasRepetitiveParameters (String[] inputs) {
		HashSet<String> table = new HashSet<String>();
		for (int i = 0; i < inputs.length; i ++) {
			if(inputs[i].matches("-(.*)")) {
				if(table.contains(inputs[i])){
					return true;
				}else{
					table.add(inputs[i]);
				}
			}
		}
		return false;
	}

	/**
	 * Find the full name/type until next parameter/end of input.
	 * @param input tokens containing the full string to be found.
	 * @return true if the input has parameter that is not within possible parameter set.
	 *         false otherwise.
	 */
	public static boolean isValidNumber (String input) {
		int number;
		try{
			number = Integer.parseInt(input);
			if (number<0){
				return false;
			}
			return true;
		}catch (Exception e) {
			return false;
		}
	}
}