package optix.util;

import optix.core.Show;

import java.time.LocalDate;
import java.util.TreeMap;

public class ShowMap extends TreeMap<LocalDate, Show> {

    public Show removeShow(Object key) {
        Show show = this.get(key);
        this.remove(key);

        return show;
    }
}
