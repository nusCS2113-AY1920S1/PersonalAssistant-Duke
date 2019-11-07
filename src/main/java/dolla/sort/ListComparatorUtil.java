package dolla.sort;

import dolla.model.Record;

import java.util.Comparator;

class ListComparatorUtil {
    private static final int POSITIVE = 1;
    private static final int NEGATIVE = -1;
    private static final int ZERO = 0;

    //@@author yetong1895
    protected static Comparator<Record> dateComparator() {
        return new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }

    protected static Comparator<Record> descComparator() {
        return new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };
    }

    protected static Comparator<Record> nameComparator() {
        return new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
    }

    protected static Comparator<Record> amountComparator() {
        return new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
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
