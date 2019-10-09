package duke.command.inventory;
import duke.command.Command;
import duke.commons.DukeException;
import duke.entities.inventory.Inventory;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddInventoryCommand extends Command {

    public static final String COMMAND_WORD = "add";
    private Inventory toAdd;

    public AddInventoryCommand(Inventory toAdd) {
        this.toAdd = toAdd;
    }

    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        addInventory(toAdd, bakingList);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
        ui.showMessage("Inventory added");
    }

    private void addInventory(Inventory inventory, BakingList bakingList) {
        bakingList.getInventoryList().add(inventory);
    }
}
