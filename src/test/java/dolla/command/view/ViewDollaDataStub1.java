package dolla.command.view;

import dolla.model.BillList;
import dolla.model.DebtList;
import dolla.model.DollaData;
import dolla.model.Entry;
import dolla.model.EntryList;
import dolla.model.LimitList;
import dolla.model.Record;
import dolla.model.ShortcutList;

import java.time.LocalDate;
import java.util.ArrayList;

public class ViewDollaDataStub1 extends DollaData {

    private String mode = MODE_DOLLA;
    private EntryList entryList;
    private DebtList debtList;
    private LimitList limitList;
    private ShortcutList shortcutList;
    private BillList billList;

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

    /**
     * Creates an instance of DollaData to store and manipulate data.
     */
    private ArrayList<Record> createNewEntryArrayList() {
        ArrayList<Record> newRecordList = new ArrayList<Record>();
        newRecordList.add(createNewEntry1());
        newRecordList.add(createNewEntry2());
        return newRecordList;
    }

    public ViewDollaDataStub1() {
        this.entryList = new EntryList(createNewEntryArrayList());
        this.limitList = new LimitList(new ArrayList<Record>());
        this.debtList = new DebtList(new ArrayList<Record>());
        this.shortcutList = new ShortcutList(new ArrayList<Record>());
        this.billList = new BillList(new ArrayList<Record>());
    }
}
