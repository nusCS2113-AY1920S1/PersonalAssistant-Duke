package rims.command;

import rims.core.ReservationList;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

public class ReturnCommand extends Command {
    protected int reservation_id;

    public ReturnCommand(int reservation_id) {
        this.reservation_id=reservation_id;
    }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources, ReservationList reservations) throws Exception {
        reservations.deleteReservationByReservationID(reservation_id);
    }
}