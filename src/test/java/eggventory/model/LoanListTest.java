package eggventory.model;

import eggventory.model.loans.Loan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoanListTest {

    private String stockCode = "R5";
    private String matric = "A1";

    Loan loan = new Loan(matric, stockCode, 10);

    @Test
    void testDeleteLoan_LoanExists_ReturnTrue() {
        LoanList.addLoan("A1", "R5", 10);
        Assertions.assertTrue(LoanList.deleteLoan("A1", "R5"));
    }

    @Test
    void testDeleteLoan_LoanDoesNotExist_ReturnFalse() {
        LoanList.addLoan("A1", "R5", 10);
        Assertions.assertFalse(LoanList.deleteLoan("A2", "R5"));

        Assertions.assertFalse(LoanList.deleteLoan("A1", "R1"));

        Assertions.assertFalse(LoanList.deleteLoan("a2", "r5"));
    }

    @Test
    void getStockLoanedQuantity_StockDoesNotExist_ReturnZero() {
        Assertions.assertEquals(0, LoanList.getStockLoanedQuantity("not stockcode"));
        Assertions.assertEquals(0, LoanList.getStockLoanedQuantity(""));
    }

    @Test
    void getStockLoanedQuantity_StockExists_ReturnQuantity() {
        Assertions.assertEquals(0, LoanList.getStockLoanedQuantity("abc"));
        LoanList.addLoan("A1", "abc", 100);
        Assertions.assertEquals(100, LoanList.getStockLoanedQuantity("abc"));
    }
}
