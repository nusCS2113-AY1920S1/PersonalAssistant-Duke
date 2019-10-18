package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.ui.Ui;
import cube.ui.Message;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;

public class SoldCommand extends Command{
	String foodName;
	int quantity;

	public SoldCommand (String foodName, int quantity) {
		this.foodName = foodName;
	}

	public void checkValid(Food foodSold, FoodList list) throws CommandException {
		if (!list.exists(foodName)) {
			throw new CommandException(Message.FOOD_NOT_EXISTS);
		}
		if (quantity < 0 || quantity > foodSold.getStock()) {
			throw new CommandException(Message.INVALID_QUANTITY_SOLD);
		}
	}

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) throws CommandException {
		Food foodSold = list.get(foodName);
		checkValid(foodSold, list);
		
		int originalQty = foodSold.getStock();
		foodSold.setStock(originalQty - quantity);
		double profit = quantity * foodSold.getPrice();
		Food.updateRevenue(Food.getRevenue() + profit);
		storage.storeRevenue(Food.getRevenue());
		ui.showSold(foodName, quantity, profit, Food.getRevenue());
	}
}