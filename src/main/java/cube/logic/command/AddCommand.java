/**
 * The command add a food to food list and storage
 *
 * @author tygq13
 */
package cube.logic.command;

import cube.model.FoodList;
import cube.model.Food;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;

public class AddCommand extends Command{
	private final Food toAdd;

	private final String MESSAGE_SUCCESS = "New Food added: \n" 
		+ "%1$s\n"
		+ "Now you have %2$s food in the list.\n";

	/**
	 * Default constructor.
	 * @param food the food to be added.
	 */
	public AddCommand (Food food) {
		this.toAdd = food;
	}

	/**
	 * Adds food to foodList and store it if the food does not already exists, otherwise throws
	 * Command exception.
	 *
	 * @param list The food list.
	 * @param storage The current Storage.
	 * @return The message feedback to user before Programme Exit.
	 */
	@Override
	public CommandResult execute(FoodList list, StorageManager storage) throws CommandException {
		CommandUtil.requireNameNotExists(list, toAdd.getName());
		list.add(toAdd);
		Food.updateRevenue(Food.getRevenue() + toAdd.getFoodRevenue());
		storage.storeFoodList(list);
		return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, list.size()));
	}
}