package dolla.command.sort;

import dolla.model.Record;

import java.util.Comparator;

class ListComparatorUtil {
    private static final int POSITIVE = 1;
    private static final int NEGATIVE = -1;
    private static final int ZERO = 0;

    //@@author yetong1895
    protected static Comparator<Record> dateComparator() {
        return Comparator.comparing(Record::getDate);
    }

    protected static Comparator<Record> descComparator() {
        return Comparator.comparing(Record::getDescription);
    }

    protected static Comparator<Record> nameComparator() {
        return Comparator.comparing(Record::getName);
    }

    protected static Comparator<Record> amountComparator() {
        return (o1, o2) -> {
            if (o1.getAmount() < o2.getAmount()) {
                return NEGATIVE;
            } else if (o1.getAmount() > o2.getAmount()) {
                return POSITIVE;
            }
            return ZERO;
        };
    }
}
