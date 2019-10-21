package dolla.sort;

import dolla.task.Log;

import java.util.Comparator;

public class ListComparator {

    static Comparator<Log> DateComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }

    static Comparator<Log> DescComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };
    }

        static Comparator<Log> NameComparator() {
            return new Comparator<Log>() {
                @Override
                public int compare(Log o1, Log o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };
        }
}
