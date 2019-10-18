package cube.logic.parser;

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
}