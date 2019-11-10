package dolla.dolladatastub;

import dolla.model.Entry;
import dolla.model.Record;
import dolla.parser.ParserStringList;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Allows for quick creation of an ArrayList of entries for initialising DollaData.
 */
public interface EntryArrayListInterface extends ParserStringList {

    private Entry createNewEntry1() {
        return new Entry("expense", 200, "Expense Description",
                LocalDate.parse("1111-11-11"));
    }

    private Entry createNewEntry2() {
        return new Entry("income", 100, "Income Description",
                LocalDate.parse("1111-11-11"));
    }

    private Entry createNewEntry3() {
        return new Entry("income", 300, "New Entry",
                LocalDate.parse("1000-01-01"));
    }

    /**
     * Returns an ArrayList of entries.
     * @return an ArrayList of entries.
     */
    default ArrayList<Record> createNewEntryArrayList() {
        ArrayList<Record> newRecordList = new ArrayList<Record>();
        newRecordList.add(createNewEntry1());
        newRecordList.add(createNewEntry2());
        newRecordList.add(createNewEntry3());
        return newRecordList;
    }
}
