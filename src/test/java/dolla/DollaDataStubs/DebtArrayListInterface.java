package dolla.DollaDataStubs;

import dolla.model.Debt;
import dolla.model.Entry;
import dolla.model.Record;
import dolla.parser.ParserStringList;

import java.time.LocalDate;
import java.util.ArrayList;

public interface DebtArrayListInterface extends ParserStringList {

    private Debt createNewDebt1() {
        return new Debt("owe", "tatayu", 30,
                "supper", LocalDate.parse("2019-11-01"));
    }

    private Debt createNewDebt2() {
        return new Debt("borrow", "xx", 10,
                "ticket", LocalDate.parse("2019-10-27"));
    }

    private Debt createNewDebt3() {
        return new Debt("borrow", "Eve", 3,
                "tea", LocalDate.parse("2019-12-02"));
    }

    default ArrayList<Record> createNewDebtArrayList() {
        ArrayList<Record> newRecordList = new ArrayList<Record>();
        newRecordList.add(createNewDebt1());
        newRecordList.add(createNewDebt2());
        newRecordList.add(createNewDebt3());
        return newRecordList;
    }
}
