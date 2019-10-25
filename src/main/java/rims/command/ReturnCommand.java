package rims.command;

import rims.resource.Resource;
import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimsException;

// c = new ReturnCommand(userId, borrowedResource, reservationId);

public class ReturnCommand extends Command {
    protected int userId;
    protected int resourceId;
    protected int reservationId;

    public ReturnCommand(int userId, int resourceId, int reservationId) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.reservationId = reservationId;
    }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimsException {
        Resource thisResource = resources.getResourceById(resourceId);
        Reservation cancelledReservation = thisResource.getReservations().getReservationById(reservationId);
        thisResource.getReservations().cancelReservationById(reservationId);
        ui.printLine();
        ui.print("Done! I've removed the following reservation:");
        ui.print(cancelledReservation.toString());
        ui.printLine();

    }
}