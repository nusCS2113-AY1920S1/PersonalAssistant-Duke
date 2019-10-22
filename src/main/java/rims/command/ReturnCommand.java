package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

public class ReturnCommand extends Command {
    protected int resource_id;
    protected int reservation_id;

    public ReturnCommand(int resource_id, int reservation_id){
        this.resource_id=resource_id;
        this.reservation_id=reservation_id;
    }
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {
        resources.deleteSingleReservation(resource_id, reservation_id);
    }
}