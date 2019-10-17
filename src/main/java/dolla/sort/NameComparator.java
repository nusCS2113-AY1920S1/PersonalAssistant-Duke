package dolla.sort;

import dolla.task.Log;

import java.util.Comparator;

public class NameComparator {
    static Comparator<Log> NameComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
    }
}
