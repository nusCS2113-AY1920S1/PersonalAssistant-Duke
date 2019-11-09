package owlmoney.model.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import owlmoney.model.card.exception.CardException;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

class CardTest {
    private static final String NEWLINE = System.lineSeparator();
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final String DIVIDER =
            "----------------------------------------------------------------------------------------"
            + "-----------------------------------------" + NEWLINE;
    private static final String HEADER_ADDED_EXPENDITURE = "Added expenditure with the following details:"
            + NEWLINE  + "Item No.             Description                                             "
            + "Amount          Date                 Category             " + NEWLINE;
    private static final String HEADER_LIST_PAID_EXPENDITURE = "Paid Expenditures:" + NEWLINE
            + "Transaction No.      "
            + "Description                                             "
            + "Amount          Date                 Category             " + NEWLINE;
    private static final String HEADER_LIST_UNPAID_EXPENDITURE = "Unpaid Expenditures:" + NEWLINE
            + "Transaction No.      "
            + "Description                                             "
            + "Amount          Date                 Category             " + NEWLINE;
    private static final String NO_PAID_EXPENDITURE = "There are no paid expenditures in this card.";
    private static final String EXPECTED_BUT_NO_PAID_EXPENDITURE = "Paid Expenditures:" + NEWLINE
            + "There are no paid expenditures in this card.";
    private static final String EXPECTED_BUT_NO_UNPAID_EXPENDITURE = "Unpaid Expenditures:" + NEWLINE
            + "There are no unpaid expenditures in this card.";
    private static final String HEADER_EDIT_EXPENDITURE = "Edited details of the specified expenditure:"
            + NEWLINE + "Item No.             Description                                             "
            + "Amount          Date                 Category             " + NEWLINE;
    private static final String HEADER_DELETE_EXPENDITURE = "Details of deleted Expenditure:" + NEWLINE
            + "Item No.             Description                                             "
            + "Amount          Date                 Category             " + NEWLINE;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    void addInExpenditure_addValidExpenditures_printSuccessMessage() throws ParseException {
        System.setOut(new PrintStream(outContent));
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        String expectedExpenditure1Output = HEADER_ADDED_EXPENDITURE + DIVIDER
                + "1                    Chicken Rice                                            "
                + "[-] $100.00     05 June 2019         Food                 " + NEWLINE
                + DIVIDER;
        String expectedExpenditure2Output = HEADER_ADDED_EXPENDITURE + DIVIDER
                + "1                    Curry Rice                                              "
                + "[-] $200.00     06 June 2019         Food                 " + NEWLINE
                + DIVIDER;
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            assertEquals(expectedExpenditure1Output, outContent.toString());
            outContent.reset();
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            assertEquals(expectedExpenditure2Output, outContent.toString());
            outContent.reset();
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
    }

    @Test
    void addInExpenditure_addToExactLimit_printSuccessMessage() throws ParseException {
        System.setOut(new PrintStream(outContent));
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 250,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 250,
                dateFormat.parse("06/06/2019"), "Food");
        String expectedExpenditure1Output = HEADER_ADDED_EXPENDITURE + DIVIDER
                + "1                    Chicken Rice                                            "
                + "[-] $250.00     05 June 2019         Food                 " + NEWLINE
                + DIVIDER;
        String expectedExpenditure2Output = HEADER_ADDED_EXPENDITURE + DIVIDER
                + "1                    Curry Rice                                              "
                + "[-] $250.00     06 June 2019         Food                 " + NEWLINE
                + DIVIDER;
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            assertEquals(expectedExpenditure1Output, outContent.toString());
            outContent.reset();
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            assertEquals(expectedExpenditure2Output, outContent.toString());
            outContent.reset();
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
    }

    @Test
    void addInExpenditure_exceedMonthlyLimit_throwsException() throws ParseException {
        System.setOut(new PrintStream(outContent));
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 300,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 300,
                dateFormat.parse("06/06/2019"), "Food");
        String expectedExpenditure1Output = HEADER_ADDED_EXPENDITURE + DIVIDER
                + "1                    Chicken Rice                                            "
                + "[-] $300.00     05 June 2019         Food                 " + NEWLINE
                + DIVIDER;
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            assertEquals(expectedExpenditure1Output, outContent.toString());
            outContent.reset();
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        CardException exception = assertThrows(CardException.class, () ->
                        testCard.addInExpenditure(newExpenditure2, testUi, "card"),
               "Expected addInExpenditure to throw CardException because "
                        + "exceeded remaining monthly limit, but it did not throw");
        assertEquals("Expenditure to be added cannot exceed remaining limit of $200.0",
                exception.toString());
    }

    @Test
    void listAllExpenditure_listMoreTransactionsThanExpected_printAllExpenditures()
            throws ParseException, TransactionException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            System.setOut(new PrintStream(outContent));
            testCard.listAllExpenditure(testUi, 5);
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        String expectedOutput = "Paid Expenditures:" + NEWLINE + NO_PAID_EXPENDITURE + NEWLINE + NEWLINE
                + HEADER_LIST_UNPAID_EXPENDITURE + DIVIDER
                + "2                    Curry Rice                                              "
                + "[-] $200.00     06 June 2019         Food                 " + NEWLINE
                + "1                    Chicken Rice                                            "
                + "[-] $100.00     05 June 2019         Food                 " + NEWLINE
                + DIVIDER;

        assertEquals(expectedOutput, outContent.toString());
        outContent.reset();
    }

    @Test
    void listAllExpenditure_listNegativeNumberOfTransactions_printOneLatestExpenditure()
            throws ParseException, TransactionException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            System.setOut(new PrintStream(outContent));
            testCard.listAllExpenditure(testUi, -10);
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        String expectedOutput = EXPECTED_BUT_NO_PAID_EXPENDITURE + NEWLINE + NEWLINE
                + HEADER_LIST_UNPAID_EXPENDITURE + DIVIDER
                + "2                    Curry Rice                                              "
                + "[-] $200.00     06 June 2019         Food                 " + NEWLINE
                + DIVIDER;
        assertEquals(expectedOutput, outContent.toString());
        outContent.reset();
    }

    @Test
    void listAllExpenditure_printWhenNoTransactions_throwsException() {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        try {
            System.setOut(new PrintStream(outContent));
            testCard.listAllExpenditure(testUi, -10);
        } catch (TransactionException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        String expectedOutput = EXPECTED_BUT_NO_PAID_EXPENDITURE + NEWLINE + NEWLINE
                + EXPECTED_BUT_NO_UNPAID_EXPENDITURE + NEWLINE;
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void editExpenditureDetails_correctParameters_printSuccessMessage() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            System.setOut(new PrintStream(outContent));
            testCard.editExpenditureDetails(2, "Fried Rice", "300",
                    "08/06/2019", "Grab Food", testUi);
        } catch (CardException | TransactionException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        String expectedOutput = HEADER_EDIT_EXPENDITURE + DIVIDER
                + "1                    Fried Rice                                              "
                + "[-] $300.00     08 June 2019         Grab Food            " + NEWLINE
                + DIVIDER;
        assertEquals(expectedOutput, outContent.toString());
        outContent.reset();
    }

    @Test
    void editExpenditureDetails_editToExactLimit_printSuccessMessage() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            System.setOut(new PrintStream(outContent));
            testCard.editExpenditureDetails(1, "", "300",
                    "08/06/2019", "", testUi);
        } catch (CardException | TransactionException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        String expectedOutput = HEADER_EDIT_EXPENDITURE + DIVIDER
                + "1                    Chicken Rice                                            "
                + "[-] $300.00     08 June 2019         Food                 " + NEWLINE
                + DIVIDER;
        assertEquals(expectedOutput, outContent.toString());
        outContent.reset();
    }

    @Test
    void editExpenditureDetails_editNonExistingExpenditure_throwsException() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        TransactionException exception = assertThrows(TransactionException.class, () ->
                        testCard.editExpenditureDetails(3, "Fried Rice", "300",
                  "08/06/2019", "Grab Food", testUi),
               "Expected editExpenditureDetails to throw TransactionException"
                        + "because transaction number does not exist, but it did not throw");
        assertEquals("Index is out of transaction list range", exception.toString());
    }

    @Test
    void editExpenditureDetails_editNegativeNonExistingExpenditure_throwsException() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        TransactionException exception = assertThrows(TransactionException.class, () ->
                        testCard.editExpenditureDetails(3, "Fried Rice", "300",
                                "08/06/2019", "Grab Food", testUi),
                "Expected editExpenditureDetails to throw TransactionException"
                        + "because transaction number does not exist, but it did not throw");
        assertEquals("Index is out of transaction list range", exception.toString());
    }

    @Test
    void editExpenditureDetails_editExpenditureAboveLimit_throwsException() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        CardException exception = assertThrows(CardException.class, () ->
                        testCard.editExpenditureDetails(1, "Fried Rice", "400",
                                "08/06/2019", "Grab Food", testUi),
                "Expected editExpenditureDetails to throw CardException"
                        + "because edited expenditure exceeds monthly limit, but it did not throw");
        assertEquals("Edited expenditure cannot exceed $300.0", exception.toString());
    }

    @Test
    void deleteExpenditure_deleteExisting_PrintSuccessMessage() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            System.setOut(new PrintStream(outContent));
            testCard.deleteExpenditure(1, testUi);
        } catch (CardException | TransactionException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        String expectedOutput = HEADER_DELETE_EXPENDITURE + DIVIDER
                + "1                    Chicken Rice                                            "
                + "[-] $100.00     05 June 2019         Food                 " + NEWLINE
                + DIVIDER;
        assertEquals(expectedOutput, outContent.toString());
        outContent.reset();
    }

    @Test
    void deleteExpenditure_deleteNonExisting_throwsException() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        TransactionException exception = assertThrows(TransactionException.class, () ->
                        testCard.deleteExpenditure(10, testUi),
                "Expected editExpenditureDetails to throw TransactionException"
                        + "because transaction number does not exist, but it did not throw");
        assertEquals("Index is out of transaction list range", exception.toString());
    }

    @Test
    void deleteExpenditure_deleteNonExistingNegativeExp_throwsException() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        TransactionException exception = assertThrows(TransactionException.class, () ->
                        testCard.deleteExpenditure(-10, testUi),
                "Expected editExpenditureDetails to throw TransactionException"
                        + "because transaction number does not exist, but it did not throw");
        assertEquals("Index is out of transaction list range", exception.toString());
    }

    @Test
    void isEmpty_checkWhenZeroExpenditures_returnsTrue() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        assertEquals(true, testCard.isEmpty());
    }

    @Test
    void isEmpty_checkWhenExpendituresExist_returnsFalse() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            assertEquals(false, testCard.isEmpty());
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
    }

    @Test
    void getUnpaidBillAmount_getCorrectAmount_returnsCorrectAmount() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/07/2019"), "Food");
        Expenditure newExpenditure3 = new Expenditure("Fried Rice", 300,
                dateFormat.parse("10/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            testCard.addInExpenditure(newExpenditure3, testUi, "card");
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        assertEquals(400, testCard.getUnpaidBillAmount(YearMonth.parse("2019-06")));
    }

    @Test
    void getUnpaidBillAmount_dateNoMatchExpenditures_returnsCorrectAmount() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/07/2019"), "Food");
        Expenditure newExpenditure3 = new Expenditure("Fried Rice", 300,
                dateFormat.parse("10/06/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            testCard.addInExpenditure(newExpenditure3, testUi, "card");
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        assertEquals(0, testCard.getUnpaidBillAmount(YearMonth.parse("2019-10")));
    }

    @Test
    void getPaidBillAmount_getCorrectAmount_returnsCorrectAmount() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/07/2019"), "Food");
        Expenditure newExpenditure3 = new Expenditure("Fried Rice", 300,
                dateFormat.parse("10/06/2019"), "Food");
        try {
            testCard.addInPaidExpenditure(newExpenditure1, testUi, "card");
            testCard.addInPaidExpenditure(newExpenditure2, testUi, "card");
            testCard.addInPaidExpenditure(newExpenditure3, testUi, "card");
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        assertEquals(400, testCard.getPaidBillAmount(YearMonth.parse("2019-06")));
    }

    @Test
    void getPaidBillAmount_dateNoMatchExpenditures_returnsCorrectAmount() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/07/2019"), "Food");
        Expenditure newExpenditure3 = new Expenditure("Fried Rice", 300,
                dateFormat.parse("10/06/2019"), "Food");
        try {
            testCard.addInPaidExpenditure(newExpenditure1, testUi, "card");
            testCard.addInPaidExpenditure(newExpenditure2, testUi, "card");
            testCard.addInPaidExpenditure(newExpenditure3, testUi, "card");
        } catch (CardException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        assertEquals(0, testCard.getPaidBillAmount(YearMonth.parse("2019-10")));
    }

    @Test
    void transferExpUnpaidToPaid_correctParameters_successfulTransfer() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure1 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        Expenditure newExpenditure3 = new Expenditure("Fried Rice", 300,
                dateFormat.parse("10/07/2019"), "Food");
        String expectedOutput = HEADER_LIST_PAID_EXPENDITURE + DIVIDER
                + "2                    Curry Rice                                              "
                + "[-] $200.00     06 June 2019         Food                 " + NEWLINE
                + "1                    Chicken Rice                                            "
                + "[-] $100.00     05 June 2019         Food                 " + NEWLINE
                + DIVIDER + NEWLINE
                + HEADER_LIST_UNPAID_EXPENDITURE + DIVIDER
                + "1                    Fried Rice                                              "
                + "[-] $300.00     10 July 2019         Food                 " + NEWLINE
                + DIVIDER;

        try {
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            testCard.addInExpenditure(newExpenditure3, testUi, "card");
            System.setOut(new PrintStream(outContent));
            testCard.transferExpUnpaidToPaid(YearMonth.parse("2019-06"), "bank");
            testCard.listAllExpenditure(testUi, 10);
        } catch (CardException | TransactionException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        assertEquals(expectedOutput, outContent.toString());
        outContent.reset();
    }

    @Test
    void transferExpUnpaidToPaid_dateNoMatchExpenditure_NoTransferHappen() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure0 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure1 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Fried Rice", 300,
                dateFormat.parse("10/07/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure0, testUi, "card");
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            testCard.transferExpUnpaidToPaid(YearMonth.parse("2019-10"), "bank");
        } catch (CardException | TransactionException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        assertEquals(0, testCard.getPaidSize());
        assertEquals(3, testCard.getUnpaidSize());
        assertEquals(newExpenditure0, testCard.getUnpaid(0));
        assertEquals(newExpenditure1, testCard.getUnpaid(1));
        assertEquals(newExpenditure2, testCard.getUnpaid(2));
    }

    @Test
    void transferExpUnpaidToPaid_noExpendituresInUnpaid_NoTransferHappen() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure0 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure1 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Fried Rice", 300,
                dateFormat.parse("10/07/2019"), "Food");
        try {
            testCard.addInPaidExpenditure(newExpenditure0, testUi, "card");
            testCard.addInPaidExpenditure(newExpenditure1, testUi, "card");
            testCard.addInPaidExpenditure(newExpenditure2, testUi, "card");
            testCard.transferExpPaidToUnpaid(YearMonth.parse("2019-10"), "bank");
        } catch (CardException | TransactionException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        assertEquals(3, testCard.getPaidSize());
        assertEquals(0, testCard.getUnpaidSize());
        assertEquals(newExpenditure0, testCard.getPaid(0));
        assertEquals(newExpenditure1, testCard.getPaid(1));
        assertEquals(newExpenditure2, testCard.getPaid(2));
    }

    @Test
    void transferExpPaidToUnpaid_correctParameters_successfulTransfer() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure0 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure1 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Fried Rice", 300,
                dateFormat.parse("10/07/2019"), "Food");
        try {
            testCard.addInPaidExpenditure(newExpenditure0, testUi, "card");
            testCard.addInPaidExpenditure(newExpenditure1, testUi, "card");
            testCard.addInPaidExpenditure(newExpenditure2, testUi, "card");
            testCard.transferExpPaidToUnpaid(YearMonth.parse("2019-06"), "bank");
        } catch (CardException | TransactionException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        assertEquals(1, testCard.getPaidSize());
        assertEquals(2, testCard.getUnpaidSize());
        assertEquals(newExpenditure2, testCard.getPaid(0));
        assertEquals(newExpenditure0, testCard.getUnpaid(0));
        assertEquals(newExpenditure1, testCard.getUnpaid(1));
    }

    @Test
    void transferExpPaidToUnpaid_dateNoMatchExpenditure_NoTransferHappen() throws ParseException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure0 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure1 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Fried Rice", 300,
                dateFormat.parse("10/07/2019"), "Food");
        try {
            testCard.addInPaidExpenditure(newExpenditure0, testUi, "card");
            testCard.addInPaidExpenditure(newExpenditure1, testUi, "card");
            testCard.addInPaidExpenditure(newExpenditure2, testUi, "card");
            testCard.transferExpPaidToUnpaid(YearMonth.parse("2019-10"), "bank");
        } catch (CardException | TransactionException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        assertEquals(3, testCard.getPaidSize());
        assertEquals(0, testCard.getUnpaidSize());
        assertEquals(newExpenditure0, testCard.getPaid(0));
        assertEquals(newExpenditure1, testCard.getPaid(1));
        assertEquals(newExpenditure2, testCard.getPaid(2));
    }

    @Test
    void transferExpPaidToUnpaid_noExpendituresInPaid_NoTransferHappen() throws ParseException, TransactionException {
        Card testCard = new Card("Test Card", 500, 0.05);
        Ui testUi = new Ui();
        Expenditure newExpenditure0 = new Expenditure("Chicken Rice", 100,
                dateFormat.parse("05/06/2019"), "Food");
        Expenditure newExpenditure1 = new Expenditure("Curry Rice", 200,
                dateFormat.parse("06/06/2019"), "Food");
        Expenditure newExpenditure2 = new Expenditure("Fried Rice", 300,
                dateFormat.parse("10/07/2019"), "Food");
        try {
            testCard.addInExpenditure(newExpenditure0, testUi, "card");
            testCard.addInExpenditure(newExpenditure1, testUi, "card");
            testCard.addInExpenditure(newExpenditure2, testUi, "card");
            testCard.transferExpPaidToUnpaid(YearMonth.parse("2019-10"), "bank");
        } catch (CardException | TransactionException error) {
            System.out.println("Expected no exceptions, but exception thrown: " + error.getMessage());
        }
        assertEquals(0, testCard.getPaidSize());
        assertEquals(3, testCard.getUnpaidSize());
        assertEquals(newExpenditure0, testCard.getUnpaid(0));
        assertEquals(newExpenditure1, testCard.getUnpaid(1));
        assertEquals(newExpenditure2, testCard.getUnpaid(2));
    }
}

