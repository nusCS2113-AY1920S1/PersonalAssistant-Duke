package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GenerateCommand extends Command {

    public static final String COMMAND_WORD = "generate";

    @Override
    public boolean execute(Wallet wallet) {

        deleteAllFiles(); //clear all data first
        wallet.getLoanList().getLoanList().clear();
        wallet.getContactList().getContactList().clear();
        wallet.getExpenseList().getExpenseList().clear();
        wallet.getBudgetList().getBudgetList().clear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate createdDate_1 = LocalDate.parse("10/10/2019", formatter);
        LocalDate createdDate_2 = LocalDate.parse("11/10/2019", formatter);
        LocalDate createdDate_3 = LocalDate.parse("12/10/2019", formatter);

        //test data for contact
        Contact person_1 = new Contact("Mary", "Sister", "91837362");
        Contact person_2 = new Contact("Tom", "Friend", "81276251");

        wallet.getContactList().addContact(person_1);
        wallet.getContactList().addContact(person_2);

        //test data for loan
        Loan loan_1 = new Loan("lunch", createdDate_3, 10.0, false, false, person_1);
        Loan loan_2 = new Loan("textbook", createdDate_2, 150.0, false, false, person_2);
        Loan loan_3 = new Loan("dinner", createdDate_1, 8.0, true, false, person_1);
        Loan loan_4 = new Loan("phone bills", createdDate_2, 50.0, false, false, person_1);
        Loan loan_5 = new Loan("groceries", createdDate_3, 15.0, false, false, person_1);

        wallet.getLoanList().addLoan(loan_1);
        wallet.getLoanList().addLoan(loan_2);
        wallet.getLoanList().addLoan(loan_3);
        wallet.getLoanList().addLoan(loan_4);
        wallet.getLoanList().addLoan(loan_5);

        //test data for expense
        Expense expense_1 = new Expense("textbook", createdDate_3, 100.0, Category.SHOPPING, false, RecurrenceRate.NO);
        Expense expense_2 = new Expense("groceries", createdDate_3, 50.0, Category.SHOPPING, false, RecurrenceRate.NO);
        Expense expense_3 = new Expense("taxi", createdDate_2, 15.0, Category.TRANSPORT, false, RecurrenceRate.NO);
        Expense expense_4 = new Expense("grab", createdDate_1, 10.0, Category.TRANSPORT, false, RecurrenceRate.NO);
        Expense expense_5 = new Expense("Ajisen Ramen", createdDate_3, 20.0, Category.FOOD, false, RecurrenceRate.NO);
        Expense expense_6 = new Expense("Korean BBQ", createdDate_3, 25.0, Category.FOOD, false, RecurrenceRate.NO);
        Expense expense_7 = new Expense("Electricity bills", createdDate_3, 100.0, Category.BILLS, false, RecurrenceRate.NO);
        Expense expense_8 = new Expense("Phone bills", createdDate_3, 100.0, Category.BILLS, false, RecurrenceRate.NO);
        Expense expense_9 = new Expense("Witcher 3(game)", createdDate_2, 25.0, Category.OTHERS, false, RecurrenceRate.NO);
        Expense expense_10 = new Expense("Cyberpunk 2077(game)", createdDate_1, 60.0, Category.OTHERS, false, RecurrenceRate.NO);

        wallet.getExpenseList().addExpense(expense_1);
        wallet.getExpenseList().addExpense(expense_2);
        wallet.getExpenseList().addExpense(expense_3);
        wallet.getExpenseList().addExpense(expense_4);
        wallet.getExpenseList().addExpense(expense_5);
        wallet.getExpenseList().addExpense(expense_6);
        wallet.getExpenseList().addExpense(expense_7);
        wallet.getExpenseList().addExpense(expense_8);
        wallet.getExpenseList().addExpense(expense_9);
        wallet.getExpenseList().addExpense(expense_10);

        //test data for budget
        Budget budget_1 = new Budget(1500.0, 10, 2019);

        wallet.getBudgetList().addBudget(budget_1);

        //save the test data into storage.
        wallet.getLoanList().setModified(true);
        wallet.getBudgetList().setModified(true);
        wallet.getExpenseList().setModified(true);
        wallet.getContactList().setModified(true);
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
