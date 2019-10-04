package optix.util;

import optix.core.Theatre;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 * TreeMap to sort all usage of the Opera Theatre according to calendar.
 */
public class ShowMap extends TreeMap<LocalDate, Theatre> {

    public Theatre removeShow(Object key) {
        Theatre show = this.get(key);
        this.remove(key);

        return show;
    }
}
