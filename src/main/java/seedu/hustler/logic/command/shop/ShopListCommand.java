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

    /**
     * The given user input.
     */
    private String[] userInput;

    /**
     * The anomaly class to check if the given command is valid.
     */
    private OneWordAnomaly anomaly;

    /**
     * Constructs a shop list command with the user input.
     * @param userInput the given user input.
     */
    public ShopListCommand(String[] userInput) {
        this.userInput = userInput;
        this.anomaly = new OneWordAnomaly();
    }

    @Override
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            ui.showShopList(Hustler.shopList.getShopList());
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
