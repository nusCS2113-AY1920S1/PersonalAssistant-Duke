package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.storage.StorageManager;

import java.util.Calendar;
import java.util.Date;

public class PromotionCommand extends Command{
    String foodName
    double discount;
    Date startDate;
    Date endDate;
    Food promotionFood;
    double newPrice;

    public PromotionCommand(String foodName, double discount, Date startDate, Date endDate) {
        this.foodName = foodName;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Acquires the food for which the promotion has to be implemented.
     * @param list The food list.
     * @throws CommandException
     */
    public void obtainFoodSold(FoodList list) throws CommandException {
        CommandUtil.requireValidName(list, foodName);
        promotionFood = list.get(foodName);
    }


    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        double tempPrice = promotionFood.getPrice();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        if (cal.getTime().before(endDate) && cal.getTime().after(startDate)) {
            newPrice = (discount/100)*tempPrice;
        }
    }

    public double getPromotionPrice() {
        return newPrice;
    }
}
