package rims.resource;

import java.util.ArrayList;

import rims.core.Ui;
import rims.util.DateRange;

public class ReservationList {
    protected ArrayList<Reservation> reservations;

    /**
     * Empty constructor, where an empty ArrayList<Reservation> is constructed. This
     * is to prevent null pointer exceptions but use with care.
     *
     */
    public ReservationList() {
        this.reservations = new ArrayList<Reservation>();
    }

    /**
     * This constructor builds a reservation list for a given resource
     *
     * @param reservations populated list of reservations
     * @param ui           UI
     */
    public ReservationList(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Get the whole list
     * 
     * @return the entire reserve list
     */
    public ArrayList<Reservation> getReservationList() {
        return this.reservations;
    }

    /**
     * Get a reservation residing at index i
     * 
     * @param i
     * @return reservations.get(i)
     */
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
     * Fetch all the pairs of dates in a reservationList under a given resource,
     * populate a list<pair_of_dates> and return this list. This is method is not
     * yet finished!
     * 
     * @param resource_id
     */
    public ArrayList<DateRange> getAllDates(int resource_id) {
        ArrayList<DateRange> listOfDates = new ArrayList<DateRange>();
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
                /*
                ui.print("The following reservation is removed: ");
                ui.printDashLine();
                ui.print(reservations.get(i).toString());
                ui.printDashLine();
                */
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

    /**
     * Returns the number of reservations in a reservation lists
     */
    public int size() {
        return reservations.size();
    }
}
