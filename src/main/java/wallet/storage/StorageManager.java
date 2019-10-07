package wallet.storage;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Budget;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.task.Task;

import java.io.File;
import java.util.ArrayList;

public class StorageManager {
    public static final String MESSAGE_ERROR_MKDIR = "Error when trying to create directory.";
    public static final String DEFAULT_STORAGE_DIRECTORY = "./data";

    private TaskStorage taskStorage;
    private ExpenseStorage expenseStorage;
    private LoanStorage loanStorage;
    private ContactStorage contactStorage;
    private BudgetStorage budgetStorage;

    /**
     * Constructs a new StorageManager object with all storage classes.
     */
    public StorageManager() {
        createDir();
        this.taskStorage = new TaskStorage();
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
     * Loads the Task objects into an ArrayList of Tasks.
     *
     * @return The ArrayList of Tasks.
     */
    public ArrayList<Task> loadTask() {
        return taskStorage.loadFile();
    }

    /**
     * Writes a Task object into task.txt.
     *
     * @param task The Task object.
     */
    public void addTask(Task task) {
        taskStorage.writeToFile(task);
    }

    /**
     * Deletes the Task from the file.
     *
     * @param taskList The ArrayList of Task objects.
     * @param index The index of the Task object.
     */
    public void deleteTask(ArrayList<Task> taskList, int index) {
        taskStorage.removeFromFile(taskList, index);
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
    public void save(Wallet wallet) {
        if (wallet.getExpenseList().getIsModified()) {
            expenseStorage.writeListToFile(wallet.getExpenseList().getExpenseList());
            wallet.getExpenseList().setModified(false);
        }
        if (wallet.getLoanList().getIsModified()) {
            loanStorage.writeListToFile(wallet.getLoanList().getLoanList());
            wallet.getLoanList().setModified(false);
        }
        if (wallet.getContactList().getIsModified()) {
            contactStorage.writeListToFile(wallet.getContactList().getContactList());
            wallet.getContactList().setModified(false);
        }
        if (wallet.getBudgetList().getIsModified()) {
            budgetStorage.writeListToFile(wallet.getBudgetList().getBudgetList());
            wallet.getBudgetList().setModified(false);
        }
    }
}
