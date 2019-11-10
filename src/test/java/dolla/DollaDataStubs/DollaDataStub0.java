package dolla.DollaDataStubs;

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

public class DollaDataStub0 extends DollaData {

    private String mode = MODE_DOLLA;
    private EntryList entryList;
    private DebtList debtList;
    private LimitList limitList;
    private ShortcutList shortcutList;
    private BillList billList;

    /**
     * Creates an empty instance of DollaData to store and manipulate data.
     */
    public DollaDataStub0() {
        this.entryList = new EntryList(new ArrayList<Record>());
        this.limitList = new LimitList(new ArrayList<Record>());
        this.debtList = new DebtList(new ArrayList<Record>());
        this.shortcutList = new ShortcutList(new ArrayList<Record>());
        this.billList = new BillList(new ArrayList<Record>());
    }
}
