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
		int originQty = food.getStock();
		food.updateStock(originQty - quantity);
		Food.updateRevenue(Food.getRevenue() + quantity * food.getPrice());
		storage.storeRevenue(Food.getRevenue());
		System.out.println("reach sold");
		//ui.showSold(foodName, Food.getRevenue());
	}
}