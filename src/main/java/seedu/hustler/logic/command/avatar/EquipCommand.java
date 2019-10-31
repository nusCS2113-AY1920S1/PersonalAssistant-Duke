package seedu.hustler.logic.command.avatar;

import seedu.hustler.Hustler;
import seedu.hustler.data.AvatarStorage;
import seedu.hustler.game.avatar.Inventory;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.InventoryAnomaly;
import seedu.hustler.ui.Ui;
import java.io.IOException;

/**
 * The command which handles the equipping of shop items.
 */
public class EquipCommand extends Command {

    /**
     * The given user input containing the index of the item to equip.
     */
    private final String[] userInput;

    /**
     * The anomaly class to check if the given command is valid.
     */
    private InventoryAnomaly anomaly = new InventoryAnomaly();

    /**
     * Constructs an equip command with the given user input.
     * @param userInput the given user input.
     */
    public EquipCommand(String[] userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute() throws IOException {
        Ui ui = new Ui();
        Inventory inventory = Hustler.inventory;
        try {
            anomaly.detect(this.userInput);
            int parsedInt = Integer.parseInt(this.userInput[1]);
            Hustler.avatar.equip(inventory.get(parsedInt - 1));
            ui.showEquipped(Hustler.inventory.get(parsedInt - 1));
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
