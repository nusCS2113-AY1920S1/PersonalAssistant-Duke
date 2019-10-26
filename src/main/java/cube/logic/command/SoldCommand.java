package cube.logic.command;

import cube.model.FoodList;
import cube.model.Food;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;

public class SoldCommand extends Command{
	String foodName;
	int quantity;
	Food toSold;

	private final String MESSAGE_SUCCESS = "%1$d of %2$s have been sold\n"
		+ "you have earn $%3$f, the total revenue is $%4$f";	

	public SoldCommand (String foodName, int quantity) {
		this.foodName = foodName;
		this.quantity = quantity;
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
	public CommandResult execute(FoodList list, StorageManager storage) throws CommandException {
		obtainFoodSold(list);
		CommandUtil.requireValidQuantity(toSold, quantity);
		
		int originalQty = toSold.getStock();
		toSold.setStock(originalQty - quantity);
		double profit = quantity * toSold.getPrice();
		Food.updateRevenue(Food.getRevenue() + profit);
		storage.storeRevenue(Food.getRevenue());
		return new CommandResult(String.format(MESSAGE_SUCCESS, quantity, foodName, profit, Food.getRevenue()));
	}
}