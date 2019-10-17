package cube.logic.parser;

import cube.exception.CubeException;
import cube.logic.command.AddCommand;
import cube.model.food.Food;
import cube.ui.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Parse add food command.
 */
public class AddCommandParser implements ParserPrototype<AddCommand> {

	public AddCommand parse(String[] args) throws CubeException {
		int foodNameIndex = -1;
		int foodTypeIndex = -1;
		int priceIndex = -1;
		int stockIndex = -1;
		int expiryDateIndex = -1;
		for (int i = 1; i < args.length; i ++) {
			if (args[i].equals("-n")) {
				foodNameIndex = i;
			}
			if (args[i].equals("-t")) {
				foodTypeIndex = i;
			}
			if (args[i].equals("-p")) {
				priceIndex = i;
			}
			if (args[i].equals("-s")) {
				stockIndex = i;
			}
			if (args[i].equals("-e")) {
				expiryDateIndex = i;
			}
		}
		if (foodNameIndex == -1) {
			throw new CubeException("Not enough parameter. Please enter food name.");
		}
		Food tempFood = new Food(findFullString(args,foodNameIndex));
		if (foodTypeIndex != -1) {
			tempFood.setType(findFullString(args,foodTypeIndex));
		}
		if (priceIndex != -1) {
			tempFood.setPrice(Integer.parseInt(args[priceIndex+1]));
		}
		if (stockIndex != -1) {
			tempFood.setStock(Integer.parseInt(args[stockIndex+1]));
		}
		if (expiryDateIndex != -1) {
			tempFood.setExpiryDate(parseStringToDate(args[priceIndex+1]));
		}
		return new AddCommand(tempFood);
	}

	/**
	 * Returns a Date object by parsing the date String.
	 * Time zone is set as Singapore time by default.
	 *
	 * @param dateString the String describing the date.
	 * @return the date
	 * @throws CubeException exception occurs when unable to parse.
	 */
	public static Date parseStringToDate(String dateString) throws CubeException {
		if (dateString == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
		try {
			Date date = formatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			throw new CubeException(Message.INVALID_DATE_FORMAT);
		}
	}

	public String findFullString (String[] inputs, int index) {
		String fullSring = "";

		for (int i = index + 1; i < inputs.length; i ++) {
			if(inputs[i].equals("-n")||inputs[i].equals("-t")||inputs[i].equals("-p")
			||inputs[i].equals("-s")||inputs[i].equals("-e")) {
				break;
			}

			if(i != index+1) {
				fullSring += " ";
			}
			fullSring += inputs[i];
		}

		return fullSring;
	}

}