package seedu.hustler.logic.command.avatar;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.InventoryAnomaly;
import seedu.hustler.ui.Ui;

import java.io.IOException;

public class EquipCommand extends Command {

    private final String[] userInput;
    private InventoryAnomaly inventoryAnomaly = new InventoryAnomaly();

    public EquipCommand(String[] userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute() throws IOException {
        Ui ui = new Ui();
        //int parsedInt = Integer.parseInt(this.userInput[1]);
        try {
            inventoryAnomaly.detect(this.userInput);
            int parsedInt = Integer.parseInt(this.userInput[1]);
            Hustler.inventory.getToEquip(parsedInt - 1);

        } catch (CommandLineException e) {
            ui.show_message(e.getMessage());
        }


    }
}
