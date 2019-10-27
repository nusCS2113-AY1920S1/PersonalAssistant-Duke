package seedu.hustler.logic.command.avatar;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.InventoryAnomaly;
import seedu.hustler.ui.Ui;

import java.io.IOException;

public class EquipCommand extends Command {

    private final String[] index;
    private InventoryAnomaly inventoryAnomaly = new InventoryAnomaly();

    public EquipCommand(String[] index) {
        this.index = index;
    }

    @Override
    public void execute() throws IOException {
        Ui ui = new Ui();
        int parsedInt = Integer.parseInt(index[1]);
        try {
            inventoryAnomaly.detect(this.index);
        } catch (CommandLineException e) {
            ui.show_message(e.getMessage());
        }

        Hustler.inventory.getToEquip(parsedInt - 1);
    }
}
