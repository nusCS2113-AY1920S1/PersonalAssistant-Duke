package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.ui.Ui;
import cube.ui.Message;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;

public class DeleteCommand extends Command{
	private int deleteIndex;

	public DeleteCommand (int index) {
		this.deleteIndex = index - 1;
	}

	private void checkValid(FoodList list) throws CommandException {
		if (deleteIndex < 0 || deleteIndex >= list.size()) {
			throw new CommandException(Message.FOOD_NOT_EXISTS);
		}
	}

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) throws CommandException {
		checkValid(list);
		
		Food temp = list.get(deleteIndex);
		list.remove(deleteIndex);
		storage.storeFoodList(list);
		//ui.showDelete(temp, list);
	}
}