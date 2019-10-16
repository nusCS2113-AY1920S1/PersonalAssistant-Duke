package cube.logic.command;

import cube.model.food.FoodList;
import cube.ui.Ui;
import cube.storage.StorageManager;

public class HelpCommand extends Command{ // 'implements' instead of 'extends'?
	/*
	public HelpCommand () { // need this??
	}*/

	/**
	 * Always returns false since this is not an exit command.
	 *
	 * @return false.
	 */

	@Override
	public boolean isExit() {
		return false;
	}

	/**
	 * Shows the list of available command.
	 *
	 * @param list the list of food products.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 */

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) {
		//System.out.println("reach help");
		// todo: ui.showHelp();
		ui.showHelp();
	}
}