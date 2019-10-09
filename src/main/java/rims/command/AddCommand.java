package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimException;
import rims.resource.Item;
import rims.resource.Resource;
import rims.resource.Room;

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
        Resource newResource = null;
        int resourceId = resources.getQuantity();

        if (type == 'I') {
            for (int i = 0; i < qty; i++) {
                newResource = new Item(resourceName, resourceId, false);
                resources.addResource(newResource);
                resourceId++;
            }
        } else if (type == 'R') {
            newResource = new Room(resourceName, resourceId, false);
            resources.addResource(newResource);
        } else {
            //RimException for invalid room type; should put in Enums instsead of I and R?
            throw new RimException();
        }

        ui.printLine();
        ui.print("The following resource(s) have been succesfully added:");
        if (newResource.getType() == 'I') {
            ui.print(newResource.toString() + " (qty: " + qty + ")");
        } else if (newResource.getType() == 'R') {
            ui.print(newResource.toString());
        } else {
            throw new RimException();
        }
        ui.printLine();
    }
}
