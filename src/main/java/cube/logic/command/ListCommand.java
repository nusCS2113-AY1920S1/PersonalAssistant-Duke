package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.FoodList.SortType;
import cube.model.food.Food;
import cube.ui.Ui;
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