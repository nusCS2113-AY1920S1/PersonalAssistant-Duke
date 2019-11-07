package dolla;

import dolla.task.Debt;
import dolla.task.DebtList;
import dolla.task.Record;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author tatayu
public class DebtListTest {
    private Debt newDebt1() {
        return new Debt("owe", "tatayu", 30,
                "supper", LocalDate.parse("2019-11-01"));
    }

    private Debt newDebt2() {
        return new Debt("borrow", "xx", 10,
                "ticket", LocalDate.parse("2019-10-27"));
    }

    private Debt newDebt3() {
        return new Debt("borrow", "Eve", 3,
                "tea", LocalDate.parse("2019-12-02"));
    }

    private Debt newDebt4() {
        return new Debt("owe", "tata", 50,
                "lunch", LocalDate.parse("2019-11-29"));
    }

    private DebtList newDebtList() {
        ArrayList<Record> newRecordList = new ArrayList<Record>();
        newRecordList.add(newDebt1());
        newRecordList.add(newDebt2());
        newRecordList.add(newDebt3());
        newRecordList.add(newDebt4());
        return new DebtList(newRecordList);
    }

    @Test
    public void removeFromList() {
        DebtList newDebtList = newDebtList();
        newDebtList.removeFromList(2);
        assertEquals(3, newDebtList.size());
    }
}
