package duke.command.inventory;
import duke.command.Command;
import duke.commons.DukeException;
import duke.entities.inventory.Inventory;
import duke.parser.CommandParser;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

public class AddInventoryCommand extends Command {
    private final Map<String, List<String>> params;
    private Inventory inventory;

    public AddInventoryCommand(Map<String, List<String>> params) throws DukeException {
        this.params = params;
    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        inventory = new Inventory();
        CommandParser.modifyInventory(params, inventory);
        addInventory(inventory, bakingList);
        storage.serialize(bakingList);
        ui.refreshInventoryList(bakingList.getInventoryList());
        ui.showMessage("Inventory added");
    }

    private void addInventory(Inventory inventory, BakingList bakingList) {
        bakingList.getInventoryList().add(inventory);
    }

}
