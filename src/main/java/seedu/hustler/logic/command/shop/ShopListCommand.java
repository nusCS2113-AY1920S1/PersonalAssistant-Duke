package seedu.hustler.logic.command.shop;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.OneWordAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command to execute the listing of items in the shop.
 */
public class ShopListCommand extends Command {

    private String[] userInput;
    private OneWordAnomaly anomaly;

    public ShopListCommand(String[] userInput) {
        this.userInput = userInput;
        this.anomaly = new OneWordAnomaly();
    }

    /**
     * Lists the items in the shop.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            Hustler.shopList.list();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
