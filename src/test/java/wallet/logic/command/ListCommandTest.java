//@@author matthewng1996

package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.Loan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest {

    private static Wallet testWallet = new Wallet();

    /**
     * Sets up the data by adding existing data.
     */
    @BeforeAll
    public static void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date1 = "10/03/2019";
        String date2 = "15/05/2019";
        String date3 = "01/11/2019";
        String date4 = "05/05/2019";
        LocalDate localDate1 = LocalDate.parse(date1, formatter);
        LocalDate localDate2 = LocalDate.parse(date2, formatter);
        LocalDate localDate3 = LocalDate.parse(date3, formatter);
        LocalDate localDate4 = LocalDate.parse(date4, formatter);

        testWallet.getExpenseList().addExpense(new Expense("Lunch", localDate1, 3, Category.FOOD, false, null));
        testWallet.getExpenseList().addExpense(new Expense("Dinner", localDate2, 5, Category.FOOD, false, null));
        testWallet.getExpenseList().addExpense(new Expense("Phone", localDate3, 10, Category.OTHERS, true, null));
        testWallet.getExpenseList().addExpense(new Expense("Card", localDate4, 3, Category.TRANSPORT, true, null));

        testWallet.getContactList().addContact(new Contact("Dave", "friend", "12345678"));
        testWallet.getContactList().addContact(new Contact("Mary", "friend", "41242131"));
        testWallet.getContactList().addContact(new Contact("Maria", "stranger", "124314343"));

        testWallet.getLoanList().addLoan(new Loan("for lunch", localDate1, 10, true,
                false, testWallet.getContactList().getContact(0)));
        testWallet.getLoanList().addLoan(new Loan("for lunch", localDate3, 10, false,
                false, testWallet.getContactList().getContact(1)));
        testWallet.getLoanList().addLoan(new Loan("for lunch", localDate4, 10, true,
                false, testWallet.getContactList().getContact(2)));
    }

    /**
     * List all expenses sorted by date.
     */
    @Test
    public void sortExpenseByDate() {
        ArrayList<Expense> expenseListSorted = new ArrayList<>();
        expenseListSorted.add(testWallet.getExpenseList().getExpense(0));
        expenseListSorted.add(testWallet.getExpenseList().getExpense(3));
        expenseListSorted.add(testWallet.getExpenseList().getExpense(1));
        expenseListSorted.add(testWallet.getExpenseList().getExpense(2));
        ListCommand listCommand = new ListCommand("expense /sortby date");
        listCommand.execute(testWallet);
        assertEquals(expenseListSorted, testWallet.getExpenseList().sortByDate());
    }

    /**
     * List all recurring expenses sorted by date.
     */
    @Test
    public void sortRecurringExpenseByDate() {
        ArrayList<Expense> recurringExpenseListSorted = new ArrayList<>();
        recurringExpenseListSorted.add(testWallet.getExpenseList().getExpense(1));
        recurringExpenseListSorted.add(testWallet.getExpenseList().getExpense(3));
        ListCommand listCommand = new ListCommand("recurring /sortby date");
        listCommand.execute(testWallet);
        assertEquals(recurringExpenseListSorted, testWallet.getExpenseList().getSortedRecurringExpenseDate());
    }

    /**
     * List all expenses sorted by category.
     */
    @Test
    public void sortExpenseByCategory() {
        ArrayList<Expense> expenseListSorted = new ArrayList<>();
        expenseListSorted.add(testWallet.getExpenseList().getExpense(0));
        expenseListSorted.add(testWallet.getExpenseList().getExpense(2));
        expenseListSorted.add(testWallet.getExpenseList().getExpense(3));
        expenseListSorted.add(testWallet.getExpenseList().getExpense(1));
        ListCommand listCommand = new ListCommand("expense /sortby category");
        listCommand.execute(testWallet);
        assertEquals(expenseListSorted, testWallet.getExpenseList().sortByCategory());
    }

    /**
     * List all recurring expenses sorted by category.
     */
    @Test
    public void sortRecurringExpenseByCategory() {
        ArrayList<Expense> recurringExpenseListSorted = new ArrayList<>();
        recurringExpenseListSorted.add(testWallet.getExpenseList().getExpense(3));
        recurringExpenseListSorted.add(testWallet.getExpenseList().getExpense(1));
        ListCommand listCommand = new ListCommand("recurring /sortby category");
        listCommand.execute(testWallet);
        assertEquals(recurringExpenseListSorted, testWallet.getExpenseList().getSortedRecurringExpenseCategory());
    }

    /**
     * List all loans sorted by date.
     */
    @Test
    public void sortLoanByDate() {
        ArrayList<Loan> loanListSorted = new ArrayList<>();
        loanListSorted.add(testWallet.getLoanList().getLoan(0));
        loanListSorted.add(testWallet.getLoanList().getLoan(2));
        loanListSorted.add(testWallet.getLoanList().getLoan(1));
        ListCommand listCommand = new ListCommand("loan /sortby date");
        listCommand.execute(testWallet);
        assertEquals(loanListSorted, testWallet.getLoanList().sortByDate());
    }

    /**
     * List all loans with lend field sorted by date.
     */
    @Test
    public void sortLoanByLend() {
        ArrayList<Loan> loanListSorted = new ArrayList<>();
        loanListSorted.add(testWallet.getLoanList().getLoan(0));
        loanListSorted.add(testWallet.getLoanList().getLoan(1));
        ListCommand listCommand = new ListCommand("loan /sortby lend");
        listCommand.execute(testWallet);
        assertEquals(loanListSorted, testWallet.getLoanList().sortByLend());
    }

    /**
     * List all loans with borrow field sorted by date.
     */
    @Test
    public void sortLoanByBorrow() {
        ArrayList<Loan> loanListSorted = new ArrayList<>();
        loanListSorted.add(testWallet.getLoanList().getLoan(2));
        ListCommand listCommand = new ListCommand("loan /sortby borrow");
        listCommand.execute(testWallet);
        assertEquals(loanListSorted, testWallet.getLoanList().sortByBorrow());
    }

    /**
     * List all contacts sorted by alphabetical order.
     */
    @Test
    public void sortContactByName() {
        ArrayList<Contact> contactListSorted = new ArrayList<>();
        contactListSorted.add(testWallet.getContactList().getContact(0));
        contactListSorted.add(testWallet.getContactList().getContact(2));
        contactListSorted.add(testWallet.getContactList().getContact(1));
        ListCommand listCommand = new ListCommand("contact /sortby name");
        listCommand.execute(testWallet);
        assertEquals(contactListSorted, testWallet.getContactList().sortByName());
    }
}
