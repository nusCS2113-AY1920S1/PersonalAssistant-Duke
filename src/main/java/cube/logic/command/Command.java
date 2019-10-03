package cube.logic.command;

import cube.exception.CubeException;
import cube.model.food.FoodList;
import cube.ui.Ui;
import cube.storage.StorageManager;
import cube.exception.CubeException;

public abstract class Command {

	public boolean isExit() {
		return false;
	}

	public abstract void execute(FoodList list, Ui ui, StorageManager storage) throws CubeException;
}