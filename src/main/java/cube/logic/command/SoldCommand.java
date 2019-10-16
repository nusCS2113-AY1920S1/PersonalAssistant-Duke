package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.ui.Ui;
import cube.storage.StorageManager;

public class SoldCommand extends Command{
	String foodName;
	int quantity;

	public SoldCommand (String foodName, int quantity) {
		this.foodName = foodName;
	}

	@Override
	public void execute(FoodList list, Ui ui, StorageManager storage) {
		Food food = list.get(foodName);
		int originalQty = food.getStock();
		food.setStock(originalQty - quantity);
		double profit = quantity * food.getPrice();
		Food.updateRevenue(Food.getRevenue() + profit);
		storage.storeRevenue(Food.getRevenue());
		ui.showSold(foodName, quantity, profit, Food.getRevenue());
	}
}