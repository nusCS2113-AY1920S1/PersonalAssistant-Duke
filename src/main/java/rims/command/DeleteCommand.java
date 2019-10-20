package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimException;

public class DeleteCommand extends Command {
    protected String resourceName;
    protected int qty;
    protected char type;

    public DeleteCommand(String itemName, int qty) {
        this.resourceName = itemName;
        this.qty = qty;
        this.type = 'I';
    }

    public DeleteCommand(String roomName) {
        this.resourceName = roomName;
        this.qty = 1;
        this.type = 'R';
    }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {

        int availableResourceQty = resources.getAvailableQuantity(resourceName);
        if (availableResourceQty < qty) {
            throw new RimException("Insufficient available resources to delete!"); //Resources not booked not enough to delete
        }

        for (int i = 0; i < qty; i++) {
            resources.deleteResource(resourceName);
        }

        ui.printLine();
        ui.print("The following resource(s) have been successfully removed:");
        if (type == 'I') {
            ui.print("[I] " + resourceName  + " (qty: " + qty + ")");
        } else if (type == 'R') {
            ui.print("[R] " + resourceName);
        } else {
            throw new RimException("Invalid resource type!");
        }
        ui.printLine();
    }
}
