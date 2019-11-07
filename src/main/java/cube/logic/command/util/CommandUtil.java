/**
 * This class of helper functions for command.
 *
 * @author tygq13
 */
package cube.logic.command.util;

import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.logic.command.exception.CommandException;
import cube.logic.command.exception.CommandErrorMessage;
import cube.model.promotion.PromotionList;

public class CommandUtil {

	/**
	 * The class checks that a given food name is not in the food list.
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
     * The class checks that a given food name is in the food list.
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
     * The class checks that a given food tyep is in the food list.
     *
     * @param list The food list.
     * @param foodType The food type to check.
     * @throws CommandException If the given food type is not inside the food list.
     */
    public static void requireValidType(FoodList list, String foodType) throws CommandException {
        if (!list.existsType(foodType)) {
            throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
        }
    }

    /**
     * The class checks that a given index is valid.
     *
     * @param list The food list.
     * @param index The food index to check.
     * @throws CommandException If the given index is invalid.
     */
    public static void requireValidIndex(FoodList list, int index) throws CommandException {
        if (index < 0 || index >= list.size()) {
            throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
        }
    }

    /**
     * The class checks that a given index is valid.
     *
     * @param list The food list.
     * @param quantity The food quantity to check.
     * @throws CommandException If the given index is invalid.
     */
    public static void requireValidQuantity(Food food, int quantity) throws CommandException {
    	if (quantity < 0 || quantity > food.getStock()) {
    		throw new CommandException(CommandErrorMessage.INVALID_QUANTITY_SOLD);
    	}
    }
}