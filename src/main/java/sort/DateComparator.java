package sort;

import dolla.task.Entry;

import java.util.Comparator;

public class DateComparator {

    static Comparator<Entry> entryDateComparator() {
        return new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }
}
