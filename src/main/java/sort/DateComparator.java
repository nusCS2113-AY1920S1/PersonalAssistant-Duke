package sort;

import dolla.task.Entry;
import dolla.task.Log;

import java.util.Comparator;

public class DateComparator {

    static Comparator<Log> entryDateComparator() {
        return new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }

//    static Comparator<Entry> limitDateComparator() {
//        return new Comparator<Limit>() {
//            @Override
//            public int compare(Limit o1, Limit o2) {
//                return o1.getDate().compareTo(o2.getDate());
//            }
//        };
//    }

//    static Comparator<Debt> debtDateComparator() {
//        return new Comparator<Debt>() {
//            @Override
//            public int compare(debt o1, debt o2) {
//                return o1.getDate().compareTo(o2.getDate());
//            }
//        };
//    }
}
