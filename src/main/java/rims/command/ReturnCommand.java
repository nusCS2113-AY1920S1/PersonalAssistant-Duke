package rims.command;

import rims.resource.Resource;
import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimsException;

/**
 * Implements the returning of a Resource by removing its relevant Reservation object.
 */
public class ReturnCommand extends Command {
    protected int userId;
    protected int resourceId;
    protected int reservationId;

    /**
     * Constructor for a ReturnCommand.
     * @param userId the ID of the user whose Resource is to be returned.
     * @param resourceId the ID of the Resource which is being returned
     * @param reservationId the ID of the Reservation which is being removed.
     */
    public ReturnCommand(int userId, int resourceId, int reservationId) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.reservationId = reservationId;
    }

    /**
     * Obtains the Resource object that represents the Resource being returned, and removes its corresponding
     * Reservation object that represents the booking of the Resource.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus far.
     * @throws RimsException if either the resource ID or reservation ID specified is invalid.
     */
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