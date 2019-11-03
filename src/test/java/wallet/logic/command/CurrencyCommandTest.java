//@@author matthewng1996

package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.model.Wallet;
import wallet.model.contact.ContactList;
import wallet.model.currency.Currency;
import wallet.model.currency.CurrencyList;
import wallet.model.record.BudgetList;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.ExpenseList;
import wallet.model.record.LoanList;
import wallet.model.record.RecordList;
import wallet.storage.CurrencyStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyCommandTest {
    private static CurrencyStorage currencyStorage = new CurrencyStorage();
    private static Wallet testWallet = new Wallet(new CurrencyList(currencyStorage.loadFile()),
            new BudgetList(),
            new RecordList(),
            new ExpenseList(),
            new ContactList(),
            new LoanList());

    /**
     * Sets up the data by adding existing expenses.
     */
    @BeforeAll
    public static void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date1 = "10/01/2019";
        String date2 = "15/02/2019";
        LocalDate localDate1 = LocalDate.parse(date1, formatter);
        LocalDate localDate2 = LocalDate.parse(date2, formatter);

        testWallet.getExpenseList().addExpense(new Expense("Lunch", localDate1, 1, Category.FOOD, false, null));
        testWallet.getExpenseList().addExpense(new Expense("Dinner", localDate2, 5, Category.FOOD, false, null));
    }

    /**
     * Test for currency conversion from Singapore Dollar to Italy Euros.
     */
    @Test
    public void executeSingaporeDollarToItaly() {
        Currency currency = new Currency("Italy", 0.66d, false);
        CurrencyCommand currencyCommand = new CurrencyCommand(currency.getCountry().toLowerCase());
        currencyCommand.execute(testWallet);

        for (long i = 0; i < 1000000000000L; i++) {

        }

        assertEquals(String.format("%.2f", 0.66), String.format("%.2f",
                testWallet.getExpenseList().getExpense(0).getAmount()));
        assertEquals(String.format("%.2f", 3.30), String.format("%.2f",
                testWallet.getExpenseList().getExpense(1).getAmount()));
        assertEquals("italy", testWallet.getCurrencyList().getCurrentCurrency().getCountry());
    }
}
