package dolla.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author omupenguin
public class EntryListTest {
    private Entry createNewEntry1() {
        return new Entry("expense", 100, "Expense Description",
                LocalDate.parse("2001-12-03"));
    }

    private Entry createNewEntry2() {
        return new Entry("income", 200, "Income Description",
                LocalDate.parse("1111-11-11"));
    }

    private Entry createNewEntry3() {
        return new Entry("income", 300, "New Entry",
                LocalDate.parse("1000-01-01"));
    }

    private EntryList createNewEntryList() {
        ArrayList<Record> newRecordList = new ArrayList<Record>();
        newRecordList.add(createNewEntry1());
        newRecordList.add(createNewEntry2());
        return new EntryList(newRecordList);
    }

    @Test
    public void removeFromList() {
        EntryList newEntryList = createNewEntryList();
        newEntryList.removeFromList(1);
        assertEquals(1, newEntryList.size());
    }
}
