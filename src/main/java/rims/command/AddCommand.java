package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimException;

public class AddCommand extends Command {
    protected String resourceName;
    protected int qty;
    protected char type;

    public AddCommand(String itemName, int qty) {
        this.resourceName = itemName;
        this.qty = qty;
        this.type = 'I';
    }

    public AddCommand(String roomName) {
        this.resourceName = roomName;
        this.qty = 1;
        this.type = 'R';
    }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimException {

        for (int i = 0; i < qty; i++) {
            resources.addResource(resourceName,type);
        }

        ui.printLine();
        ui.print("The following resource(s) have been successfully added:");
        if (type == 'I') {
            ui.print("[I] " + resourceName + " (qty: " + qty + ")");
        } else if (type == 'R') {
            ui.print("[R] " + resourceName);
        } else {
            throw new RimException();
        }
        ui.printLine();
    }
}
