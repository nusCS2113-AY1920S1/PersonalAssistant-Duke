package sort;

import dolla.task.Entry;
import dolla.task.Log;

import java.util.Comparator;

public class DateComparator {

    static Comparator<Log> DateComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }
}
