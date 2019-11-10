/**
 * The command list all the promotions.
 *
 * @author parvathi14
 */

package cube.logic.command;

import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.model.promotion.PromotionList;
import cube.storage.StorageManager;

public class ListPromotionCommand extends Command{
    public static final String MESSAGE_SUCCESS = "Here are the list of promotions: \n";

    /**
     * Default constructor of ListPromotionCommand.
     */
    public ListPromotionCommand() {}

    /**
     * Shows the list of promotion.
     *
     * @param model
     * @param storage The storage we have.
     * @return Message feedback to user.
     */
    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) {
        PromotionList list = model.getPromotionList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, list));

    }

}
