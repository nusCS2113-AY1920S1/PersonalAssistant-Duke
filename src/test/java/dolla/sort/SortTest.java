package dolla.sort;

import dolla.command.sort.SortAmount;
import dolla.model.Debt;
import dolla.model.Entry;
import dolla.model.Record;
import dolla.model.Shortcut;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTest implements SortTestOutputString {
    private ArrayList<Record> entryListTest = new ArrayList<>();
    private ArrayList<Record> debtListTest = new ArrayList<>();
    private ArrayList<Record> limitListTest = new ArrayList<>();

    private void createRecordForTest() {
        Record testRecord1 = new Entry("expense", 100, "Expense Description",
                LocalDate.parse("2001-12-03"), "");
        Record testRecord2 = new Entry("income",20,"tution",
                LocalDate.parse("2002-11-03"), "");

        Record testRecord3 = new Debt("owe", "tata", 20, "food",
                LocalDate.parse("2019-01-01"), "");
        Record testRecord4 = new Debt("borrow", "yuyu", 35, "drink",
                LocalDate.parse("2018-02-02"), "");

        Record testRecord5 = new Shortcut("income",258,"Income Description");
        Record testRecord6 = new Shortcut("expense", 333,"Expense Description");
        entryListTest.add(testRecord1);
        entryListTest.add(testRecord2);
        debtListTest.add(testRecord3);
        debtListTest.add(testRecord4);
        limitListTest.add(testRecord5);
        limitListTest.add(testRecord6);

    }

    @Test
    public void testSortAmount() {
        createRecordForTest();
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        SortAmount sortAmount = new SortAmount(entryListTest);
        assertEquals(Test,os.toString());
    }




}
