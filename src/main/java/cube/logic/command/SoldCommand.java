/**
 * SoldCommand.java
 * This class manages the selling of food.
 *
 * @author tygq13
 */

package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.model.promotion.Promotion;
import cube.model.promotion.PromotionList;
import cube.model.sale.Sale;
import cube.model.sale.SalesHistory;
import cube.model.ModelManager;
import cube.storage.ProfitStorage;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;

import java.util.Calendar;
import java.util.Date;

public class SoldCommand extends Command {
	String foodName;
	int quantity;
	Date soldDate;
	Food toSold;
	Promotion promotion;

	public static final String MESSAGE_SUCCESS = "%1$d of %2$s have been sold with $%3$.2f\n"
		+ "you have earn profit $%4$.2f";

	/**
	 * Constructor with two arguments.
	 * Calls another constructor with additional argument Date = current time.
	 * @param foodName The name of the food to be sold.
	 * @param quantity The quantity of food sold.
	 */
	public SoldCommand(String foodName, int quantity) {
		this(foodName, quantity, new Date());
	}

	/**
	 * Constructor with three arguments.
	 * @param foodName The name of the food to be sold.
	 * @param quantity The quantity of food sold.
	 * @param soldDate The date of the food sold.
	 */
	public SoldCommand(String foodName, int quantity, Date soldDate) {
		this.foodName = foodName;
		this.quantity = quantity;
		this.soldDate = soldDate;
	}

	/**
	 * Acquires the food to sold for this command.
	 * @param list The food list.
	 * @throws CommandException when the command requirements are not met.
	 */
	public void obtainFoodSold(FoodList list) throws CommandException {
		CommandUtil.requireValidName(list, foodName);
		toSold = list.get(foodName);
	}

	/**
	 * If parameters are valid, this method will generate a sale record and adjust the quantity
	 * of food toSold. Finally, changes sale record and food will be saved in storage.
	 *
	 * @param storage The storage we have.
	 * @return The Feedback to User for Delete Command.
	 * @throws CommandException If deletion is unsuccessful.
	 */
	@Override
	public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
		FoodList list = ModelManager.getFoodList();
		final PromotionList promotionList = model.getPromotionList();
		final SalesHistory salesHistory = model.getSalesHistory();
		obtainFoodSold(list);
		CommandUtil.requireValidQuantity(toSold, quantity);

		double price;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		if (promotionList.existsName(foodName)) {
			promotion = promotionList.get(foodName);
			Date currentDate = cal.getTime();
			cal.add(Calendar.DATE, 1);
			Date modifiedDate = cal.getTime();
			//cal.add(Calendar.DATE, -1);

			if (currentDate.before(promotion.getEndDate())
					&& modifiedDate.after(promotion.getStartDate())) {
				price = promotion.getPromotionalPrice();
			} else {
				price = toSold.getPrice();
			}
		} else {
			price = toSold.getPrice();
		}

		int originalQty = toSold.getStock();
		double revenue = quantity * price;
		toSold.setStock(originalQty - quantity);
		double profit = revenue - quantity * toSold.getCost();

		//profit and revenue in sales record update
		double tempRevenue = ProfitStorage.getAnnualRevenue();
		tempRevenue += revenue;
		ProfitStorage.setAnnualRevenue(tempRevenue);

		double tempProfit = ProfitStorage.getAnnualProfit();
		tempProfit += profit;
		ProfitStorage.setAnnualProfit(tempProfit);

		Sale saleRecord = new Sale(foodName, quantity, revenue, profit, soldDate);
		salesHistory.add(saleRecord);
		storage.storeSalesHistory(salesHistory);

		return new CommandResult(String.format(MESSAGE_SUCCESS, quantity, foodName, revenue, profit));
	}
}