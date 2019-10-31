package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.resource.Item;
import rims.resource.Room;
import rims.resource.ReservationList;
import rims.resource.Resource;

import java.io.IOException;

//@@author hin1
/**
 * Implements the addition of a new Resource to the ResourceList.
 */
public class AddCommand extends Command {
    protected String resourceName;
    protected String resourceType;
    protected int qty;

    /**
     * Constructor of a new AddCommand for a new Room.
     * @param roomName the name of the new Room to be added to the ResourceList.
     */
    public AddCommand(String roomName) {
        resourceName = roomName;
        resourceType = "room";
        canModifyData = true;
        commandUserInput = "add " + roomName + " (room)";
    }

    /**
     * Constructor of a new AddCommand for a new Item.
     * @param itemName the name of the new Item to be added to the ResourceList.
     * @param quantity the quantity of the new Item to be added.
     */
    public AddCommand(String itemName, int quantity) {
        resourceName = itemName;
        resourceType = "item";
        qty = quantity;
        canModifyData = true;
        commandUserInput = "add " + qty + " " + itemName + " (item)";
    }

    /**
     * Creates the new Resource, adds it to the ResourceList, and prints a message to the CLI
     * that the Resource has been successfully added.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus far.
     */
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws IOException {
        storage.saveToFile(resources.getResources());

        if (resourceType.equals("room")) {
            int resourceId = resources.generateResourceId();
            Room newRoom = new Room(resourceId, resourceName);
            resources.add(newRoom);
            ui.printLine();
            ui.print("The following room has been successfully added:");
            ui.print(newRoom.toString());
            ui.printLine();
        } else if (resourceType.equals("item")) {
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
