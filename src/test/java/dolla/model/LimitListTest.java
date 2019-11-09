package dolla.model;

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

    private ArrayList<Record> createEmptyList() {
        ArrayList<Record> limitList = new ArrayList<Record>();
        return limitList;
    }

    private ArrayList<Record> createPopulatedList() {
        ArrayList<Record> limitList = new ArrayList<Record>();
        limitList.add(newBudget);
        limitList.add(newSaving);
        return limitList;
    }

    @Test
    public void addTest1() {
        ArrayList<Record> newLimitList = createEmptyList();
        newLimitList.add(newBudget);
        newLimitList.add(newSaving);
        assertEquals(2, newLimitList.size());
    }

    @Test
    public void addTest2() {
        ArrayList<Record> newLimitList = createPopulatedList();
        newLimitList.add(newBudget);
        newLimitList.add(newSaving);
        assertEquals(4, newLimitList.size());
    }

    @Test
    public void removeFromListTest() {
        ArrayList<Record> newLimitList = createPopulatedList();
        newLimitList.remove(0);
        assertEquals(1, newLimitList.size());
    }
}
