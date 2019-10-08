package optix.commons.model;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 * TreeMap to sort all usage of the Opera Theatre according to calendar.
 */
public class ShowMap extends TreeMap<LocalDate, Theatre> {
    /**
     * Remove a show from the show map.
     *
     * @param key the show to be removed.
     * @return the show that is removed.
     */
    public Theatre removeShow(Object key) {
        Theatre show = this.get(key);
        this.remove(key);

        return show;
    }
}
