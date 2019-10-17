package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.ui.Ui;
import cube.ui.Message;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;

public class AddCommand extends Command{
	private final Food toAdd;

	public AddCommand (Food food) {
		this.toAdd = food;
	}

	private void checkValid(FoodList list) throws CommandException {
		if (list.exists(toAdd)) {
			throw new CommandException(Message.FOOD_ALREADY_EXISTS);
		}
	}

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) throws CommandException {
		checkValid(list);

		list.add(toAdd);
		storage.storeFoodList(list);
		ui.showAddFood(list);
	}
}