package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.resource.Resource;

public class ReserveCommand extends Command {
    protected int reservation_id;
    protected String resourceName;
    protected int qty;
    protected int user_id;
    protected String startDate;
    protected String endDate;
    protected int resource_id;

    public ReserveCommand(){
        ;
    }

    /**
     * This command supports the reservation for a single resource.
     * @param resource_id
     * @param user_id
     * @param startDate
     * @param endDate
     */
    public ReserveCommand(int resource_id, int user_id, String startDate, String endDate) {
        this.resource_id = resource_id;
        this.user_id = user_id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * A reserve command requires id, type, name and quantity
     * 
     * @exception IDexists
     * @exception quantityvalid
     */
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {
        Resource thisResource = resources.getResourceByResourceId(resource_id);
        ReservationList currentReservations = thisResource.getReservations();
        reservation_id = currentReservations.generateReservationId();
        
        Reservation newReservation = new Reservation(reservation_id, resource_id, user_id, startDate, endDate);
        ui.printSuccessReservation(newReservation);
        currentReservations.addNewReservation(newReservation);
    }
}