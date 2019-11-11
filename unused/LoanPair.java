package eggventory.model.loans;

//@@author cyanoei-unused

//Unused because Loan Pairs are now stored differently within the LoanList.

/**
 * Data structure to store the pairs of Person and Stock, used as the key for accessing the relevant Loan.
 * For ease of implementation, LoanPairs' two attributes should never be modified.
 */
public class LoanPair {
    private String stockCode;
    private String matricNo;

    public LoanPair(String stockCode, String matricNo) {
        this.stockCode = stockCode;
        this.matricNo = matricNo;
    }

    /**
     * Gets the stockCode of this pair.
     * @return the stockCode.
     */
    public String getStockCode() {
        return stockCode;
    }

    /**
     * Gets the matricNo of this pair.
     * @return the matric number.
     */
    public String getMatricNo() {
        return matricNo;
    }

    /**
     * Custom .equals method to compare two LoanPairs.
     * @param other the LoanPair to compare to.
     * @return true if their stockCode and matricNo are the same, false otherwise.
     */
    public boolean equals(LoanPair other) {
        return this.stockCode.equals(other.getStockCode()) && this.matricNo.equals(other.getMatricNo());
    }
}
