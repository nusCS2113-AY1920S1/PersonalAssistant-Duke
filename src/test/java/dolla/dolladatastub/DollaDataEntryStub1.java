package dolla.dolladatastub;

import dolla.model.BillList;
import dolla.model.DebtList;
import dolla.model.DollaData;
import dolla.model.EntryList;
import dolla.model.LimitList;
import dolla.model.Record;
import dolla.model.ShortcutList;

import java.util.ArrayList;

public class DollaDataEntryStub1 extends DollaData implements EntryArrayListInterface {

    /**
     * Creates an instance of DollaData to store and manipulate data.
     */
    public DollaDataEntryStub1() {
        this.mode = MODE_ENTRY;
        this.entryList = new EntryList(createNewEntryArrayList());
        this.limitList = new LimitList(new ArrayList<Record>());
        this.debtList = new DebtList(new ArrayList<Record>());
        this.shortcutList = new ShortcutList(new ArrayList<Record>());
        this.billList = new BillList(new ArrayList<Record>());
    }
}
