package rims.command;

import rims.resource.Resource;
import rims.resource.Reservation;
import rims.resource.ReservationList;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

import rims.exception.RimsException;

import java.io.IOException;
import java.util.ArrayList;

//@@author isbobby
/**
 * Implements the returning of multiple Resources by removing the relevant Reservation objects.
 */
public class ReturnCommand extends Command {
    protected int userId;
    protected ArrayList<Integer> resourceIds;
    protected ArrayList<Integer> reservationIds;

    /**
     * Constructor for a ReturnCommand.
     * @param userId the ID of the user whose Resources are being returned.
     * @param resourceIds an array of resource IDs representing the Resources to be returned
     * @param reservationIds an array of reservation IDs representing the Reservations to be removed.
     */
    public ReturnCommand(int userId, ArrayList<Integer> resourceIds, ArrayList<Integer> reservationIds) {
        this.userId = userId;
        this.resourceIds = resourceIds;
        this.reservationIds = reservationIds;
    }

    /**
     * Obtains the Resource objects that represents the Resources being returned, and removes the corresponding
     * Reservation objects that represents the booking of the Resource.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus far.
     * @throws RimsException if either the resource ID or reservation ID specified is invalid.
     */
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimsException {
        storage.saveToFile(resources.getResources());
        ArrayList<Reservation> cancelledReservations = new ArrayList<Reservation>();
        for (int i = 0; i < resourceIds.size(); i++) {
            Resource thisResource = resources.getResourceById(resourceIds.get(i));
            Reservation cancelledReservation = thisResource.getReservations().getReservationById(reservationIds.get(i));
            thisResource.getReservations().cancelReservationById(reservationIds.get(i));
            cancelledReservations.add(cancelledReservation);
        }
        ui.printLine();
        ui.print("Done! I've removed the following reservation(s):\n");
        for (int j = 0; j < cancelledReservations.size(); j++) {
            ui.print(resources.getResourceById(cancelledReservations.get(j).getResourceId()).toString());
            ui.print("\t" + cancelledReservations.get(j).toString());
        }
        ui.printLine();

    }

    private String reservationsIdsToString(ArrayList<Integer> reservationIds) {
        String reservationsIdsString = "";
        for (int i : reservationIds) {
            reservationsIdsString += (i + ", ");
        }
        return reservationsIdsString;
    }

}