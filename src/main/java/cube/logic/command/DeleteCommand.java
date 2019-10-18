package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.ui.Ui;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;
import cube.logic.command.exception.CommandErrorMessage;

public class DeleteCommand extends Command{
	private int deleteIndex;
	private final String MESSAGE_SUCCESS = "Nice! I've removed this food:\n"
		+ "%1$s\n"
		+ "Now you have %2$s food in the list.\n";

	public DeleteCommand(int index) {
		this.deleteIndex = index - 1;
	}

	//todo: delete by foodname

	private void checkValid(FoodList list) throws CommandException {
		if (deleteIndex < 0 || deleteIndex >= list.size()) {
			throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
		}
	}

	@Override
	public CommandResult execute(FoodList list, StorageManager storage) throws CommandException {
		checkValid(list);
		
		Food toDelete = list.get(deleteIndex);
		list.remove(deleteIndex);
		storage.storeFoodList(list);
		return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete, list.size()));
	}
}