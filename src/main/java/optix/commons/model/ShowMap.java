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

    public void addShow(String showName, LocalDate showDate, double seatBasePrice) {
        Theatre theatre = new Theatre(showName, seatBasePrice);
        this.put(showDate, theatre);
    }

    public void editShowName(LocalDate showDate, String showName) {
        this.get(showDate).setShowName(showName);
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
     * Remove a show from the show map.
     * @param key the show to be removed.
     * @return the show that is removed.
     */
    public Theatre removeShow(Object key) {
        Theatre show = this.get(key);
        this.remove(key);

        return show;
    }
}
