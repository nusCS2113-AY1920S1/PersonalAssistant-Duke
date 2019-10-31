package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.storage.StorageManager;

public class UpdateCommand extends Command{

    private Food newFood;
    private int[] changeBit;
    public static final String MESSAGE_SUCCESS = "Food updated: \n"
            + "You have updated:\n"
            + "%1$s\n"
            + "to: \n"
            + "%2$s.\n";

    /**
     * Default constructor.
     * @param food the food to be added.
     */
    public UpdateCommand (Food food, int[] changeBit) {
        this.newFood = food;
        this.changeBit = changeBit;
    }

    /**
     * Adds food to foodList and store it if the food does not already exists, otherwise throws
     * Command exception.
     *
     * @param storage The current Storage.
     * @return The message feedback to user before Programme Exit.
     */
    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        FoodList list = model.getFoodList();
        CommandUtil.requireValidName(list, newFood.getName());
        String oldInfo = list.get(newFood.getName()).toString();
        if(changeBit[0] == 1){
            //type
            list.get(newFood.getName()).setType(newFood.getType());
        }
        if(changeBit[1] == 1){
            //price
            list.get(newFood.getName()).setPrice(newFood.getPrice());
        }
        if(changeBit[2] == 1){
            //stock
            list.get(newFood.getName()).setStock(newFood.getStock());
        }
        if(changeBit[1] == 1){
            //expiry date
            list.get(newFood.getName()).setExpiryDate(newFood.getExpiryDate());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, oldInfo, newFood));
    }
}
