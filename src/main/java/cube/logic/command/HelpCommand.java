package cube.logic.command;

import cube.model.FoodList;
import cube.storage.StorageManager;

public class HelpCommand extends Command{

	String MESSAGE_SUCCESS = "Currently we support the following commands: (all command ignore cases)\n"
			+ "Manipulate tasks:\n"
			+ String.format("%1$-50s", "  'add <food name> -t <food type> -p <price> -s <stock> -e <expiry date>' ") + "add a new food product with all details/description\n"
			+ String.format("%1$-50s", "  'generaterevenue -i <number> OR -n <food name> OR -t <food type>'") + "generate the revenue for the specified food product in the list\n"
			+ String.format("%1$-50s", "  'delete -i <number> OR -n <food name> OR -t <food type>'") + "delete the specified food product in the list\n"
			+ String.format("%1$-50s", "  'sold -n <food name> -q <quantity>'") + "marks quantity q of product n sold\n"
			+ "Show task list:\n"
			+ String.format("%1$-50s", "  'list -sort <sort type>'") + "shows the list of food products, sort type can be expiry/name/stock\n"
			+ String.format("%1$-50s", "  'find -i <number> OR -n <food name> OR -t <food type> -sort <sort type>'") + "find specific food using index/name/type, sort type is only available when finding by type, it can be expiry/name/stock\n"
			+ String.format("%1$-50s", "  'reminder'") + "show the list of food products that are low on stock and/or are approaching its expiry date\n"
			+ "Miscellaneous:\n"
			+ String.format("%1$-50s", "  'bye' OR 'exit' OR 'quit'") + "to exit the programme\n"
			+ String.format("%1$-50s", "  'help'") + "to show a list of available command\n";

	/**
	 * Shows the list of available command.
	 *
	 * @param list the list of food products.
	 * @param storage storage of Duke.
	 */

	@Override
	public CommandResult execute(FoodList list, StorageManager storage) {
		return new CommandResult(MESSAGE_SUCCESS, true, false);
	}
}