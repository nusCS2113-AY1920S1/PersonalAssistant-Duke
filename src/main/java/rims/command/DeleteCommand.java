package rims.command;

import rims.core.ReservationList;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

public class DeleteCommand extends Command {
    protected int resource_id;

    public DeleteCommand(int resource_id){
        this.resource_id = resource_id;
    }

    /**
     * Implementing cascade delete: select a item to delete via resource ID, it will delete
     * the resource itself from the resource file as well as all reservations associated with it.
     * 
     * Use with care!
     */
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources, ReservationList reservations) throws Exception {
        resources.cascadeDeleteResourceById(resource_id);
        reservations.deleteReservationsByResourceID(resource_id);
    }
}
