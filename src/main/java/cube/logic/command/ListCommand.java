/**
 * The command list all the food.
 *
 * @author tygq13
 */
package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.model.food.FoodList.SortType;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.command.util.CommandResult;

public class ListCommand extends Command{
	public SortType sortType;

	public static final String MESSAGE_SUCCESS = "Here are the food in your list: \n"
		+ "%1$s\n" // todo: toString for list
		+ "The total revenue so far is $ %2$f\n";

	/**
	 * Default constructor of ListCommand.
	 */
	public ListCommand() {}

	/**
	 * Constructor with one argument.
	 *
	 * @param sortType The type of sorting used for food list.
	 */
	public ListCommand (SortType sortType) {
		this.sortType = sortType;
	}

	/**
	 *
	 *
	 * @param storage The current Storage.
	 * @return The message feedback to user before Programme Exit.
	 */
	@Override
	public CommandResult execute(ModelManager model, StorageManager storage) {
		FoodList list = model.getFoodList();
		if (sortType != null) {
			list.sort(sortType);
		}
		return new CommandResult(String.format(MESSAGE_SUCCESS, list, Food.getRevenue()));
	}
}