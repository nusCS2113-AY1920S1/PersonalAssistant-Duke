package cube.logic.command;

import cube.model.food.FoodList;
import cube.ui.Ui;
import cube.storage.StorageManager;

public class ExitCommand extends Command{

	public ExitCommand () {
	}

	@Override
	public boolean isExit() {
		return true;
	}

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) {
		ui.showExit();
	}
}