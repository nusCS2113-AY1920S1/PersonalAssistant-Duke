/*package wallet.model.record;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanListTest {
    LoanList loanList = new LoanList();

    @Test
    public void addLoan_notSettled_borrow_success() {
        Loan l = new Loan("lunch", LocalDate.now(), 100, false, false);
        loanList.addLoan(l);
        for (Loan loan : loanList.getLoanList()) {
            assertEquals("lunch", loan.getDescription());
            assertEquals(100.0, loan.getAmount());
            assertEquals(LocalDate.now(), loan.getDate());
            assertEquals(false, loan.isLend());
            assertEquals(false, loan.isSettled());
        }
    }

    @Test
    public void addLoan_Settled_borrow_success() {
        Loan l = new Loan("lunch", LocalDate.now(), 100, false, true);
        loanList.addLoan(l);
        for (Loan loan : loanList.getLoanList()) {
            assertEquals("lunch", loan.getDescription());
            assertEquals(100.0, loan.getAmount());
            assertEquals(LocalDate.now(), loan.getDate());
            assertEquals(false, loan.isLend());
            assertEquals(true, loan.isSettled());
        }
    }

    @Test
    public void addLoan_notSettled_lend_success() {
        Loan l = new Loan("lunch", LocalDate.now(), 100, true, false);
        loanList.addLoan(l);
        for (Loan loan : loanList.getLoanList()) {
            assertEquals("lunch", loan.getDescription());
            assertEquals(100.0, loan.getAmount());
            assertEquals(LocalDate.now(), loan.getDate());
            assertEquals(true, loan.isLend());
            assertEquals(false, loan.isSettled());
        }
    }

    @Test
    public void addLoan_Settled_lend_success() {
        Loan l = new Loan("lunch", LocalDate.now(), 100, true, true);
        loanList.addLoan(l);
        for (Loan loan : loanList.getLoanList()) {
            assertEquals("lunch", loan.getDescription());
            assertEquals(100.0, loan.getAmount());
            assertEquals(LocalDate.now(), loan.getDate());
            assertEquals(true, loan.isLend());
            assertEquals(true, loan.isSettled());
        }
    }
}
*/
