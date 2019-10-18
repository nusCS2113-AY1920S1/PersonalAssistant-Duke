package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.ui.Ui;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;
import cube.logic.command.exception.CommandErrorMessage;

public class AddCommand extends Command{
	private final Food toAdd;

	private final String MESSAGE_SUCCESS = "New Food added: \n" 
		+ "%1$s\n"
		+ "Now you have %2$s food in the list.\n";

	public AddCommand (Food food) {
		this.toAdd = food;
	}

	private void checkValid(FoodList list) throws CommandException {
		if (list.exists(toAdd.getName())) {
			throw new CommandException(CommandErrorMessage.FOOD_ALREADY_EXISTS);
		}
	}

	@Override
	public CommandResult execute(FoodList list, StorageManager storage) throws CommandException {
		checkValid(list);

		list.add(toAdd);
		storage.storeFoodList(list);
		return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, list.size()));
	}
}