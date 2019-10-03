package cube.logic.parser;

import cube.logic.command.*;
import cube.exception.CubeException;
import cube.model.food.Food;
import cube.ui.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

// design pattern copied from address book
// reference: https://github.com/nusCS2113-AY1920S1/addressbook-level3/tree/master/src/main/java/seedu/address/logic/parser

/**
 * Parse user command.
 * @author Zheng Kaining
 */
public class Parser {

	/**
	 * Parse user command.
	 *
	 * Format:
	 * add -n foodName -t foodType -p price -s stock -e expiryDate
	 * list
	 * delete -i index
	 * sold -n foodName -q quantity
	 * help
	 * bye
	 *
	 * Assumption: no repetitive parameters given.
	 *
	 * @param fullCommand the command that user inputs.
	 * @return corresponding command.
	 */
	public static Command parse (String fullCommand) throws CubeException{
		String[] inputs = fullCommand.split(" ");
		String command = inputs[0];
		command = command.trim().toLowerCase();
		switch (command) {
			case "add":
				int foodNameIndex = -1;
				int foodTypeIndex = -1;
				int priceIndex = -1;
				int stockIndex = -1;
				int expiryDateIndex = -1;
				for (int i = 1; i < inputs.length; i ++) {
					if (inputs[i].equals("-n")) {
						foodNameIndex = i;
					}
					if (inputs[i].equals("-t")) {
						foodTypeIndex = i;
					}
					if (inputs[i].equals("-p")) {
						priceIndex = i;
					}
					if (inputs[i].equals("-s")) {
						stockIndex = i;
					}
					if (inputs[i].equals("-e")) {
						expiryDateIndex = i;
					}
				}
				if (foodNameIndex == -1) {
					throw new CubeException("Not enough parameter. Please enter food name.");
				}
				Food tempFood = new Food(inputs[foodNameIndex+1]);
				if (foodTypeIndex != -1) {
					tempFood.setType(inputs[foodTypeIndex+1]);
				}
				if (priceIndex != -1) {
					tempFood.setPrice(Integer.parseInt(inputs[priceIndex+1]));
				}
				if (stockIndex != -1) {
					tempFood.updateStock(Integer.parseInt(inputs[stockIndex+1]));
				}
				if (expiryDateIndex != -1) {
					tempFood.setExpiryDate(parseStringToDate(inputs[priceIndex+1]));
				}
				return new AddCommand(tempFood);
			case "list":
				return new ListCommand();
			case "delete":
				if (inputs.length < 3) {
					throw new CubeException("Not enough parameters. Please enter index you want to delete.");
				}
				return new DeleteCommand(Integer.parseInt(inputs[2]));
			case "sold":
				foodNameIndex = -1;
				int quantityIndex = -1;
				for (int i = 1; i < inputs.length; i ++) {
					if (inputs[i].equals("-n")) {
						foodNameIndex = i;
					}
					if (inputs[i].equals("-q")) {
						quantityIndex = i;
					}
				}
				if(foodNameIndex == -1 || quantityIndex == -1) {
					throw new CubeException("Not enough parameters. Please enter both food name and quantity sold.");
				}
				return new SoldCommand(inputs[foodNameIndex+1],Integer.parseInt(inputs[quantityIndex+1]));
			case "help":
				return new HelpCommand();
			case "bye":
			case "exit":
			case "quit":
				return new ExitCommand();
			default:
				throw new CubeException(Message.INVALID_COMMAND);
		}
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
}

