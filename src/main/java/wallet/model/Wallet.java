package wallet.model;

import wallet.model.contact.ContactList;
import wallet.model.record.BudgetList;
import wallet.model.record.ExpenseList;
import wallet.model.record.LoanList;
import wallet.model.record.RecordList;
import wallet.model.task.ScheduleList;
import wallet.model.task.TaskList;

public class Wallet {
    private BudgetList budgetList;
    private RecordList recordList;
    private ExpenseList expenseList;
    private ContactList contactList;
    private TaskList taskList;
    private ScheduleList scheduleList;
    private LoanList loanList;

    /**
     * Default constructor with no data.
     */
    public Wallet() {
        this.budgetList = new BudgetList();
        this.recordList = new RecordList();
        this.expenseList = new ExpenseList();
        this.contactList = new ContactList();
        this.taskList = new TaskList();
        this.scheduleList = new ScheduleList();
        this.loanList = new LoanList();
    }

    /**
     * Constructs a Wallet object.
     * @param budgetList The BudgetList object.
     * @param recordList The RecordList object.
     * @param expenseList The ExpenseList object.
     * @param contactList The ContactList object.
     * @param taskList The TaskList object.
     * @param scheduleList The ScheduleList object.
     * @param loanList The LoanList object.
     */
    public Wallet(BudgetList budgetList, RecordList recordList, ExpenseList expenseList, ContactList contactList,
                  TaskList taskList, ScheduleList scheduleList, LoanList loanList) {
        this.budgetList = budgetList;
        this.recordList = recordList;
        this.expenseList = expenseList;
        this.contactList = contactList;
        this.taskList = taskList;
        this.scheduleList = scheduleList;
        this.loanList = loanList;
    }

    public BudgetList getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(BudgetList budgetList) {
        this.budgetList = budgetList;
    }

    /**
     * Returns the RecordList object.
     *
     * @return The RecordList object.
     */
    public RecordList getRecordList() {
        return recordList;
    }

    /**
     * Sets the RecordList object.
     *
     * @param recordList The RecordList object.
     */
    public void setRecordList(RecordList recordList) {
        this.recordList = recordList;
    }

    /**
     * Returns the ExpenseList object.
     *
     * @return The ExpenseList object.
     */
    public ExpenseList getExpenseList() {
        return expenseList;
    }

    /**
     * Sets the ExpenseList object.
     *
     * @param expenseList The ExpenseList object.
     */
    public void setExpenseList(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }

    /**
     * Returns the ContactList object.
     *
     * @return The ContactList object,
     */
    public ContactList getContactList() {
        return contactList;
    }

    /**
     * Sets the ContactList object.
     *
     * @param contactList The ContactList object.
     */
    public void setContactList(ContactList contactList) {
        this.contactList = contactList;
    }

    /**
     * Returns the TaskList object.
     *
     * @return The TaskList object.
     */
    public TaskList getTaskList() {
        return taskList;
    }

    /**
     * Sets the TaskList object.
     *
     * @param taskList THe TaskList object.
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Returns the ScheduleList object.
     *
     * @return The ScheduleList object.
     */
    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    /**
     * Sets the ScheduleList object.
     *
     * @param scheduleList The ScheduleList object.
     */
    public void setScheduleList(ScheduleList scheduleList) {
        this.scheduleList = scheduleList;
    }

    /**
     * Returns the LoanList object.
     *
     * @return The LoanList object.
     */
    public LoanList getLoanList() {
        return loanList;
    }

    /**
     * Sets the LoanList object.
     *
     * @param loanList The LoanList Object.
     */
    public void setLoanList(LoanList loanList) {
        this.loanList = loanList;
    }
}
