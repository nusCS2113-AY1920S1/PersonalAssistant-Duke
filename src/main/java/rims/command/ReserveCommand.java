package rims.command;

import java.util.ArrayList;

import rims.core.ReservationList;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.reserve.Reservation;
import rims.util.DateRange;

public class ReserveCommand extends Command {
    protected int reservationID;
    protected String resourceName;
    protected int qty;
    protected int userid;
    protected String startDate;
    protected String endDate;
    protected int resourceId;

    public ReserveCommand(String itemName, int qty, int userid, String startDate, String endDate) {
        resourceName = itemName;
        this.qty = qty;
        this.userid = userid;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ReserveCommand(String roomName, int userid, String startDate, String endDate) {
        resourceName = roomName;
        this.qty = 1;
        this.userid = userid;
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
    public void execute(Ui ui, Storage storage, ResourceList resources, ReservationList reservations) throws Exception {
        //calculate a unique reservationID, same formula as resource id
        this.reservationID = 0;
        if(reservations.size()>0){
        this.reservationID = reservations.getReservationByIndex(reservations.size()-1).getReservationId() + 1;
        }
        Reservation newReservation = new Reservation(reservationID, resourceId, userid, qty, startDate, endDate);
        ui.printLine();
        ui.print("The following reservation has been registerd:");
        ui.print(newReservation.toString());
        ui.printLine();
        reservations.addNewReservation(newReservation);
    }
}