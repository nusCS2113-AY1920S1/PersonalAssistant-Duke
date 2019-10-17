package sort;

import dolla.task.Entry;
import dolla.task.Log;

import java.util.Comparator;

public class DescriptionComparator {

    static Comparator<Log> entryDescComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
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
