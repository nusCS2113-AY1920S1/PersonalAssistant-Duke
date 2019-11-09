package dolla;

import dolla.model.Debt;
import dolla.model.Entry;
import dolla.model.Limit;
import dolla.model.Record;
import dolla.model.Shortcut;
import dolla.storage.Storage;
import dolla.storage.StorageWrite;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yetong1895
public class StorageTest {
    private ArrayList<Record> entryListTest = new ArrayList<>();
    private ArrayList<Record> debtListTest = new ArrayList<>();
    private ArrayList<Record> limitListTest = new ArrayList<>();
    private ArrayList<Record> shortcutListTest = new ArrayList<>();

    /**
     * This method will setup the storage for testing.
     */
    public void setupStorage() {
        entryListTest.add(new Entry("income",256,"storage test1", LocalDate.parse("2001-12-03")));
        entryListTest.add(new Entry("expense",123,"storage test2", LocalDate.parse("2011-12-03")));
        debtListTest.add(new Debt("owe","tata",256,"storage test4", LocalDate.parse("2002-12-03")));
        debtListTest.add(new Debt("borrow","tata",123,"storage test5", LocalDate.parse("2012-12-03")));
        limitListTest.add(new Limit("budget",256,"weekly"));
        limitListTest.add(new Limit("limit",123,"monthly"));
        limitListTest.add(new Limit("budget",521,"daily"));
        shortcutListTest.add(new Shortcut("income",256,"storage test6"));
        shortcutListTest.add(new Shortcut("expense",123,"storage test7"));

        StorageWrite.setEntries(entryListTest);
        StorageWrite.setDebts(debtListTest);
        StorageWrite.setLimits(limitListTest);
        StorageWrite.setShortcuts(shortcutListTest);
    }

    @Test
    public void storageGetListTest() {
        setupStorage();

        ArrayList<Record> entryTest = Storage.getEntriesFromSave();
        Record entry = entryTest.get(0);
        String expected = "income 256.0 storage test1 /on 03/12/2001";
        assertEquals(expected,entry.getUserInput());

        entry = entryTest.get(1);
        expected = "expense 123.0 storage test2 /on 03/12/2011";
        assertEquals(expected,entry.getUserInput());

        ArrayList<Record> debtList = Storage.getDebtsFromSave();
        Record debt = debtList.get(0);
        expected = "owe tata 256.0 storage test4 /due 03/12/2002";
        assertEquals(expected,debt.getUserInput());

        debt = debtList.get(1);
        expected = "borrow tata 123.0 storage test5 /due 03/12/2012";
        assertEquals(expected,debt.getUserInput());

        ArrayList<Record> limitList = Storage.getLimitsFromSave();
        Record limit = limitList.get(0);
        expected = "budget 256.0 weekly";
        assertEquals(expected,limit.getUserInput());

        limit = limitList.get(1);
        expected = "limit 123.0 monthly";
        assertEquals(expected,limit.getUserInput());

        limit = limitList.get(2);
        expected = "budget 521.0 daily";
        assertEquals(expected,limit.getUserInput());

        ArrayList<Record> shortcutList = Storage.getShortcutsFromSave();
        Record shortcut = shortcutList.get(0);
        expected = "income 256.0 storage test6";
        assertEquals(expected,shortcut.getUserInput());

        shortcut = shortcutList.get(1);
        expected = "expense 123.0 storage test7";
        assertEquals(expected,shortcut.getUserInput());

        Storage.clearStorage();
    }
}
