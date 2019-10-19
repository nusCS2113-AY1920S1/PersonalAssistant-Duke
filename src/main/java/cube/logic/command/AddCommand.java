package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;
import cube.logic.command.exception.CommandErrorMessage;

public class AddCommand extends Command{
	private final Food toAdd;

	private final String MESSAGE_SUCCESS = "New Food added: \n" 
		+ "%1$s\n"
		+ "Now you have %2$s food in the list.\n";

	/**
	 * Default constructor.
	 * @param food the food to be added.
	 */
	public AddCommand (Food food) {
		this.toAdd = food;
	}

	/**
	 * Check whether the food to be added is already in the list.
	 * @param list the food list to be checked through.
	 * @throws CommandException
	 */
	private void checkValid(FoodList list) throws CommandException {
		if (list.existsName(toAdd.getName())) {
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