package rims.command;

import rims.core.ReservationList;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

public class CloseCommand extends Command {

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources, ReservationList reservations) throws Exception {
        storage.saveToResourceFile(resources.getResourceList());
        storage.saveToReserveFile(reservations.getReservationList());
        ui.farewell();
        setExitCode();
    }
}