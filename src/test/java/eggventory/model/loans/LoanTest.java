package eggventory.model.loans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

//@@author cyanoei
public class LoanTest {

    Loan loan = new Loan("A1", "R5", 10);

    private ArrayList<String> data = new ArrayList<>();

    @Test
    void testLoanEquals_IsEqual_ReturnsTrue() {
        Assertions.assertTrue(loan.loanEquals("A1", "R5"));
    }

    @Test
    void testLoanEquals_NotEqual_ReturnsFalse() {
        Assertions.assertTrue(loan.loanEquals("A1", "R5"));
        Assertions.assertFalse(loan.loanEquals("not", "R5"));

        Assertions.assertTrue(loan.loanEquals("A1", "R5"));
        Assertions.assertFalse(loan.loanEquals("A1", "diff"));

        Assertions.assertTrue(loan.loanEquals("A1", "R5"));
        Assertions.assertFalse(loan.loanEquals("a1", "r5"));
    }

    @Test
    void testGetDataAsArray_Input() {
        data.add("A1");
    }

}
