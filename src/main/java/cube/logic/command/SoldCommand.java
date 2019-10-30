package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.model.sale.Sale;
import cube.model.sale.SalesHistory;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;
import java.util.Date;

public class SoldCommand extends Command{
	String foodName;
	int quantity;
	Date soldDate;
	Food toSold;

	public static final String MESSAGE_SUCCESS = "%1$d of %2$s have been sold with $%3$f\n"
		+ "you have earn $%4$f";	

	public SoldCommand(String foodName, int quantity) {
		this(foodName, quantity, new Date());
	}

	public SoldCommand(String foodName, int quantity, Date soldDate) {
		this.foodName = foodName;
		this.quantity = quantity;
		this.soldDate = soldDate;
	}

	/**
	 * Acquires the food to sold for this command.
	 * @param list The food list.
	 * @throws CommandException
	 */
	public void obtainFoodSold(FoodList list) throws CommandException {
		CommandUtil.requireValidName(list, foodName);
		toSold = list.get(foodName);
	}

	/**
	 * If parameters are valid, this method will generate a sale record and adjust the quantity
	 * of food toSold. Finally, changes sale record and food will be saved in storage.
	 *
	 * @param list The food list.
	 * @param storage The storage we have.
	 * @return The Feedback to User for Delete Command.
	 * @throws CommandException If deletion is unsuccessful.
	 */
	@Override
	public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
		//TODO: check if the user has set price and cost
		FoodList list = model.getFoodList();
		SalesHistory salesHistory = model.getSalesHistory();
		obtainFoodSold(list);
		CommandUtil.requireValidQuantity(toSold, quantity);
		
		int originalQty = toSold.getStock();
		double revenue = quantity * toSold.getPrice();
		toSold.setStock(originalQty - quantity);
		// old function
		Food.updateRevenue(Food.getRevenue() + revenue);
		// new function
		double profit = revenue - quantity * toSold.getCost();
		Sale saleRecord = new Sale(foodName, quantity, revenue, profit, soldDate);
		salesHistory.add(saleRecord);
		storage.storeSalesHistory(salesHistory);

		storage.storeRevenue(Food.getRevenue());
		return new CommandResult(String.format(MESSAGE_SUCCESS, quantity, foodName, revenue, profit));
	}
}