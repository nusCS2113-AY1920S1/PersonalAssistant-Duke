package dolla.task;

import dolla.model.Limit;
import dolla.model.Record;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static dolla.parser.ParserStringList.LIMIT_DURATION_D;
import static dolla.parser.ParserStringList.LIMIT_DURATION_W;
import static dolla.parser.ParserStringList.LIMIT_TYPE_B;
import static dolla.parser.ParserStringList.LIMIT_TYPE_S;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Weng-Kexin
public class LimitListTest {

    private Limit newBudget = createNewLimit(LIMIT_TYPE_B, 5, LIMIT_DURATION_D);

    private Limit newSaving = createNewLimit(LIMIT_TYPE_S, 50, LIMIT_DURATION_W);

    private Limit createNewLimit(String limitType, double amount, String limitDuration) {
        return new Limit(limitType, amount, limitDuration);
    }

    private ArrayList<Record> createNewLimitList() {
        ArrayList<Record> limitList = new ArrayList<Record>();
        return limitList;
    }

    @Test
    public void addTest1() {
        ArrayList<Record> newLimitList = createNewLimitList();
        newLimitList.add(newBudget);
        newLimitList.add(newSaving);
        assertEquals(2, newLimitList.size());
    }
}
