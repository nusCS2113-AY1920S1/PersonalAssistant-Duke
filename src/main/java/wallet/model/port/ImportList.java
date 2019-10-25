package wallet.model.port;

import wallet.model.record.Expense;
import wallet.model.record.Loan;

import java.util.ArrayList;

public class ImportList {
    private ArrayList<Loan> loanList;
    private ArrayList<Expense> expenseList;


    public ImportList(ArrayList<Loan> loanList, ArrayList<Expense> expenseList) {
        this.loanList = loanList;
        this.expenseList = expenseList;
    }

    public ArrayList<Loan> getLoanList() {
        return loanList;
    }

    public void setLoanList(ArrayList<Loan> loanList) {
        this.loanList = loanList;
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

}
