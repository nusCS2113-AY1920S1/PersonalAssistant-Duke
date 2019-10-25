package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.resource.Item;
import rims.resource.Room;
import rims.resource.ReservationList;
import rims.resource.Resource;

public class AddCommand extends Command {
    protected String resourceName;
    protected String resourceType;
    protected int qty;

    public AddCommand(String roomName) {
        this.resourceName = roomName;
        this.resourceType = "room";
    }

    public AddCommand(String itemName, int qty) {
        this.resourceName = itemName;
        this.resourceType = "item";
        this.qty = qty;
    }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) {
        if (resourceType.equals("room")) {
            int resourceId = resources.generateResourceId();
            Room newRoom = new Room(resourceId, resourceName);
            resources.add(newRoom);
            ui.printLine();
            ui.print("The following room has been successfully added:");
            ui.print(newRoom.toString());
            ui.printLine();
        }
        else if (resourceType.equals("item")) {
            for (int i = 0; i < qty; i++) {
                int resourceId = resources.generateResourceId();
                Item newItem = new Item(resourceId, resourceName);
                resources.add(newItem);
                if (i == qty - 1) {
                    ui.printLine();
                    ui.print("The following item(s) have been successfully added:");
                    ui.print(newItem.toString() + " (qty: " + qty + ")");
                    ui.printLine();
                }
            }
        }
    }
}
