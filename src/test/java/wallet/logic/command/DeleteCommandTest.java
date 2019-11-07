package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.WalletList;
import wallet.model.contact.Contact;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.record.RecurrenceRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCommandTest {
    //@@author kyang96
    private static Wallet testWallet = new Wallet();

    /**
     * setUp() method to make dummy objects for testing delete logic.
     */
    @BeforeEach
    public void setUp() {
        testWallet = new Wallet();
        WalletList dummyWalletList = new WalletList();
        testWallet.getExpenseList().addExpense(new Expense("Lunch", LocalDate.now(), 3,
                Category.FOOD, false, RecurrenceRate.NO));
        testWallet.getExpenseList().addExpense(new Expense("Dinner", LocalDate.now(), 5,
                Category.FOOD, false, RecurrenceRate.NO));
        Contact person1 = new Contact("Mary", "Friend", "1234 5678");
        Contact person2 = new Contact("Jane", "Girlfriend", "8765 4321");
        Contact person3 = new Contact("John", "Boyfriend", "9017 3121");
        testWallet.getContactList().addContact(person1);
        testWallet.getContactList().addContact(person2);
        testWallet.getContactList().addContact(person3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate = LocalDate.parse("24/10/2019", formatter);
        Loan loan1 = new Loan("lunch", createdDate, 10.0, false, false, person1);
        Loan loan2 = new Loan("dinner", createdDate, 10.0, true, false, person2);
        testWallet.getLoanList().addLoan(loan1);
        testWallet.getLoanList().addLoan(loan2);

        dummyWalletList.getWalletList().add(testWallet);
        LogicManager.setWalletList(dummyWalletList);
    }

    @Test
    public void execute_expense_validId_success() {
        DeleteCommand command = new DeleteCommand("expense", 1);
        command.execute(testWallet);
        Expense e = testWallet.getExpenseList().findExpenseWithId(1);
        assertEquals(null, e);

        assertEquals(1, testWallet.getExpenseList().getSize());
    }

    @Test
    public void execute_expense_invalidId_error() {
        DeleteCommand command = new DeleteCommand("expense", 4);
        command.execute(testWallet);
        assertEquals(2, testWallet.getExpenseList().getSize());
    }
    //@@author

    //@@author A0171206R
    @Test
    public void execute_loan_validId_success() {
        DeleteCommand command = new DeleteCommand("loan", 1);
        command.execute(testWallet);
        Loan l = testWallet.getLoanList().findLoanWithId(1);
        assertEquals(null, l);
        assertEquals(1, testWallet.getLoanList().getSize());
    }
    //@@author

    //@@author Xdecosee
    @Test
    public void execute_contact_validId_success() {
        DeleteCommand command = new DeleteCommand("contact", 3);
        command.execute(testWallet);
        assertEquals(2, testWallet.getContactList().getContactListSize());
    }

    /**
     * Testing execute function when deleting contact with invalid ID.
     * @param input Test inputs.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 4})
    public void execute_contact_invalidId_error(int input) {
        DeleteCommand command = new DeleteCommand("contact", input);
        command.execute(testWallet);
        assertEquals(3, testWallet.getContactList().getContactListSize());
    }
    //@@author
}
