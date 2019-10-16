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

    /*static Comparator<Limit> limitDescComparator() {
        return new Comparator<Limit>() {
            @Override
            public int compare(Limit o1, Limit o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };
    }

    static Comparator<Debt> debtDescComparator() {
        return new Comparator<Debt>() {
            @Override
            public int compare(Debt o1, Debt o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };
    }*/
}
