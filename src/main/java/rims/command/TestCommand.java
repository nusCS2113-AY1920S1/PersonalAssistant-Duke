package rims.command;

import rims.core.ReservationList;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

public class TestCommand extends Command {

    public void execute(Ui ui, Storage storage, ReservationList rlist) {
        ui.ErrorPrint("Error");
        //ui.printReservationsArray(rlist.getReserveList());
    }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources, ReservationList reservations) throws Exception {
        ;

    }
}