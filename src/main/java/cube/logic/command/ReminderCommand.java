package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.ui.Ui;
import cube.storage.StorageManager;

import java.util.Calendar;
import java.util.Date;

public class ReminderCommand extends Command{

    /**
     * Always returns false since this is not an exit command.
     *
     * @return false.
     */
    @Override
    public boolean isExit() {
        return false;
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
    public void execute(FoodList list, Ui ui, StorageManager storage) {
        FoodList stockReminder = new FoodList();
        FoodList expiryReminder = new FoodList();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date()); // get current time
        cal.add(Calendar.DATE, 7); // deadline within 7 days
        for (int i = 0; i < list.size(); i++) {
            Food food = list.get(i);
            Date expiryDate = food.getExpiryDate();
            int stock = food.getStock();

            if (expiryDate != null && expiryDate.before(cal.getTime())) {
                expiryReminder.add(food);
            }

            if (stock != 0 && stock < 5 ) {
                stockReminder.add(food);
            }
        }
        ui.showExpiryReminder(expiryReminder);
        System.out.println();
        ui.showStockReminder(stockReminder);
    }
}
