package dolla.sort;

import dolla.task.Log;

import java.util.Comparator;

public class DescriptionComparator {

    static Comparator<Log> DescComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };
    }
}
