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

    public BudgetList getBudgetList() { return budgetList; }

    public void setBudgetList(BudgetList budgetList) {this.budgetList = budgetList; }

    public RecordList getRecordList() {
        return recordList;
    }

    public void setRecordList(RecordList recordList) {
        this.recordList = recordList;
    }

    public ExpenseList getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ExpenseList expenseList) {
        this.expenseList = expenseList;
    }

    public ContactList getContactList() {
        return contactList;
    }

    public void setContactList(ContactList contactList) {
        this.contactList = contactList;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(ScheduleList scheduleList) {
        this.scheduleList = scheduleList;
    }

    public LoanList getLoanList() {
        return loanList;
    }

    public void setLoanList(LoanList loanList) {
        this.loanList = loanList;
    }
}
