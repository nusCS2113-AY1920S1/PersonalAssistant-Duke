package eggventory.model;

import com.sun.source.tree.AssertTree;
import eggventory.model.loans.Loan;
import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LoanListTest {

    private final String testStockCode = "R5";
    private final String testMatricNo = "A1";
    private final int testQuantity = 10;

    Loan loan;

    @BeforeEach
    void resetLoanObject() {
        loan = new Loan(testMatricNo, testStockCode, testQuantity);
    }

    @BeforeEach
    void resetLoanList() {
        ArrayList<Loan> loans = LoanList.getLoansList();
        ArrayList<Pair<String, String>> loanPairs = new ArrayList<>();

        for (Loan loan : loans) {
            loanPairs.add(new Pair<>(loan.getMatricNo(), loan.getStockCode()));
        }

        for (Pair<String,String> loanPair : loanPairs) {
            LoanList.deleteLoan(loanPair.getKey(), loanPair.getValue());
        }

    }

    @Test
    void deleteLoan_LoanExists_ReturnTrue() {
        LoanList.addLoan(testMatricNo, testStockCode, testQuantity);
        Assertions.assertTrue(LoanList.deleteLoan(testMatricNo, testStockCode));
    }

    @Test
    void deleteLoan_LoanDoesNotExist_ReturnFalse() {
        LoanList.addLoan(testMatricNo, testStockCode, testQuantity);
        Assertions.assertFalse(LoanList.deleteLoan("A2", testStockCode));

        Assertions.assertFalse(LoanList.deleteLoan(testMatricNo, "R1"));

        Assertions.assertFalse(LoanList.deleteLoan("a2", testStockCode));
    }

    @Test
    void getStockLoanedQuantity_StockExists_ReturnQuantity() {
        Assertions.assertEquals(0, LoanList.getStockLoanedQuantity(testStockCode));
        LoanList.addLoan(testMatricNo, testStockCode, testQuantity);
        Assertions.assertEquals(testQuantity, LoanList.getStockLoanedQuantity(testStockCode));
    }

    @Test
    void getStockLoanedQuantity_StockDoesNotExist_ReturnZero() {
        Assertions.assertEquals(0, LoanList.getStockLoanedQuantity("not stockcode"));
        Assertions.assertEquals(0, LoanList.getStockLoanedQuantity(""));
    }

    @Test
    void getPersonLoanedQuantity_LoanDoesNotExist_ReturnNegativeOne() {
        Assertions.assertEquals(-1, LoanList.getPersonLoanQuantity(testMatricNo, testStockCode));
    }

    @Test
    void getPersonLoanedQuantity_LoanExists_ReturnQuantity() {
        LoanList.addLoan(testMatricNo, testStockCode, testQuantity);
        Assertions.assertEquals(testQuantity, LoanList.getPersonLoanQuantity(testMatricNo, testStockCode));
    }

    @Test
    void printLoans_LoansExist_ReturnListString() {
        final String outputExpected = "Here are all the Loans: \n" + loan.toString() + "\n";
        LoanList.addLoan(testMatricNo, testStockCode, testQuantity);

        Assertions.assertEquals(outputExpected, LoanList.printLoans());
    }

    @Test
    void printPersonLoans_PersonExists_ReturnListString() {
        final String outputExpected = "Here are all Loans made by " + testMatricNo + ": \n"
                + loan.toString() + "\n";

        LoanList.addLoan(testMatricNo, testStockCode, testQuantity);
        Assertions.assertEquals(outputExpected, LoanList.printPersonLoans(testMatricNo));
    }

    @Test
    void saveLoanListString_HasLoans_ReturnListString() {
        final String outputExpected = loan.savedLoanString() + "\n";
        LoanList.addLoan(testMatricNo, testStockCode, testQuantity);

        LoanList testList = new LoanList();
        Assertions.assertEquals(outputExpected, testList.saveLoanListString());
    }


}
