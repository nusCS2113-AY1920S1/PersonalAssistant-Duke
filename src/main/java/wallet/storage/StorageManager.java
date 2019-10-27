package wallet.storage;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Budget;
import wallet.model.record.Expense;
import wallet.model.record.Loan;

import java.io.File;
import java.util.ArrayList;

public class StorageManager {
    public static final String MESSAGE_ERROR_MKDIR = "Error when trying to create directory.";
    public static final String DEFAULT_STORAGE_DIRECTORY = "./data";

    private ExpenseStorage expenseStorage;
    private LoanStorage loanStorage;
    private ContactStorage contactStorage;
    private BudgetStorage budgetStorage;

    /**
     * Constructs a new StorageManager object with all storage classes.
     */
    public StorageManager() {
        createDir();
        this.expenseStorage = new ExpenseStorage();
        this.loanStorage = new LoanStorage();
        this.contactStorage = new ContactStorage();
        this.loanStorage.setContactStorage(this.contactStorage);
        this.budgetStorage = new BudgetStorage();
    }

    /**
     * Creates the directory for storing data if it does not exist.
     */
    public void createDir() {
        File file = new File(DEFAULT_STORAGE_DIRECTORY);
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (SecurityException se) {
                System.out.println(MESSAGE_ERROR_MKDIR);
            }
        }
    }

    /**
     * Loads the Expense objects into an ArrayList of Expenses.
     *
     * @return The ArrayList of Expenses.
     */
    public ArrayList<Expense> loadExpense() {
        return expenseStorage.loadFile();
    }

    /**
     * Loads the Loan objects into an ArrayList of Loans.
     *
     * @return The ArrayList of Loans.
     */
    public ArrayList<Loan> loadLoan() {
        return loanStorage.loadFile();
    }

    /**
     * Loads the Contact objects into an ArrayList of Contacts.
     *
     * @return The ArrayList of Contacts.
     */
    public ArrayList<Contact> loadContact() {
        return contactStorage.loadFile();
    }

    /**
     * Loads the Budget object in the ArrayList of Budget objects.
     *
     * @return the ArrayList of Budgets objects.
     */
    public ArrayList<Budget> loadBudget() {
        return budgetStorage.loadFile();
    }

    /**
     * Checks if lists are modified and updates save file.
     */
    public boolean save(Wallet wallet) {
        boolean isModified = false;
        if (wallet.getExpenseList().getIsModified()) {
            expenseStorage.writeListToFile(wallet.getExpenseList().getExpenseList());
            wallet.getExpenseList().setModified(false);
            isModified = true;
        }
        if (wallet.getLoanList().getIsModified()) {
            loanStorage.writeListToFile(wallet.getLoanList().getLoanList());
            wallet.getLoanList().setModified(false);
            isModified = true;
        }
        if (wallet.getContactList().getIsModified()) {
            contactStorage.writeListToFile(wallet.getContactList().getContactList());
            wallet.getContactList().setModified(false);
            isModified = true;
        }
        if (wallet.getBudgetList().getIsModified()) {
            budgetStorage.writeListToFile(wallet.getBudgetList().getBudgetList());
            wallet.getBudgetList().setModified(false);
            isModified = true;
        }
        return isModified;
    }
}
