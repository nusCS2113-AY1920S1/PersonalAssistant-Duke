package wallet.logic.parser;

import org.junit.jupiter.api.Test;
import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.WalletList;
import wallet.model.contact.Contact;
import wallet.model.contact.ContactList;
import wallet.model.record.BudgetList;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.ExpenseList;
import wallet.model.record.Loan;
import wallet.model.record.LoanList;
import wallet.model.record.RecordList;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditCommandParserTest {
    //@@author kyang96
    @Test
    public void parseExpense_validInput_success() {
        EditCommandParser parser = new EditCommandParser();
        String input = "2 /d Supper /a 10 /c Others";
        Expense expense = parser.parseExpense(input);
        assertAll("Expense should contain edited values",
            () -> assertEquals(2, expense.getId()),
            () -> assertEquals("Supper", expense.getDescription()),
            () -> assertEquals(null, expense.getDate()),
            () -> assertEquals(10.0, expense.getAmount()),
            () -> assertEquals(Category.OTHERS, expense.getCategory()),
            () -> assertEquals(false, expense.isRecurring()),
            () -> assertEquals("", expense.getRecFrequency())
        );
    }

    @Test
    public void parseExpense_validRecurringInput_success() {
        EditCommandParser parser = new EditCommandParser();
        String input = "2 /d Supper /a 10 /c Others /r Daily";
        Expense expense = parser.parseExpense(input);
        assertAll("Expense should contain edited values",
            () -> assertEquals(2, expense.getId()),
            () -> assertEquals("Supper", expense.getDescription()),
            () -> assertEquals(null, expense.getDate()),
            () -> assertEquals(10.0, expense.getAmount()),
            () -> assertEquals(Category.OTHERS, expense.getCategory()),
            () -> assertEquals(true, expense.isRecurring()),
            () -> assertEquals("DAILY", expense.getRecFrequency())
        );
    }

    //@@author A0171206R
    @Test
    public void parseLoan_validInput_success_case_description() {
        EditCommandParser parser = new EditCommandParser();
        WalletList dummyWalletList = new WalletList();
        Wallet dummyWallet = new Wallet(new BudgetList(),
                new RecordList(),
                new ExpenseList(),
                new ContactList(),
                new LoanList());

        //dummy Contact object
        Contact person1 = new Contact("Mary", "Friend", "1234 5678");
        Contact person2 = new Contact("Jane", "Girlfriend", "8765 4321");

        ContactList contactList = dummyWallet.getContactList();
        contactList.addContact(person1);
        contactList.addContact(person2);

        //dummy Loan object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate = LocalDate.parse("24/10/2019", formatter);
        Loan loan1 = new Loan("lunch", createdDate, 10.0, false, false, person1);

        LoanList loanList = dummyWallet.getLoanList();
        loanList.addLoan(loan1);

        dummyWalletList.getWalletList().add(dummyWallet);
        LogicManager.setWalletList(dummyWalletList);

        String input = "1 /d dinner";
        Loan loan = parser.parseLoan(input);
        assertAll("Loan should contain correct input values",
            () -> assertEquals("dinner", loan.getDescription()),
            () -> assertEquals(10.0, loan.getAmount()),
            () -> assertEquals(LocalDate.parse("2019-10-24"), loan.getDate()),
            () -> assertEquals(false, loan.getIsLend()),
            () -> assertEquals(false, loan.getIsSettled()),
            () -> assertEquals(contactList.getContactList().get(contactList.findIndexWithId(1)), loan.getPerson())
        );
    }

    @Test
    public void parseLoan_validInput_success_case_date() {
        EditCommandParser parser = new EditCommandParser();
        WalletList dummyWalletList = new WalletList();
        Wallet dummyWallet = new Wallet(new BudgetList(),
                new RecordList(),
                new ExpenseList(),
                new ContactList(),
                new LoanList());

        //dummy Contact object
        Contact person1 = new Contact("Mary", "Friend", "1234 5678");
        Contact person2 = new Contact("Jane", "Girlfriend", "8765 4321");

        ContactList contactList = dummyWallet.getContactList();
        contactList.addContact(person1);
        contactList.addContact(person2);

        //dummy Loan object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate = LocalDate.parse("24/10/2019", formatter);
        Loan loan1 = new Loan("lunch", createdDate, 10.0, false, false, person1);

        LoanList loanList = dummyWallet.getLoanList();
        loanList.addLoan(loan1);

        dummyWalletList.getWalletList().add(dummyWallet);
        LogicManager.setWalletList(dummyWalletList);

        String input = "1 /t 25/10/2019";
        Loan loan = parser.parseLoan(input);
        assertAll("Loan should contain correct input values",
            () -> assertEquals("lunch", loan.getDescription()),
            () -> assertEquals(10.0, loan.getAmount()),
            () -> assertEquals(LocalDate.parse("2019-10-25"), loan.getDate()),
            () -> assertEquals(false, loan.getIsLend()),
            () -> assertEquals(false, loan.getIsSettled()),
            () -> assertEquals(contactList.getContactList().get(contactList.findIndexWithId(1)), loan.getPerson())
        );
    }

    @Test
    public void parseLoan_validInput_success_case_amount() {
        EditCommandParser parser = new EditCommandParser();
        WalletList dummyWalletList = new WalletList();
        Wallet dummyWallet = new Wallet(new BudgetList(),
                new RecordList(),
                new ExpenseList(),
                new ContactList(),
                new LoanList());

        //dummy Contact object
        Contact person1 = new Contact("Mary", "Friend", "1234 5678");
        Contact person2 = new Contact("Jane", "Girlfriend", "8765 4321");

        ContactList contactList = dummyWallet.getContactList();
        contactList.addContact(person1);
        contactList.addContact(person2);

        //dummy Loan object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate = LocalDate.parse("24/10/2019", formatter);
        Loan loan1 = new Loan("lunch", createdDate, 10.0, false, false, person1);

        LoanList loanList = dummyWallet.getLoanList();
        loanList.addLoan(loan1);

        dummyWalletList.getWalletList().add(dummyWallet);
        LogicManager.setWalletList(dummyWalletList);

        String input = "1 /a 15";
        Loan loan = parser.parseLoan(input);
        assertAll("Loan should contain correct input values",
            () -> assertEquals("lunch", loan.getDescription()),
            () -> assertEquals(15.0, loan.getAmount()),
            () -> assertEquals(LocalDate.parse("2019-10-24"), loan.getDate()),
            () -> assertEquals(false, loan.getIsLend()),
            () -> assertEquals(false, loan.getIsSettled()),
            () -> assertEquals(contactList.getContactList().get(contactList.findIndexWithId(1)), loan.getPerson())
        );
    }

    @Test
    public void parseLoan_validInput_success_case_isLend() {
        EditCommandParser parser = new EditCommandParser();
        WalletList dummyWalletList = new WalletList();
        Wallet dummyWallet = new Wallet(new BudgetList(),
                new RecordList(),
                new ExpenseList(),
                new ContactList(),
                new LoanList());

        //dummy Contact object
        Contact person1 = new Contact("Mary", "Friend", "1234 5678");
        Contact person2 = new Contact("Jane", "Girlfriend", "8765 4321");

        ContactList contactList = dummyWallet.getContactList();
        contactList.addContact(person1);
        contactList.addContact(person2);

        //dummy Loan object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate = LocalDate.parse("24/10/2019", formatter);
        Loan loan1 = new Loan("lunch", createdDate, 10.0, false, false, person1);

        LoanList loanList = dummyWallet.getLoanList();
        loanList.addLoan(loan1);

        dummyWalletList.getWalletList().add(dummyWallet);
        LogicManager.setWalletList(dummyWalletList);

        String input = "1 /l";
        Loan loan = parser.parseLoan(input);
        assertAll("Loan should contain correct input values",
            () -> assertEquals("lunch", loan.getDescription()),
            () -> assertEquals(10.0, loan.getAmount()),
            () -> assertEquals(LocalDate.parse("2019-10-24"), loan.getDate()),
            () -> assertEquals(true, loan.getIsLend()),
            () -> assertEquals(false, loan.getIsSettled()),
            () -> assertEquals(contactList.getContactList().get(contactList.findIndexWithId(1)), loan.getPerson())
        );
    }

    @Test
    public void parseLoan_validInput_success_case_contact() {
        EditCommandParser parser = new EditCommandParser();
        WalletList dummyWalletList = new WalletList();
        Wallet dummyWallet = new Wallet(new BudgetList(),
                new RecordList(),
                new ExpenseList(),
                new ContactList(),
                new LoanList());

        //dummy Contact object
        Contact person1 = new Contact("Mary", "Friend", "1234 5678");
        Contact person2 = new Contact("Jane", "Girlfriend", "8765 4321");

        ContactList contactList = dummyWallet.getContactList();
        contactList.addContact(person1);
        contactList.addContact(person2);

        //dummy Loan object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate = LocalDate.parse("24/10/2019", formatter);
        Loan loan1 = new Loan("lunch", createdDate, 10.0, false, false, person1);

        LoanList loanList = dummyWallet.getLoanList();
        loanList.addLoan(loan1);

        dummyWalletList.getWalletList().add(dummyWallet);
        LogicManager.setWalletList(dummyWalletList);

        String input = "1 /c 2";
        Loan loan = parser.parseLoan(input);
        assertAll("Loan should contain correct input values",
            () -> assertEquals("lunch", loan.getDescription()),
            () -> assertEquals(10.0, loan.getAmount()),
            () -> assertEquals(LocalDate.parse("2019-10-24"), loan.getDate()),
            () -> assertEquals(false, loan.getIsLend()),
            () -> assertEquals(false, loan.getIsSettled()),
            () -> assertEquals(contactList.getContactList().get(contactList.findIndexWithId(2)), loan.getPerson())
        );
    }
    //@@author
}
