package wallet.model;

import wallet.model.contact.ContactList;
import wallet.model.record.ExpenseList;
import wallet.model.record.RecordList;
import wallet.model.task.ScheduleList;
import wallet.model.task.TaskList;

public class Wallet {
    private RecordList recordList;
    private ExpenseList expenseList;
    private ContactList contactList;
    private TaskList taskList;
    private ScheduleList scheduleList;

    /**
     * Constructs a wallet object with all the lists needed.
     */
    public Wallet(RecordList recordList, ExpenseList expenseList, ContactList contactList,
                  TaskList taskList, ScheduleList scheduleList) {
        this.recordList = recordList;
        this.expenseList = expenseList;
        this.contactList = contactList;
        this.taskList = taskList;
        this.scheduleList = scheduleList;
    }

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
}
