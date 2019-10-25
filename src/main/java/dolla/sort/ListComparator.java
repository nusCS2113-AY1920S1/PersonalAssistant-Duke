package dolla.sort;

import dolla.task.Record;

import java.util.Comparator;

public class ListComparator {

    static Comparator<Record> dateComparator() {
        return new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }

    static Comparator<Record> descComparator() {
        return new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };
    }

    static Comparator<Record> nameComparator() {
        return new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
    }
}
