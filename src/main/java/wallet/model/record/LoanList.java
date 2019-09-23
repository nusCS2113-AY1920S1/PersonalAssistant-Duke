package wallet.model.record;

import java.util.ArrayList;

public class LoanList {
    private ArrayList<Loan> loanList;

    /**
     * Constructs the LoanList Object.
     */
    public LoanList() {
        this.loanList = new ArrayList<Loan>();
    }

    /**
     * Returns the list of loans.
     * @return The list of loans.
     */
    public ArrayList<Loan> getLoanList() {
        return this.loanList;
    }

    /**
     * Sets a LoanList.
     *
     * @param loanList The list of loans.
     */
    public void setLoanList(ArrayList<Loan> loanList) {
        this.loanList = loanList;
    }

    /**
     * Adds a Loan object to the list of loans.
     *
     * @param loan The Loan object.
     */
    public void addLoan(Loan loan) {
        this.loanList.add(loan);
    }

    /**
     * Gets the Loan object based on index in list of loans.
     *
     * @param index The index of the Loan object in the list of loans.
     * @return
     */
    public Loan getLoan(int index) {
        return this.loanList.get(index);
    }

    /**
     * Edits the Loan object in the list of loans.
     *
     * @param index The index of the Loan object in the list of loans.
     * @param loan The Loan object.
     */
    public void editLoan(int index, Loan loan) {
        this.loanList.set(index, loan);
    }

    /**
     * Returns the index of Loan object in the list of loans.
     *
     * @param loan The Loan object.
     * @return Index of Loan object in list of loans.
     */
    public int findLoanIndex(Loan loan) {
        return this.loanList.indexOf(loan);
    }

    /**
     * Returns size of the list of loans.
     *
     * @return Size of the list of loans.
     */
    public int getSize() {
        return this.loanList.size();
    }
}