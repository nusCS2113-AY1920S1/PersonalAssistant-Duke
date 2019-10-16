package sort;

import dolla.task.Entry;

import java.util.Comparator;

public class DescriptionComparator {

    static Comparator<Entry> entryDescComparator() {
        return new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };
    }
}
