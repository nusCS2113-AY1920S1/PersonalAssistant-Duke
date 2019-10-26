package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.contact.ContactList;
import wallet.model.record.Loan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoneCommandTest {
    //@@author A0171206R
    private static Wallet testWallet = new Wallet();

    /**
     * setUp() method to make dummy objects for testing done logic.
     */
    @BeforeAll
    public static void setUp() {
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
    public void execute_done_validId() {

        DoneCommand command = new DoneCommand(1);
        command.execute(testWallet);

        Loan l = testWallet.getLoanList().findLoanWithId(1);
        ContactList contactList = testWallet.getContactList();
        assertAll("loan should be settled",
            () -> assertEquals(1, l.getId()),
            () -> assertEquals("lunch", l.getDescription()),
            () -> assertEquals(LocalDate.parse("2019-10-24"), l.getDate()),
            () -> assertEquals(10.0, l.getAmount()),
            () -> assertEquals(false, l.getIsLend()),
            () -> assertEquals(true, l.getIsSettled()),
            () -> assertEquals(contactList.getContactList().get(contactList.findIndexWithId(1)), l.getPerson())
        );
    }
}
