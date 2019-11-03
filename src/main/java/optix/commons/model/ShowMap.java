package optix.commons.model;

import optix.util.OptixDateFormatter;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * TreeMap to sort all usage of the Opera Theatre according to calendar.
 */
public class ShowMap extends TreeMap<LocalDate, Theatre> {

    private OptixDateFormatter formatter = new OptixDateFormatter();

    /**
     * Get show name.
     * @param key the show being queried.
     * @return the name of the show.
     */
    public String getShowName(Object key) {
        return this.get(key).getShowName();
    }

    //// Commands that deals with shows

    /**
     * Add shows into Archive.
     * @param showDate The date of the show.
     * @param showName The name of the show.
     * @param revenue The money earned from the show.
     */
    public void addShowHistory(LocalDate showDate, String showName, double revenue) {
        Show show = new Show(showName, revenue);
        Theatre theatre = new Theatre(show);
        this.put(showDate, theatre);
    }

    public void addShow(String showName, LocalDate showDate, double seatBasePrice) {
        Theatre theatre = new Theatre(showName, seatBasePrice);
        this.put(showDate, theatre);
    }

    public void editShowName(LocalDate showDate, String showName) {
        this.get(showDate).setShowName(showName);
    }

    public void rescheduleShow(LocalDate oldDate, LocalDate newDate) {
        Theatre theatre = this.deleteShow(oldDate);
        this.put(newDate, theatre);
    }

    /**
     * Get all the shows that are scheduled and their dates.
     * @return String message of all the shows that are registered.
     */
    public String listShow() {
        StringBuilder message = new StringBuilder();

        int counter = 1;

        for (Map.Entry<LocalDate, Theatre> entry : this.entrySet()) {
            String date = formatter.toStringDate(entry.getKey());
            String showName = entry.getValue().getShowName();

            message.append(String.format("%d. %s (on: %s)\n", counter, showName, date));
            counter++;
        }
        return message.toString();
    }

    /**
     * Get the list of show dates for the show in query.
     * @param showName The name of the show.
     * @return new ShowMap with shows that have the show in query.
     */
    public ShowMap listShow(String showName) {
        ShowMap shows = new ShowMap();
        for (Map.Entry<LocalDate, Theatre> entry : this.entrySet()) {
            if (entry.getValue().hasSameName(showName)) {
                shows.put(entry.getKey(), entry.getValue());
            }
        }
        return shows;
    }

    /**
     * Get the list of show for the month in query.
     * @param startOfMonth The first day of month in query.
     * @param endOfMonth The first day of the following month for the month in query.
     * @return new ShowMap with shows that are within the month of query.
     */
    public ShowMap listShow(LocalDate startOfMonth, LocalDate endOfMonth) {
        ShowMap shows = new ShowMap();

        while (startOfMonth.compareTo(endOfMonth) != 0) {
            if (this.containsKey(startOfMonth)) {
                shows.put(startOfMonth, this.get(startOfMonth));
            }
            startOfMonth = startOfMonth.plusDays(1);
        }
        return shows;
    }

    /**
     * Remove a show from the show map.
     * @param key the show to be removed.
     * @return the show that is removed.
     */
    public Theatre deleteShow(Object key) {
        Theatre show = this.get(key);
        this.remove(key);

        return show;
    }

    //// Command that deals with seats

    public String viewSeats(LocalDate localDate) {
        return this.get(localDate).getSeatingArrangement();
    }

    public String sellSeats(LocalDate localDate, String... seats) {
        return this.get(localDate).sellSeats(seats);
    }


    public String reassignSeat(LocalDate showLocalDate, String oldSeat, String newSeat) {
        return this.get(showLocalDate).reassignSeat(oldSeat, newSeat);
    }

    public double getProfit(LocalDate localDate) {
        return this.get(localDate).getProfit();
    }
}