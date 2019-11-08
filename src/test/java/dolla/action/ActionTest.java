package dolla.action;

import dolla.command.action.state.DebtState;
import dolla.command.action.state.RedoStateList;
import dolla.command.action.state.State;
import dolla.command.action.state.UndoStateList;
import dolla.model.Debt;
import dolla.model.Record;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionTest implements ActionTestStringList {
    private ArrayList<Record> testList1 = new ArrayList<>();
    private ArrayList<Record> testList2 = new ArrayList<>();

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
    public void testState() {
        State debtState1 = createDebtState1();
        State debtState2 = createDebtState2();
        ArrayList<Record> debtTestList1 = new ArrayList<>(debtState1.getDebtState());
        ArrayList<Record> debtTestList2 = new ArrayList<>(debtState2.getDebtState());

        assertEquals(4,debtTestList1.size());
        assertEquals(5, debtTestList2.size());

        Record debtTest1 = debtTestList1.get(0);
        assertEquals(DEBT_TEST1,debtTest1.getRecordDetail());
        Record debtTest2 = debtTestList1.get(1);
        assertEquals(DEBT_TEST2,debtTest2.getRecordDetail());
        Record debtTest3 = debtTestList1.get(2);
        assertEquals(DEBT_TEST3,debtTest3.getRecordDetail());
        Record debtTest4 = debtTestList1.get(3);
        assertEquals(DEBT_TEST4,debtTest4.getRecordDetail());

        Record debtTest5 = debtTestList2.get(0);
        assertEquals(DEBT_TEST5, debtTest5.getRecordDetail());
        Record debtTest6 = debtTestList2.get(1);
        assertEquals(DEBT_TEST6, debtTest6.getRecordDetail());
        Record debtTest7 = debtTestList2.get(2);
        assertEquals(DEBT_TEST7, debtTest7.getRecordDetail());
        Record debtTest8 = debtTestList2.get(3);
        assertEquals(DEBT_TEST8, debtTest8.getRecordDetail());
        Record debtTest9 = debtTestList2.get(4);
        assertEquals(DEBT_TEST9, debtTest9.getRecordDetail());
    }

    //Unit testing for RedoStateList and UndoStateList.
    @Test
    public void testStateList() {
        //check for UndoStateList
        UndoStateList.addState(createDebtState1(), MODE_DEBT);
        UndoStateList.addState(createDebtState2(), MODE_DEBT);
        assertEquals(2, UndoStateList.getSize(MODE_DEBT));

        State undoDebtState1 = UndoStateList.getState(MODE_DEBT);
        assertEquals(1, UndoStateList.getSize(MODE_DEBT));
        State undoDebtState2 = UndoStateList.getState(MODE_DEBT);
        assertEquals(0, UndoStateList.getSize(MODE_DEBT));

        assert undoDebtState1 != null;
        assert undoDebtState2 != null;
        ArrayList<Record> undoDebtTestList1 = new ArrayList<>(undoDebtState1.getDebtState());
        ArrayList<Record> undoDebtTestList2 = new ArrayList<>(undoDebtState2.getDebtState());
        assertEquals(5,undoDebtTestList1.size()); //check if createDebtState2 pop first
        assertEquals(4,undoDebtTestList2.size());

        Record debtTest1 = undoDebtTestList1.get(0);
        assertEquals(DEBT_TEST5, debtTest1.getRecordDetail());
        Record debtTest2 = undoDebtTestList1.get(1);
        assertEquals(DEBT_TEST6, debtTest2.getRecordDetail());
        Record debtTest3 = undoDebtTestList1.get(2);
        assertEquals(DEBT_TEST7, debtTest3.getRecordDetail());
        Record debtTest4 = undoDebtTestList1.get(3);
        assertEquals(DEBT_TEST8, debtTest4.getRecordDetail());
        Record debtTest5 = undoDebtTestList1.get(4);
        assertEquals(DEBT_TEST9, debtTest5.getRecordDetail());
        Record debtTest6 = undoDebtTestList2.get(0);
        assertEquals(DEBT_TEST1, debtTest6.getRecordDetail());
        Record debtTest7 = undoDebtTestList2.get(1);
        assertEquals(DEBT_TEST2, debtTest7.getRecordDetail());
        Record debtTest8 = undoDebtTestList2.get(2);
        assertEquals(DEBT_TEST3, debtTest8.getRecordDetail());
        Record debtTest9 = undoDebtTestList2.get(3);
        assertEquals(DEBT_TEST4, debtTest9.getRecordDetail());

        testList1.clear();
        testList2.clear();

        //check for RedoStateList
        RedoStateList.addState(createDebtState2(), MODE_DEBT);
        RedoStateList.addState(createDebtState1(), MODE_DEBT);
        assertEquals(2,RedoStateList.getSize(MODE_DEBT));

        State redoDebtState1 = RedoStateList.getState(MODE_DEBT);
        assertEquals(1,RedoStateList.getSize(MODE_DEBT));
        State redoDebtState2 = RedoStateList.getState(MODE_DEBT);
        assertEquals(0,RedoStateList.getSize(MODE_DEBT));

        assert redoDebtState1 != null;
        assert redoDebtState2 != null;
        ArrayList<Record> redoDebtTestList1 = new ArrayList<>(redoDebtState1.getDebtState());
        ArrayList<Record> redoDebtTestList2 = new ArrayList<>(redoDebtState2.getDebtState());
        assertEquals(4,redoDebtTestList1.size()); //check if createDebtState1 pop first
        assertEquals(5,redoDebtTestList2.size());

        debtTest1 = redoDebtTestList1.get(0);
        debtTest2 = redoDebtTestList1.get(1);
        debtTest3 = redoDebtTestList1.get(2);
        debtTest4 = redoDebtTestList1.get(3);

        debtTest5 = redoDebtTestList2.get(0);
        debtTest6 = redoDebtTestList2.get(1);
        debtTest7 = redoDebtTestList2.get(2);
        debtTest8 = redoDebtTestList2.get(3);
        debtTest9 = redoDebtTestList2.get(4);

        assertEquals(DEBT_TEST1,debtTest1.getRecordDetail());
        assertEquals(DEBT_TEST2,debtTest2.getRecordDetail());
        assertEquals(DEBT_TEST3,debtTest3.getRecordDetail());
        assertEquals(DEBT_TEST4,debtTest4.getRecordDetail());

        assertEquals(DEBT_TEST5, debtTest5.getRecordDetail());
        assertEquals(DEBT_TEST6, debtTest6.getRecordDetail());
        assertEquals(DEBT_TEST7, debtTest7.getRecordDetail());
        assertEquals(DEBT_TEST8, debtTest8.getRecordDetail());
        assertEquals(DEBT_TEST9, debtTest9.getRecordDetail());

        //check for redo clear
        RedoStateList.addState(createDebtState2(), MODE_DEBT);
        RedoStateList.addState(createDebtState1(), MODE_DEBT);
        assertEquals(2,RedoStateList.getSize(MODE_DEBT));
        RedoStateList.clear(MODE_DEBT);
        assertEquals(0,RedoStateList.getSize(MODE_DEBT));

        testList1.clear();
        testList2.clear();
    }










}
