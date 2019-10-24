package cube.logic.command;

import cube.model.FoodList;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;

public abstract class Command {

	public boolean isExit() {
		return false;
	}

	public abstract CommandResult execute(FoodList list, StorageManager storage) throws CommandException;
}