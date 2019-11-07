package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.contact.ContactList;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.record.RecurrenceRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditCommandTest {
    //@@author kyang96
    private static Wallet testWallet = new Wallet();

    /**
     * setUp() method to make dummy objects for testing edit logic.
     */
    @BeforeAll
    public static void setUp() {
        testWallet.getExpenseList().addExpense(new Expense("Lunch", LocalDate.now(), 3,
                Category.FOOD, false, RecurrenceRate.NO));
        testWallet.getExpenseList().addExpense(new Expense("Dinner", LocalDate.now(), 5,
                Category.FOOD, false, RecurrenceRate.NO));
        Contact person1 = new Contact("Mary", "Friend", "1234 5678");
        Contact person2 = new Contact("Jane", "Girlfriend", "8765 4321");
        testWallet.getContactList().addContact(person1);
        testWallet.getContactList().addContact(person2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate = LocalDate.parse("24/10/2019", formatter);
        Loan loan1 = new Loan("lunch", createdDate, 10.0, false, false, person1);
        Loan loan2 = new Loan("dinner", createdDate, 10.0, true, false, person2);
        testWallet.getLoanList().addLoan(loan1);
        testWallet.getLoanList().addLoan(loan2);
    }

    @Test
    public void execute_expense_validId_success() {
        Expense expense = testWallet.getExpenseList().findExpenseWithId(1);
        expense.setDescription("Supper");
        expense.setAmount(8);
        EditCommand command = new EditCommand(expense);
        command.execute(testWallet);
        Expense e = testWallet.getExpenseList().findExpenseWithId(1);
        assertAll("Expense should be updated with new values",
            () -> assertEquals(1, e.getId()),
            () -> assertEquals("Supper", e.getDescription()),
            () -> assertEquals(LocalDate.now(), e.getDate()),
            () -> assertEquals(8.0, e.getAmount()),
            () -> assertEquals(Category.FOOD, e.getCategory()),
            () -> assertEquals(false, e.isRecurring()),
            () -> assertEquals(RecurrenceRate.NO, e.getRecFrequency())
        );
    }
    //@@author

    //@@author A0171206R
    @Test
    public void execute_loan_validId_success() {
        Loan loan = testWallet.getLoanList().findLoanWithId(1);
        loan.setDescription("breakfast");
        loan.setAmount(8);
        EditCommand command = new EditCommand(loan);
        command.execute(testWallet);
        Loan l = testWallet.getLoanList().findLoanWithId(1);
        ContactList contactList = testWallet.getContactList();
        assertAll("loan should be updated with new values",
            () -> assertEquals(1, l.getId()),
            () -> assertEquals("breakfast", l.getDescription()),
            () -> assertEquals(LocalDate.parse("2019-10-24"), l.getDate()),
            () -> assertEquals(8.0, l.getAmount()),
            () -> assertEquals(false, l.getIsLend()),
            () -> assertEquals(false, l.getIsSettled()),
            () -> assertEquals(contactList.getContactList().get(contactList.findIndexWithId(1)), l.getPerson())
        );
    }
    //@@author
}
