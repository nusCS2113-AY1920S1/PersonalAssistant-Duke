package seedu.hustler.logic.command.shop;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.OneWordAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command to check the inventory of the user.
 */
public class InventoryCommand extends Command {

    /**
     * The given user input.
     */
    private final String[] userInput;

    /**
     * The anomaly class to check if the user input is valid.
     */
    private OneWordAnomaly inventoryAnomaly = new OneWordAnomaly();

    /**
     * Constructs an inventory command with the given user input.
     * @param userInput the given user input.
     */
    public InventoryCommand(String[] userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute() {
        Ui ui = new Ui();
        try {
            inventoryAnomaly.detect(this.userInput);
            ui.listInventory(Hustler.inventory.getItems());
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }

    }
}
