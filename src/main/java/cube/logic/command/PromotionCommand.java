/**
 * The command adds a new promotion to the promotion list.
 *
 * @@author parvathi14
 */

package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.model.promotion.Promotion;
import cube.model.promotion.PromotionList;
import cube.storage.StorageManager;

public class PromotionCommand extends Command{
    private final Promotion newPromotion;
    public Food promotionFood;
    public static final String MESSAGE_SUCCESS = "New promotion added: \n"
            + "%1$s\n"
            + "Now you have %2$s promotional items in the list.\n";

    /**
     * Default constructor.
     * @param promotion the promotion to be added.
     */
    public PromotionCommand(Promotion promotion) {
        this.newPromotion = promotion;
    }

    /**
     * Acquires the food for which the promotion has to be implemented.
     * @param list The food list.
     * @throws CommandException
     */
    public void obtainPromotionFood(FoodList list) throws CommandException {
        CommandUtil.requireValidName(list, newPromotion.getName());
        promotionFood = list.get(newPromotion.getName());
    }

    /**
     * Adds promotion to promotionList and store it if the promotion does not already exist, otherwise throws Command exception.
     *
     * @param model
     * @param storage The storage we have
     * @return Message feedback to user.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        FoodList list = model.getFoodList();
        PromotionList promotionList = model.getPromotionList();
        obtainPromotionFood(list);
        CommandUtil.requirePromotionNotExists(promotionList, newPromotion.getName());
        CommandUtil.requireValidPromotionDates(newPromotion.getStartDate(), newPromotion.getEndDate());
        double tempPrice = promotionFood.getPrice();
        double newPrice;
        newPrice = (newPromotion.getDiscount()/100)*tempPrice;
        newPromotion.setPromotionalPrice(newPrice);

        promotionList.add(newPromotion);
        //storage.storePromotionList(promotionList);

        return new CommandResult(String.format(MESSAGE_SUCCESS, newPromotion, promotionList.size()));
    }
}
