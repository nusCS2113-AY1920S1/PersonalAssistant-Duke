package dolla.sort;

import dolla.command.sort.SortAmount;
import dolla.command.sort.SortDate;
import dolla.command.sort.SortDescription;
import dolla.command.sort.SortName;
import dolla.model.Debt;
import dolla.model.Entry;
import dolla.model.Record;
import dolla.model.Shortcut;
import dolla.ui.SortUi;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yetong1895
public class SortingTest implements SortTestOutputString {
    private ArrayList<Record> entryListTest = new ArrayList<>();
    private ArrayList<Record> debtListTest = new ArrayList<>();
    private ArrayList<Record> shortcutListTest = new ArrayList<>();

    private void createRecordForTest() {
        Record testRecord1 = new Entry("expense", 100, "Expense Description",
                LocalDate.parse("2001-12-03"));
        entryListTest.add(testRecord1);
        Record testRecord2 = new Entry("income",20,"tution",
                LocalDate.parse("2002-11-03"));
        entryListTest.add(testRecord2);
        Record testRecord3 = new Debt("owe", "yuyu", 20, "food",
                LocalDate.parse("2019-01-01"));
        debtListTest.add(testRecord3);
        Record testRecord4 = new Debt("borrow", "tata", 35, "drink",
                LocalDate.parse("2018-02-02"));
        debtListTest.add(testRecord4);
        Record testRecord5 = new Shortcut("income",258,"Income Description");
        shortcutListTest.add(testRecord5);
        Record testRecord6 = new Shortcut("expense", 333,"Expense Description");
        shortcutListTest.add(testRecord6);

    }

    @Test
    public void testSortAmount() {
        createRecordForTest();
        new SortAmount(entryListTest);
        assertEquals(ENTRY_SORT_AMOUNT, SortUi.outputStringTest);

        new SortAmount(debtListTest);
        assertEquals(DEBT_SORT_AMOUNT, SortUi.outputStringTest);

        new SortAmount(shortcutListTest);
        assertEquals(SHORTCUT_SORT_AMOUNT,SortUi.outputStringTest);
    }

    @Test
    public void testSortDate() {
        createRecordForTest();
        new SortDate(entryListTest);
        assertEquals(ENTRY_SORT_DATE, SortUi.outputStringTest);

        new SortDate(debtListTest);
        assertEquals(DEBT_SORT_DATE, SortUi.outputStringTest);
    }

    @Test
    public void testSortDescription() {
        createRecordForTest();
        new SortDescription(entryListTest);
        assertEquals(ENTRY_SORT_DESC, SortUi.outputStringTest);

        new SortDescription(debtListTest);
        assertEquals(DEBT_SORT_DESC, SortUi.outputStringTest);

        new SortDescription(shortcutListTest);
        assertEquals(SHORTCUT_SORT_DESC,SortUi.outputStringTest);
    }

    @Test
    public void testSortName() {
        createRecordForTest();
        new SortName(debtListTest);
        assertEquals(DEBT_SORT_NAME, SortUi.outputStringTest);
    }




}
