package cube.logic.command;

import cube.model.food.FoodList;
import cube.ui.Ui;
import cube.storage.StorageManager;

public class HelpCommand extends Command{

	public HelpCommand () {
	}

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) {
		System.out.println("reach help");
		// todo: ui.showHelp();
	}
}