package cube.logic.command;

import cube.model.FoodList;
import cube.model.FoodList.SortType;
import cube.model.Food;
import cube.storage.StorageManager;

public class ListCommand extends Command{
	SortType sortType;

	private final String MESSAGE_SUCCESS = "Here are the food in your list: \n"
		+ "%1$s\n" // todo: toString for list
		+ "The total revenue so far is $ %2$f\n";

	public ListCommand() {

	}
	public ListCommand (SortType sortType) {
		this.sortType = sortType;
	}

	@Override
	public CommandResult execute(FoodList list, StorageManager storage) {
		if (sortType != null) {
			list.sort(sortType);
		}
		return new CommandResult(String.format(MESSAGE_SUCCESS, list, Food.getRevenue()));
	}
}