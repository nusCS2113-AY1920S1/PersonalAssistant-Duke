package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.model.FoodList;
import cube.model.Food;
import cube.storage.StorageManager;
import cube.logic.parser.ParserUtil;

import java.util.Calendar;
import java.util.Date;

public class ReminderCommand extends Command{
    int daysToExpiry;
    int stockIndex;

    public ReminderCommand(int daysToExpiry, int stockIndex) {
        this.daysToExpiry = daysToExpiry;
        this.stockIndex = stockIndex;
    }

    private String MESSAGE_SUCCESS = "";

    private void buildExpiryReminder(FoodList list) {
        MESSAGE_SUCCESS += "Here are the upcoming expiry dates:\n";
        for(int i = 0; i < list.size(); i++) {
            Food food = list.get(i);
            MESSAGE_SUCCESS += String.format("%1$s due in %2$s\n", food.getName(), ParserUtil.parseDateToString(food.getExpiryDate()));
        }
        MESSAGE_SUCCESS += "\n";
    }

    private void buildStockReminder(FoodList list) {
        MESSAGE_SUCCESS += "Here are the food products that are low in stock:\n";
        for(int i = 0; i < list.size(); i++) {
            Food food = list.get(i);
            MESSAGE_SUCCESS += String.format("%1$s : %2$s left\n", food.getName(), food.getStock());
        }
    }

    /**
     * Shows the list of food products that are low on stock (less than quantity of 5)
     * or approaching/nearing its expiry date (by a week or lesser).
     *
     * @param list the list of food products
     * @param ui the user interface to output message
     * @param storage storage of Cube
     */

    @Override
    public CommandResult execute(FoodList list, StorageManager storage) throws CommandException {
        FoodList stockReminder = new FoodList();
        FoodList expiryReminder = new FoodList();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date()); // get current time
        cal.add(Calendar.DATE, daysToExpiry); // deadline within 7 days
        for (int i = 0; i < list.size(); i++) {
            Food food = list.get(i);
            Date expiryDate = food.getExpiryDate();
            int stock = food.getStock();

            if (expiryDate != null && expiryDate.before(cal.getTime())) {
                expiryReminder.add(food);
            }

            if (stock != 0 && stock < stockIndex ) {
                stockReminder.add(food);
            }
        }

        buildExpiryReminder(expiryReminder);
        buildStockReminder(stockReminder);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
