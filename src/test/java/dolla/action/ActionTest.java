package dolla.action;

import dolla.command.action.state.DebtState;
import dolla.command.action.state.State;
import dolla.command.action.state.UndoStateList;
import dolla.model.Debt;
import dolla.model.Record;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionTest {
    private ArrayList<Record> testList1 = new ArrayList<>();
    private ArrayList<Record> testList2 = new ArrayList<>();
    private static final String OWE = "owe";
    private static final String BORROW = "borrow";

    private State createDebtState1() {
        testList1.add(new Debt(OWE, "tata", 20, "food",
                LocalDate.parse("2019-01-01"), ""));
        testList1.add(new Debt(BORROW, "yuyu", 40, "drink",
                LocalDate.parse("2019-01-02"), ""));
        testList1.add(new Debt(OWE, "mama", 30, "bill",
                LocalDate.parse("2019-01-03"), ""));
        testList1.add(new Debt(BORROW, "baba", 60, "dinner",
                LocalDate.parse("2019-01-04"), ""));
        return new DebtState(testList1);
}

    private State createDebtState2() {
        testList2.add(new Debt(BORROW, "aikpeng", 126, "bushi",
                LocalDate.parse("2011-01-01"), ""));
        testList2.add(new Debt(OWE, "yetong", 245, "sushi",
                LocalDate.parse("2012-01-02"), ""));
        testList2.add(new Debt(OWE, "kexin", 91, "disco",
                LocalDate.parse("2013-01-03"), ""));
        testList2.add(new Debt(OWE, "xiaoxue", 1151, "wine",
                LocalDate.parse("2014-01-04"), ""));
        testList2.add(new Debt(BORROW, "bahaba", 521, "rebina",
                LocalDate.parse("2015-01-04"), ""));
        return new DebtState(testList2);
    }


    @Test
    public void testDebtState() {
        State debtState1 = createDebtState1();
        State debtState2 = createDebtState2();
        ArrayList<Record> newTestList1 = new ArrayList<>(debtState1.getDebtState());
        ArrayList<Record> newTestList2 = new ArrayList<>(debtState2.getDebtState());
        Record debtTest1 = newTestList1.get(0);
        Record debtTest2 = newTestList1.get(1);
        Record debtTest3 = newTestList1.get(2);
        Record debtTest4 = newTestList1.get(3);

        Record debtTest5 = newTestList2.get(0);
        Record debtTest6 = newTestList2.get(1);
        Record debtTest7 = newTestList2.get(2);
        Record debtTest8 = newTestList2.get(3);
        Record debtTest9 = newTestList2.get(4);

        assertEquals(4,newTestList1.size());
        assertEquals("[owe] [tata] [$20.0] [food] [/due 01/01/2019] {Tag: }",debtTest1.getRecordDetail());
        assertEquals("[borrow] [yuyu] [$40.0] [drink] [/due 02/01/2019] {Tag: }",debtTest2.getRecordDetail());
        assertEquals("[owe] [mama] [$30.0] [bill] [/due 03/01/2019] {Tag: }",debtTest3.getRecordDetail());
        assertEquals("[borrow] [baba] [$60.0] [dinner] [/due 04/01/2019] {Tag: }",debtTest4.getRecordDetail());

        assertEquals(5, newTestList2.size());
        assertEquals("[borrow] [aikpeng] [$126.0] [bushi] [/due 01/01/2011] {Tag: }",
                debtTest5.getRecordDetail());
        assertEquals("[owe] [yetong] [$245.0] [sushi] [/due 02/01/2012] {Tag: }",
                debtTest6.getRecordDetail());
        assertEquals("[owe] [kexin] [$91.0] [disco] [/due 03/01/2013] {Tag: }",
                debtTest7.getRecordDetail());
        assertEquals("[owe] [xiaoxue] [$1151.0] [wine] [/due 04/01/2014] {Tag: }",
                debtTest8.getRecordDetail());
        assertEquals("[borrow] [bahaba] [$521.0] [rebina] [/due 04/01/2015] {Tag: }",
                debtTest9.getRecordDetail());
    }

    @Test
    public void testUndoStateList() {
        UndoStateList.addState(createDebtState1(), "debt");
        assertEquals(1,UndoStateList.getSize("debt"));
        State debtState = UndoStateList.getState("debt");
        assert debtState != null;
        ArrayList<Record> newTestList = new ArrayList<>(debtState.getDebtState());

        Record debtTest1 = newTestList.get(0);
        Record debtTest2 = newTestList.get(1);
        Record debtTest3 = newTestList.get(2);
        Record debtTest4 = newTestList.get(3);

        assertEquals(4,newTestList.size());
        assertEquals("[owe] [tata] [$20.0] [food] [/due 01/01/2019] {Tag: }",debtTest1.getRecordDetail());
        assertEquals("[borrow] [yuyu] [$40.0] [drink] [/due 02/01/2019] {Tag: }",debtTest2.getRecordDetail());
        assertEquals("[owe] [mama] [$30.0] [bill] [/due 03/01/2019] {Tag: }",debtTest3.getRecordDetail());
        assertEquals("[borrow] [baba] [$60.0] [dinner] [/due 04/01/2019] {Tag: }",debtTest4.getRecordDetail());
    }










}
