package cube.logic.command;

import cube.model.food.FoodList;
import cube.ui.Ui;
import cube.storage.StorageManager;

public class ExitCommand extends Command{

	private final String MESSAGE_SUCCESS = "Bye. Hope to see you again soon!";

	@Override
	public boolean isExit() {
		return true;
	}

	@Override
	public CommandResult execute(FoodList list, StorageManager storage) {
		return new CommandResult(MESSAGE_SUCCESS, false, true);
	}
}