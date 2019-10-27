package dolla.sort;

import dolla.task.Log;

import java.util.Comparator;

class ListComparatorUtil {
    private static final int POSITIVE = 1;
    private static final int NEGATIVE = -1;
    private static final int ZERO = 0;


    protected static Comparator<Log> dateComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }

    protected static Comparator<Log> descComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };
    }

    protected static Comparator<Log> nameComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
    }

    protected static Comparator<Log> amountComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                if (o1.getAmount() < o2.getAmount()) {
                    return NEGATIVE;
                } else if (o1.getAmount() > o2.getAmount()) {
                    return POSITIVE;
                }
                return ZERO;
            }
        };
    }
}
