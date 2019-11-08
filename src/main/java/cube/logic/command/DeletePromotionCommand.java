/**
 * The command deletes a promotion from the promotion list.
 *
 * @@author parvathi14
 */

package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;
import cube.model.ModelManager;
import cube.model.promotion.Promotion;
import cube.model.promotion.PromotionList;
import cube.storage.StorageManager;

public class DeletePromotionCommand extends Command{

    /**
     * Use enums to specify which promotion is to be deleted.
     */
    public enum DeletePromotionBy {
        INDEX, ALL
    }

    private int deletePromotionIndex;
    private String all;
    private DeletePromotionBy deleteParam;

    public static final String MESSAGE_SUCCESS_SINGLE = "Nice! I've removed this promotion:\n"
            + "%1$s\n"
            + "Now you have %2$s promotions in the list.\n";
    public static final String MESSAGE_SUCCESS_ALL = "Nice! I've removed all the promotions from your list. \n"
            +"Total number removed is:"
            + "%1$s.\n";

    /**
     * Default constructor of ListPromotionCommand.
     */
    public DeletePromotionCommand() {}

    /**
     * The constructor for deleting based on index value.
     * @param index The index of the promotion to be deleted.
     * @param deleteParam The parameter is used to specify the type of deletion.
     */

    public DeletePromotionCommand(int index, String deleteParam) {
        this.deletePromotionIndex = index - 1;
        this.deleteParam = DeletePromotionBy.valueOf(deleteParam);
    }

    /**
     * The constructor for deleting all the promotions.
     * @param all User input stating that they want to remove ALL the promotions from the list.
     * @param deleteParam The parameter is used to specify the type of deletion.
     */

    public DeletePromotionCommand(String all, String deleteParam) {
        this.all = all;
        this.deleteParam = DeletePromotionBy.valueOf(deleteParam);
    }

    /**
     * The class removes the promotion(s) the user wishes to remove.
     *
     * @param storage The storage we have.
     * @return The feedback to user for delete promotion command.
     * @throws CommandException If deletion is unsuccessful.
     */

    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        PromotionList list = model.getPromotionList();
        Promotion toDelete;

        switch (deleteParam) {
            case INDEX:
                CommandUtil.requireValidIndexPromotion(list, deletePromotionIndex);
                toDelete = list.get(deletePromotionIndex);
                list.removeIndex(deletePromotionIndex);
                storage.storePromotionList(list);
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, toDelete, list.size()));
            case ALL:
                int count = list.size();
                list.clear();
                storage.storePromotionList(list);
                return new CommandResult(String.format(MESSAGE_SUCCESS_ALL, count));
        }
         return null;

    }
}
