package eggventory.model.loans;

import eggventory.commons.exceptions.BadInputException;
import eggventory.model.PersonList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

//@@author cyanoei
public class LoanTest {

    private Loan loan;

    private ArrayList<String> data;

    @BeforeEach
    void resetPersonList() throws BadInputException {
        if (PersonList.getSize() == 0) {
            return;
        }

        ArrayList<Person> persons = PersonList.getPersonList();
        ArrayList<String> matrics = new ArrayList<>();

        for (Person person : persons) {
            matrics.add(person.getMatricNo());
        }

        for (String matric : matrics) {
            PersonList.delete(matric);
        }
    }

    @BeforeEach
    void resetLoanObject() {
        loan = new Loan("A1", "R5", 10);
    }

    @BeforeEach
    void resetDataArray() {
        data = new ArrayList<>();
    }

    @Test
    void loanEquals_IsEqual_ReturnsTrue() {
        Assertions.assertTrue(loan.loanEquals("A1", "R5"));
    }

    @Test
    void loanEquals_NotEqual_ReturnsFalse() {
        Assertions.assertTrue(loan.loanEquals("A1", "R5"));
        Assertions.assertFalse(loan.loanEquals("not", "R5"));

        Assertions.assertTrue(loan.loanEquals("A1", "R5"));
        Assertions.assertFalse(loan.loanEquals("A1", "diff"));

        Assertions.assertTrue(loan.loanEquals("A1", "R5"));
        Assertions.assertFalse(loan.loanEquals("a1", "r5"));
    }

    @Test
    void settersGetters_Exists_ReturnsValue() {
        Assertions.assertEquals("A1", loan.getMatricNo());
        Assertions.assertEquals("R5", loan.getStockCode());

        Assertions.assertEquals(10, loan.getQuantity());
        loan.setQuantity(20);
        Assertions.assertEquals(20, loan.getQuantity());

    }

    @Test
    void getStockDataAsArray_StockInfoExists_ReturnsSuccess() {
        data.add("R5");
        data.add("10");

        Assertions.assertEquals(data, loan.getStockDataAsArray());
    }

    @Test
    void getDataAsArray_InputValid_ReturnsSuccess() throws BadInputException {
        data.add("A1");
        data.add("TestName");
        data.add("R5");
        data.add("10");
        PersonList.add("A1", "TestName");

        Assertions.assertEquals(data, loan.getDataAsArray());
    }

}
