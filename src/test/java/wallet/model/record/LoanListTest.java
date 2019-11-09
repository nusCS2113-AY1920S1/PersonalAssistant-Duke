package wallet.model.record;

import org.junit.jupiter.api.Test;
import wallet.model.contact.Contact;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanListTest {
    LoanList loanList = new LoanList();
    private Contact person = new Contact("mary", "friend", "92737231");

    @Test
    public void addLoan_notSettled_borrow_success() {
        Loan l = new Loan("lunch", LocalDate.now(), 100, false, false, person);
        loanList.addLoan(l);
        for (Loan loan : loanList.getLoanList()) {
            assertEquals("lunch", loan.getDescription());
            assertEquals(100.0, loan.getAmount());
            assertEquals(LocalDate.now(), loan.getDate());
            assertEquals(false, loan.getIsLend());
            assertEquals(false, loan.getIsSettled());
            assertEquals(person, loan.getPerson());
        }
    }

    @Test
    public void addLoan_Settled_borrow_success() {
        Loan l = new Loan("lunch", LocalDate.now(), 100, false, true, person);
        loanList.addLoan(l);
        for (Loan loan : loanList.getLoanList()) {
            assertEquals("lunch", loan.getDescription());
            assertEquals(100.0, loan.getAmount());
            assertEquals(LocalDate.now(), loan.getDate());
            assertEquals(false, loan.getIsLend());
            assertEquals(true, loan.getIsSettled());
            assertEquals(person, loan.getPerson());
        }
    }

    @Test
    public void addLoan_notSettled_lend_success() {
        Loan l = new Loan("lunch", LocalDate.now(), 100, true, false, person);
        loanList.addLoan(l);
        for (Loan loan : loanList.getLoanList()) {
            assertEquals("lunch", loan.getDescription());
            assertEquals(100.0, loan.getAmount());
            assertEquals(LocalDate.now(), loan.getDate());
            assertEquals(true, loan.getIsLend());
            assertEquals(false, loan.getIsSettled());
            assertEquals(person, loan.getPerson());
        }
    }

    @Test
    public void addLoan_Settled_lend_success() {
        Loan l = new Loan("lunch", LocalDate.now(), 100, true, true, person);
        loanList.addLoan(l);
        for (Loan loan : loanList.getLoanList()) {
            assertEquals("lunch", loan.getDescription());
            assertEquals(100.0, loan.getAmount());
            assertEquals(LocalDate.now(), loan.getDate());
            assertEquals(true, loan.getIsLend());
            assertEquals(true, loan.getIsSettled());
            assertEquals(person, loan.getPerson());
        }
    }
}

