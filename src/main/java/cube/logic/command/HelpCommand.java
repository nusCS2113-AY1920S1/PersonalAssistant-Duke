package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.command.util.CommandResult;

public class HelpCommand extends Command{

	public static final String MESSAGE_SUCCESS = "Currently we support the following commands: (all command ignore cases)\n"
			+ "Manipulate food:\n"
			+ String.format("%1$-50s", "  'add <food name> -t <food type> -p <price> -s <stock> -e <expiry date>' ") + "add a new food product with all details/description\n"
			+ String.format("%1$-50s", "  'delete -i <number> OR -n <food name> OR -t <food type>'") + "delete the specified food product in the list\n"
			+ String.format("%1$-50s", "  'sold <food name> -q <quantity>'") + "marks quantity q of product n sold\n"
			+ "Show inventory:\n"
			+ String.format("%1$-50s", "  'list -sort <sort type>'") + "shows the list of food products, sort type can be expiry/name/stock\n"
			+ String.format("%1$-50s", "  'find -i <number> OR -n <food name> OR -t <food type> -sort <sort type>'") + "find specific food using index/name/type, sort type is only available when finding by type, it can be expiry/name/stock\n"
			+ String.format("%1$-50s", "  'reminder'") + "show the list of food products that are low on stock and/or are approaching its expiry date\n"
			+ String.format("%1$-50s", "  'promotion <food name> -% <discount> -s <start date> -e <end date>'") + "adds a new promotion item\n"
			+ "Sales record:\n"
			+ String.format("%1$-50s", "  'profit -n <food name> -period <date>-<date>'") + "show the profit and revenue for a food cross a period\n"
			+ "Miscellaneous:\n"
			+ String.format("%1$-50s", "  'batch -i <filename> OR -o <filename>'") + "batch imports or outputs the list of food products as the filename in CSV.\n"
			+ String.format("%1$-50s", "  'config'") + "lists all the stored user configurations.\n"
			+ String.format("%1$-50s", "  'config UI -h <height> -w <width>'") + "updates the default window size configuration with specified values.\n"
			+ String.format("%1$-50s", "  'config LOG -c <log count> -s <size in MB> -l <level>'") + "updates the logging configuration with specified values.\n"
			+ String.format("%1$-50s", "  'bye' OR 'exit' OR 'quit'") + "to exit the programme\n"
			+ String.format("%1$-50s", "  'help'") + "to show a list of available command\n";

	/**
	 * Shows the list of available command.
	 * @param model The facade of all models.
	 * @param storage The storage manager for commands.
	 * @return The command result with feedback to user.
	 */
	@Override
	public CommandResult execute(ModelManager model, StorageManager storage) {
		return new CommandResult(MESSAGE_SUCCESS, true, false);
	}
}
