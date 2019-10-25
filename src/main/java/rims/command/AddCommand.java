package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimException;
import rims.resource.Item;
import rims.resource.ReservationList;
import rims.resource.Resource;

public class AddCommand extends Command {
    protected String resourceName;
    protected String type;

    public AddCommand(String itemName, String type) {
        this.resourceName = itemName;
        if (type.equals("room")) {
            this.type = "R";
        }
        else if (type.equals("item")) {
            this.type = "I";
        }
    }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimException {
        int resource_id = resources.generateResourceId();
        ReservationList list = new ReservationList();
        Resource newResource = new Item(resource_id, type, resourceName, list);
        resources.addResource(newResource);   
        ui.printLine();
        ui.print("The following resource(s) have been successfully added:");
        ui.print(newResource.toString());
        ui.printLine();
    }
}
