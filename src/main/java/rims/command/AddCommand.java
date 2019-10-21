package rims.command;

import rims.core.ReservationList;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimException;
import rims.resource.Item;
import rims.resource.Resource;

public class AddCommand extends Command {
    protected String resourceName;
    protected int qty;
    protected String type;

    public AddCommand(String itemName, int qty) {
        this.resourceName = itemName;
        this.qty = qty;
        this.type = "I";
    }

    public AddCommand(String roomName) {
        this.resourceName = roomName;
        this.qty = 1;
        this.type = "R";
    }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources, ReservationList reserves) throws RimException {
        //ID = (ID of the last element + 1)
        //first get the id, then check if this id already exists, throws internal error first
        int id = 0;
        if (resources.size()>0){
            id = resources.getResourceViaIndex(resources.size()-1).getResourceId() + 1;
        }
        Resource newResource = null;
        if (type=="I"){
            newResource = new Item(id, type, resourceName, qty);
        }
        else if ( type == "R" ){
            newResource = new Item(id, type, resourceName, qty);
        }
        
        resources.addResource(newResource);

        ui.printLine();
        ui.print("The following resource(s) have been successfully added:");
        ui.print("\t" + newResource.toString());
        ui.printLine();
    }
}
