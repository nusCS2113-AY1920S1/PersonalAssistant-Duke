package seedu.hustler.logic.command.shop;

import seedu.hustler.Hustler;
import seedu.hustler.logic.command.Command;
import seedu.hustler.data.ShopStorage;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.parser.anomaly.BuyAnomaly;
import seedu.hustler.ui.Ui;

import java.io.IOException;

/**
 * Command to purchase an item in the shop with the given index.
 */
public class BuyCommand extends Command {
    /**
     * The index of the item desired.
     */
    private int index;

    /**
     * User input to parse.
     */
    private String[] userInput;

    /**
     * Detect anomalies for input.
     * @param index
     */
    private BuyAnomaly anomaly = new BuyAnomaly();

    /**
     * Constructs a buyCommand with the index of the item.
     *
     * @param userInput initialization of the userInput
     */
    public BuyCommand(String[] userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            this.index = Integer.parseInt(userInput[1]);
            Hustler.shopList.buy(this.index - 1);
            ShopStorage.update();
            Hustler.inventory.updateInventory();
        } catch (CommandLineException e) {
            ui.show_message(e.getMessage());
        }
    }
}
