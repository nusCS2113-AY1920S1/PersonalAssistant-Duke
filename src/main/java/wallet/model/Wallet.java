package wallet.model;

import wallet.model.contact.ContactList;
import wallet.model.record.BudgetList;
import wallet.model.record.ExpenseList;
import wallet.model.record.LoanList;
import wallet.model.record.RecordList;

public class Wallet {
    private BudgetList budgetList;
    private RecordList recordList;
    private ExpenseList expenseList;
    private ContactList contactList;
    private LoanList loanList;

    /**
     * Default constructor with no data.
     */
    public Wallet() {
        this.budgetList = new BudgetList();
        this.recordList = new RecordList();
        this.expenseList = new ExpenseList();
        this.contactList = new ContactList();
        this.loanList = new LoanList();
    }

    /**
     * Constructs a Wallet object.
     * @param budgetList The BudgetList object.
     * @param recordList The RecordList object.
     * @param expenseList The ExpenseList object.
     * @param contactList The ContactList object.
     * @param loanList The LoanList object.
     */
    public Wallet(BudgetList budgetList, RecordList recordList, ExpenseList expenseList,
                  ContactList contactList, LoanList loanList) {
        this.budgetList = budgetList;
        this.recordList = recordList;
        this.expenseList = expenseList;
        this.contactList = contactList;
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
