package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.ui.Ui;
import cube.storage.StorageManager;

public class AddCommand extends Command{
	private final Food toAdd;

	public AddCommand (Food food) {
		this.toAdd = food;
	}

	private boolean isValid(FoodList list) {
		return !list.exists(toAdd);
	}

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) {
		if (isValid(list)) {
			list.add(toAdd);
			storage.storeFoodList(list);
		}
		ui.showAddFood(list);
	}
}