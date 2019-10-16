package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.ui.Ui;
import cube.storage.StorageManager;

public class DeleteCommand extends Command{
	private int deleteIndex;

	public DeleteCommand (int index) {
		this.deleteIndex = index - 1;
	}

	private boolean isValid(FoodList list) {
		return deleteIndex >= 0 && deleteIndex < list.size();
	}

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) {
		if (isValid(list)) {
			Food temp = list.get(deleteIndex);
			list.remove(deleteIndex);
			storage.storeFoodList(list);
			ui.showRemoveFood(temp, list);
		}
	}
}