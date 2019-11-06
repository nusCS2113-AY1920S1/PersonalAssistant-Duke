package seedu.hustler.logic.parser;

import seedu.hustler.logic.command.task.editcommands.EditCommand;
import seedu.hustler.logic.command.achievementCommand.AchievementCommand;
import seedu.hustler.logic.command.shop.BuyCommand;
import seedu.hustler.logic.command.avatar.CheckAvatarCommand;
import seedu.hustler.logic.command.avatar.EquipCommand;
import seedu.hustler.logic.command.avatar.SetNameCommand;
import seedu.hustler.logic.command.schedulecommands.AddEntry;
import seedu.hustler.logic.command.schedulecommands.RemoveEntry;
import seedu.hustler.logic.command.schedulecommands.UpdateEntry;
import seedu.hustler.logic.command.shop.InventoryCommand;
import seedu.hustler.logic.command.shop.ShopListCommand;
import seedu.hustler.logic.command.task.*;
import seedu.hustler.logic.command.timer.*;
import seedu.hustler.data.CommandLog;
import seedu.hustler.Hustler;
import seedu.hustler.task.Task;
import seedu.hustler.logic.command.task.editcommands.EditDifficultyCommand;
import seedu.hustler.logic.command.task.editcommands.Edit;
import seedu.hustler.logic.command.task.editcommands.EditDescriptionCommand;

/**
 * Parses which edit to be made.
 */
public class EditCommandParser extends Parser {
    /**
     * This method takes the raw user input for edit command and finds out which
     * edit is to be made.
     *
     * @param rawInput user's single line string input
     * @return an Edit to be made.
     */
    public Edit parse(String rawInput) {
        int index = rawInput.indexOf("/id") + 1;
        
        Task task = Hustler.list.get(index);
        
        if (rawInput.contains("/difficulty")) {
            int difficultyIndex = rawInput.indexOf("/difficulty") + 1;
            String difficulty = rawInput.substring(difficultyIndex);
            return new EditDifficultyCommand(task, difficulty);
        }
        else if (rawInput.contains("/description")) {
            int descriptionIndex = rawInput.indexOf("/description") + 1;
            String description = rawInput.substring(descriptionIndex);
            return new EditDescriptionCommand(task, description);
        }
    }
}
