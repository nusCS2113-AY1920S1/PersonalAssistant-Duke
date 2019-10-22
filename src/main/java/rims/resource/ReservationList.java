package rims.resource;

import java.util.ArrayList;

import rims.core.Ui;
import rims.util.DateRange;

public class ReservationList {
    protected ArrayList<Reservation> reservations;
    private Ui ui;

    public ReservationList() {
        this.reservations = new ArrayList<Reservation>();
    }

    public ReservationList(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public ReservationList(ArrayList<Reservation> reservations, Ui ui) {
        this.reservations = reservations;
        this.ui = ui;
    }

    /**
     * This section contains all methods performing READ operations
     */

    /**
     * Get the whole list
     * 
     * @return the entire reserve list
     */
    public ArrayList<Reservation> getReservationList() {
        return this.reservations;
    }

    public Reservation getReservationByIndex(int i) {
        return reservations.get(i);
    }

    /**
     * This method generates a reservation id by taking the last element's id + 1
     * 
     * @return id
     */
    public int generateReservationId() {
        int i = 0;
        if (reservations.size() > 0) {
            i = reservations.get(reservations.size() - 1).getReservationId() + 1;
        }
        return i;
    }

    /**
     * Fetch one single reserve given a Loan ID
     * 
     * @param lid Loan Id
     * @return reserve if found
     * @return null if not found
     */
    public ArrayList<Reservation> getReservationListByUid(int uid) {
        ArrayList<Reservation> tempList = new ArrayList<Reservation>();
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getUid() == uid) {
                tempList.add(reservations.get(i));
            }
        }
        return tempList;
    }

    public ArrayList<DateRange> getAllDates(int resource_id) {
        ArrayList<DateRange> listOfDates = new ArrayList<DateRange>();
        for (int i = 0; i < reservations.size(); i++) {

            DateRange newRange = new DateRange(reservations.get(i).getStartDate(), reservations.get(i).getEndDate());
            listOfDates.add(newRange);

        }
        return listOfDates;
    }

    /**
     * This section contains all methods performing CREATE operation
     */
    public void addNewReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    /**
     * This section contains all methods performing DELETE operation
     * 
     * @return
     */
    public void deleteReservationByReservationID(int reservationid) {
        int index_to_remove = -1;
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId() == reservationid) {
                ui.ErrorPrint("The following reservation is removed\n");
                ui.print(reservations.get(i).toString());
                index_to_remove = i;
                break;
            }
        }

        if (index_to_remove != -1) {
            reservations.remove(index_to_remove);
        }
    }

    /**
     * This section contains all utility methods (usually related to the data
     * structure)
     */

    public int size() {
        return reservations.size();
    }
}
