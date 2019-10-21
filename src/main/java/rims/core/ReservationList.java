package rims.core;

import java.util.ArrayList;
import java.util.Collections;

import rims.reserve.Reservation;
import rims.util.DateRange;

public class ReservationList {
    protected ArrayList<Reservation> reservations;
    private Ui ui;

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

    public ArrayList<Reservation> getReservationListByResourceId(int resource_id) {
        ArrayList<Reservation> tempList = new ArrayList<Reservation>();
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getResourceId() == resource_id) {
                tempList.add(reservations.get(i));
            }
        }
        return tempList;
    }

    public ArrayList<DateRange> getAllDatesByResourceId(int resource_id) {
        ArrayList<DateRange> listOfDates = new ArrayList<DateRange>();
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getResourceId() == resource_id) {
                DateRange newRange = new DateRange(reservations.get(i).getStartDate(), reservations.get(i).getEndDate());
                listOfDates.add(newRange);
            }
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

    public void deleteReservationsByResourceID(int resource_id) {
        ArrayList<Integer> indices_to_remove = new ArrayList<Integer>();
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getResourceId() == resource_id) {
                indices_to_remove.add(i);
            }
        }

        // we have to sort the indices in descending order to remove, so the shifting
        // does not affect
        // data structure integrity
        Collections.sort(indices_to_remove, Collections.reverseOrder());

        if (indices_to_remove.size() > 0) {
            for (int i = 0; i < indices_to_remove.size(); i++) {
                ui.ErrorPrint("The following reservation is removed\n");
                ui.print(reservations.get(indices_to_remove.get(i)).toString());
                int index = indices_to_remove.get(i);
                reservations.remove(index);
            }
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
