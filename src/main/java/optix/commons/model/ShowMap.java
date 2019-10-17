package optix.commons.model;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 * TreeMap to sort all usage of the Opera Theatre according to calendar.
 */
public class ShowMap extends TreeMap<LocalDate, Theatre> {


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
