package cube.logic.command;

import cube.model.food.FoodList;
import cube.ui.Ui;
import cube.storage.StorageManager;

public class DeleteCommand extends Command{
	private int deleteIndex;

	public DeleteCommand (int index) {
		this.deleteIndex = index;
	}

	private boolean isValid(FoodList list) {
		return deleteIndex > 0 && deleteIndex < list.size();
	}

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) {
		if (isValid(list)) {
			list.remove(deleteIndex);
			storage.storeFoodList(list);
		}
		System.out.println("reach delete");
		// ui.showDelete()
	}
}