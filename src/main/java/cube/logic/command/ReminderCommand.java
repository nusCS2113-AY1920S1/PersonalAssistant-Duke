/**
 * The command adds a new reminder to the reminder list.
 *
 * @@author parvathi14
 */
package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.parser.ParserUtil;
import cube.logic.command.util.CommandResult;

import java.util.Calendar;
import java.util.Date;

public class ReminderCommand extends Command{
    int daysToExpiry;
    int stockIndex;

    /**
     * Constructor for setting a new reminder.
     * @param daysToExpiry Number of days to expiry date.
     * @param stockIndex Value for low stock.
     */

    public ReminderCommand(int daysToExpiry, int stockIndex) {
        this.daysToExpiry = daysToExpiry;
        this.stockIndex = stockIndex;
    }

    private String MESSAGE_SUCCESS = "";

    /**
     * Creates the list of reminders based on items nearing its expiry date.
     * @param list The list of expiry date reminders.
     */

    private void buildExpiryReminder(FoodList list) {
        MESSAGE_SUCCESS += "Here are the upcoming expiry dates:\n";
        for(int i = 0; i < list.size(); i++) {
            Food food = list.get(i);
            MESSAGE_SUCCESS += String.format("%1$s due in %2$s\n", food.getName(), ParserUtil.parseDateToString(food.getExpiryDate()));
        }
        MESSAGE_SUCCESS += "\n";
    }

    /**
     * Creates the list of reminders based on items that are low in stock.
     * @param list The list of low stock reminders.
     */

    private void buildStockReminder(FoodList list) {
        MESSAGE_SUCCESS += "Here are the food products that are low in stock:\n";
        for(int i = 0; i < list.size(); i++) {
            Food food = list.get(i);
            MESSAGE_SUCCESS += String.format("%1$s : %2$s left\n", food.getName(), food.getStock());
        }
    }

    /**
     * Shows the list of food products that are low on stock (Default: less than quantity of 5)
     * or approaching/nearing its expiry date (Default: by a week or lesser).
     *
     * @param model
     * @param storage storage of Cube
     * @return Message feedback to user
     * @throws CommandException
     */

    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        FoodList list = model.getFoodList();
        FoodList stockReminder = new FoodList();
        FoodList expiryReminder = new FoodList();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date()); // get current time
        cal.add(Calendar.DATE, daysToExpiry); // deadline within 7 days
        for (int i = 0; i < list.size(); i++) {
            Food food = list.get(i);
            Date expiryDate = food.getExpiryDate();
            int stock = food.getStock();

            if (expiryDate != null && expiryDate.before(cal.getTime()) && expiryDate.after(new Date())) {
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
