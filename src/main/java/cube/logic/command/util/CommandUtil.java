/**
 * This class of helper functions for command.
 *
 * @author tygq13
 */
//@@author tygq13
package cube.logic.command.util;

import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.logic.command.exception.CommandException;
import cube.logic.command.exception.CommandErrorMessage;
import cube.model.promotion.Promotion;
import cube.model.promotion.PromotionList;

import java.util.Date;
import java.util.Calendar;

public class CommandUtil {

	/**
	 * Checks that a given food name is not in the food list.
	 *
	 * @param list The food list.
	 * @param foodName The food name to check.
	 * @throws CommandException If the given food name is inside the food list.
	 */
    public static void requireNameNotExists(FoodList list, String foodName) throws CommandException {
        if (list.existsName(foodName)) {
            throw new CommandException(CommandErrorMessage.FOOD_ALREADY_EXISTS);
        }
    }

    /**
     * Checks that a given food name is in the food list.
     *
     * @param list The food list.
     * @param foodName The food name to check.
     * @throws CommandException If the given food name is not inside the food list.
     */
    public static void requireValidName(FoodList list, String foodName) throws CommandException {
        if (!list.existsName(foodName)) {
            throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
        }
    }

    /**
     * Checks that a given food type is in the food list.
     *
     * @param list The food list.
     * @param foodType The food type to check.
     * @throws CommandException If the given food type is not inside the food list.
     */
    public static void requireValidType(FoodList list, String foodType) throws CommandException {
        if (!list.existsType(foodType)) {
            throw new CommandException(CommandErrorMessage.INVALID_TYPE);
        }
    }

    /**
     * Checks that a given index is valid.
     *
     * @param list The food list.
     * @param index The food index to check.
     * @throws CommandException If the given index is invalid.
     */
    public static void requireValidIndex(FoodList list, int index) throws CommandException {
        if (index < 0 || index >= list.size()) {
            throw new CommandException(CommandErrorMessage.INVALID_INDEX);
        }
    }

    /**
     * Checks the quantity is valid.
     * @param food The food to check agaisnt.
     * @param quantity The quantity to check.
     * @throws CommandException if quantity is not valid.
     */
    public static void requireValidQuantity(Food food, int quantity) throws CommandException {
    	if (quantity < 0 || quantity > food.getStock()) {
    		throw new CommandException(CommandErrorMessage.INVALID_QUANTITY_SOLD);
    	}
    }

    /**
     * Checks that the expiry date is not before today.
     * @param date The expiry date to check.
     * @throws CommandException if expiry date is before today.
     */
    public static void requireValidExpiryDate(Date date) throws CommandException {
        if (date == null) {
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.before(Calendar.getInstance())) {
            throw new CommandException(CommandErrorMessage.INVALID_EXPIRY_DATE);
        }
    }

    // @@author parvathi14

    /**
     * Checks that a given index is valid in the promotion list.
     *
     * @param list The promotion list.
     * @param index The promotion index to check.
     * @throws CommandException If the given index is invalid.
     */
    public static void requireValidIndexPromotion(PromotionList list, int index)
            throws CommandException {
        if (index < 0 || index >= list.size()) {
            throw new CommandException(CommandErrorMessage.INVALID_INDEX);
        }
    }

    /**
     * Checks that the promotion period is valid
     * i.e. the dates are not before the current date
     * and the end date is not before the start date.
     * @param startDate The start date of the promotion period.
     * @param endDate The end date of the promotion period.
     * @throws CommandException if promotion period is invalid.
     */
    public static void requireValidPromotionDates(Date startDate, Date endDate)
            throws CommandException {
        if (startDate == null || endDate == null) {
            return;
        }

        if (endDate.before(startDate)) {
            throw new CommandException(CommandErrorMessage.INVALID_PROMOTION_PERIOD);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        Date modifiedDate = cal.getTime();

        if (endDate.before(modifiedDate) || startDate.before(modifiedDate)) {
            throw new CommandException(CommandErrorMessage.INVALID_PROMOTION_DATES);
        }
    }

    //@@author ZKathrynx
    /**
     * Checks that the same food does not have two different promotions at the same time.
     * @param promotionList the list of existing promotions.
     * @param newPromotion the new promotion to be added.
     * @throws CommandException if the same food has two different promotions at the same time.
     */
    public static void requireNotOverlappingTime(PromotionList promotionList, Promotion newPromotion)
            throws CommandException {
        String foodName = newPromotion.getName();
        Date start = newPromotion.getStartDate();
        Date end = newPromotion.getEndDate();
        if (!promotionList.existsName(foodName)) {
            return;
        }
        for (int i = 0; i < promotionList.size(); i++) {
            if (promotionList.get(i).getName().equals(foodName)) {
                if (!(end.before(promotionList.get(i).getStartDate())
                        || start.after(promotionList.get(i).getEndDate()))) {
                    throw new CommandException(CommandErrorMessage.PROMOTION_ALREADY_EXISTS);
                }
            }
        }

    }

    /**
     * Checks whether the food item that is going to promote is already free.
     * @param foodList the list of food.
     * @param foodName the food item to promote.
     * @throws CommandException when the food item that is going to promote is already free.
     */
    public static void requireNotFreeItem(FoodList foodList, String foodName) throws CommandException {
        if (foodList.get(foodName).getPrice() == 0) {
            throw new CommandException(CommandErrorMessage.FREE_ITEM);
        }
    }

}