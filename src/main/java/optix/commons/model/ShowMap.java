package optix.commons.model;

import optix.util.OptixDateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public void addShow(String showName, LocalDate showDate, double revenue, double seatBasePrice) {
        Theatre theatre = new Theatre(showName, revenue, seatBasePrice);
        this.put(showDate, theatre);
    }

    public void editShowName(LocalDate showDate, String showName) {
        this.get(showDate).setShowName(showName);
    }

    public void postponeShow(LocalDate oldDate, LocalDate newDate) {
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
     * Get all the dates that are scheduled for the show in query.
     * @param showName the name of the show.
     * @return String message of all the dates for that are scheduled the show in query.
     */
    public String listShow(String showName) {
        StringBuilder message = new StringBuilder();

        int counter = 1;

        for (Map.Entry<LocalDate, Theatre> entry : this.entrySet()) {
            if (entry.getValue().hasSameName(showName)) {
                String date = formatter.toStringDate(entry.getKey());
                message.append(String.format("%d. %s\n", counter, date));
                counter++;
            }
        }

        return message.toString();
    }

    /**
     * Get all the show name and show date that are scheduled for the month in query.
     * @param startOfMonth First day of the month in query.
     * @param endOfMonth First day of the next month.
     * @return String message of all the shows that are scheduled for the month in query.
     */
    public String listShow(LocalDate startOfMonth, LocalDate endOfMonth) {
        StringBuilder message = new StringBuilder();

        int counter = 1;

        while (startOfMonth.compareTo(endOfMonth) != 0) {
            if (this.containsKey(startOfMonth)) {
                String date = formatter.toStringDate(startOfMonth);
                String showName = this.getShowName(startOfMonth);

                message.append(String.format("%d. %s (on: %s)\n", counter, showName, date));
                counter++;
            }

            startOfMonth = startOfMonth.plusDays(1);
        }

        return message.toString();
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

    /**
     * Remove shows with the same showName.
     * @param showNames an array of show names.
     * @return String message of all the deleted entries.
     */
    public String deleteShow(String[] showNames) {
        StringBuilder message = new StringBuilder();
        ArrayList<String> deletedShows = new ArrayList<>();
        ArrayList<String> missingShows = new ArrayList<>();

        for (String show : showNames) {
            boolean isFound = false;
            ArrayList<Map.Entry<LocalDate, Theatre>> entryArrayList = new ArrayList<>();
            for (Map.Entry<LocalDate, Theatre> entry : this.entrySet()) {
                if (entry.getValue().hasSameName(show.trim())) {
                    String showDescription = entry.getValue().getShowName() + ' '
                            + formatter.toStringDate(entry.getKey());
                    entryArrayList.add(entry);
                    deletedShows.add(showDescription);
                    isFound = true;
                }
            }
            // add show to missing show list if it's not found.
            if (!isFound) {
                missingShows.add(show);
            }
            // remove entry from shows.
            for (Map.Entry<LocalDate, Theatre> entry : entryArrayList) {
                this.remove(entry.getKey(), entry.getValue());
            }
        }

        if (!deletedShows.isEmpty()) {
            message.append("Noted. These are the deleted entries:\n");
            for (String infoStrings : deletedShows) {
                message.append(infoStrings.trim()).append('\n');
            }
        }

        if (!missingShows.isEmpty()) {
            message.append("Sorry, these shows were not found:\n");
            for (String missingShow : missingShows) {
                message.append(missingShow.trim()).append('\n');
            }
        }

        return message.toString();
    }

    //// Command that deals with seats

    public String viewSeats(LocalDate localDate) {
        return this.get(localDate).getSeatingArrangement();
    }

    public String sellSeats(LocalDate localDate, String... seats) {
        return this.get(localDate).sellSeats(seats);
    }
}
