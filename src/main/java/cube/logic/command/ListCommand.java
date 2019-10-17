package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.FoodList.SortType;
import cube.ui.Ui;
import cube.storage.StorageManager;

public class ListCommand extends Command{
	SortType sortType;
	public ListCommand() {

	}
	public ListCommand (SortType sortType) {
		this.sortType = sortType;
	}

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) {
		if (sortType != null) {
			list.sort(sortType);
		}
		ui.showListFood(list);
	}
}