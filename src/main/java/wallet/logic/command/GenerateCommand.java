package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Budget;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.record.RecurrenceRate;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GenerateCommand extends Command {

    public static final String COMMAND_WORD = "generate";
    public static final String MESSAGE_SUCCESS = "All previous data has been wiped. Inserting default data...";

    @Override
    public boolean execute(Wallet wallet) {

        deleteAllFiles(); //clear all data first
        wallet.getLoanList().getLoanList().clear();
        wallet.getContactList().getContactList().clear();
        wallet.getExpenseList().getExpenseList().clear();
        wallet.getBudgetList().getBudgetList().clear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate1 = LocalDate.parse("10/10/2019", formatter);
        LocalDate createdDate2 = LocalDate.parse("11/10/2019", formatter);
        LocalDate createdDate3 = LocalDate.parse("12/10/2019", formatter);

        //test data for contact
        Contact person1 = new Contact("Mary", "Sister", "91837362");
        Contact person2 = new Contact("Tom", "Friend", "81276251");

        wallet.getContactList().addContact(person1);
        wallet.getContactList().addContact(person2);

        //test data for loan
        Loan loan1 = new Loan("lunch", createdDate3, 10.0, false,
                false, person1);
        Loan loan2 = new Loan("textbook", createdDate2, 150.0, false,
                false, person2);
        Loan loan3 = new Loan("dinner", createdDate1, 8.0, true,
                false, person1);
        Loan loan4 = new Loan("phone bills", createdDate2, 50.0, false,
                false, person1);
        Loan loan5 = new Loan("groceries", createdDate3, 15.0, false,
                false, person1);

        wallet.getLoanList().addLoan(loan1);
        wallet.getLoanList().addLoan(loan2);
        wallet.getLoanList().addLoan(loan3);
        wallet.getLoanList().addLoan(loan4);
        wallet.getLoanList().addLoan(loan5);

        //test data for expense
        Expense expense1 = new Expense("textbook", createdDate3, 100.0, Category.SHOPPING,
                false, RecurrenceRate.NO);
        Expense expense2 = new Expense("groceries", createdDate3, 50.0, Category.SHOPPING,
                false, RecurrenceRate.NO);
        Expense expense3 = new Expense("taxi", createdDate2, 15.0, Category.TRANSPORT,
                false, RecurrenceRate.NO);
        Expense expense4 = new Expense("grab", createdDate1, 10.0, Category.TRANSPORT,
                false, RecurrenceRate.NO);
        Expense expense5 = new Expense("Ajisen Ramen", createdDate3, 20.0, Category.FOOD,
                false, RecurrenceRate.NO);
        Expense expense6 = new Expense("Korean BBQ", createdDate3, 25.0, Category.FOOD,
                false, RecurrenceRate.NO);
        Expense expense7 = new Expense("Electricity bills", createdDate3, 100.0, Category.BILLS,
                false, RecurrenceRate.NO);
        Expense expense8 = new Expense("Phone bills", createdDate3, 100.0, Category.BILLS,
                false, RecurrenceRate.NO);
        Expense expense9 = new Expense("Witcher 3(game)", createdDate2, 25.0, Category.OTHERS,
                false, RecurrenceRate.NO);
        Expense expense10 = new Expense("Cyberpunk 2077(game)", createdDate1, 60.0, Category.OTHERS,
                false, RecurrenceRate.NO);

        wallet.getExpenseList().addExpense(expense1);
        wallet.getExpenseList().addExpense(expense2);
        wallet.getExpenseList().addExpense(expense3);
        wallet.getExpenseList().addExpense(expense4);
        wallet.getExpenseList().addExpense(expense5);
        wallet.getExpenseList().addExpense(expense6);
        wallet.getExpenseList().addExpense(expense7);
        wallet.getExpenseList().addExpense(expense8);
        wallet.getExpenseList().addExpense(expense9);
        wallet.getExpenseList().addExpense(expense10);

        //test data for budget
        Budget budget1 = new Budget(1500.0, 10, 2019);

        wallet.getBudgetList().addBudget(budget1);

        //save the test data into storage.
        wallet.getLoanList().setModified(true);
        wallet.getBudgetList().setModified(true);
        wallet.getExpenseList().setModified(true);
        wallet.getContactList().setModified(true);
        System.out.println(MESSAGE_SUCCESS);
        return false;
    }

    void deleteAllFiles() {
        File file = new File("./data/contact.txt");
        if (file.delete()) {
            System.out.println("contact.txt deleted successfully");
        }

        file = new File("./data/loan.txt");
        if (file.delete()) {
            System.out.println("loan.txt deleted successfully");
        }

        file = new File("./data/expense.txt");
        if (file.delete()) {
            System.out.println("expense.txt deleted successfully");
        }

        file = new File("./data/budget.txt");
        if (file.delete()) {
            System.out.println("budget.txt deleted successfully");
        }
    }
}
