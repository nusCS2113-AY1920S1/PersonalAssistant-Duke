package seedu.hustler.logic.command.shop;

import seedu.hustler.Hustler;
import seedu.hustler.game.achievement.Achievements;
import seedu.hustler.logic.command.Command;
import seedu.hustler.data.ShopStorage;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.parser.anomaly.BuyAnomaly;
import seedu.hustler.ui.Ui;

/**
 * Command to purchase an item in the shop with the given index.
 */
public class BuyCommand extends Command {

    /**
     * The given user input containing the index of the item to purchase.
     */
    private String[] userInput;

    /**
     * The anomaly class to check if the user input is valid.
     */
    private BuyAnomaly anomaly = new BuyAnomaly();

    /**
     * Constructs a buyCommand with the index of the item.
     * @param userInput the given user input.
     */
    public BuyCommand(String[] userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute() {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            int index = Integer.parseInt(userInput[1]) - 1;
            int oldPoints = Achievements.totalPoints;
            if (Hustler.shopList.buy(index).isPresent()) {
                if (oldPoints < Achievements.totalPoints) {
                    ui.showPurchasedSuccess();
                } else {
                    ui.showAlreadyPurchased();
                }
            } else {
                ui.showPurchasedFailure();
            }
            ShopStorage.update();
            Hustler.inventory.updateInventory();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
