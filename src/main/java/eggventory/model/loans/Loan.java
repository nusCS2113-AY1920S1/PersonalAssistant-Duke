package eggventory.model.loans;

import eggventory.model.PersonList;

import java.util.ArrayList;

//@@author cyanoei
public class Loan {

    private String stockCode;
    private String matricNo;
    private int quantity;

    /**
     * Temporary shorter constructor used to test the non-date attributes.
     * @param matricNo the matric number of the person making the loan.
     * @param stockCode the stockCode of the stock being loaned.
     * @param quantity the quantity being loaned out.
     */
    public Loan(String matricNo, String stockCode, int quantity) {
        //AddLoanCommand should have determined beforehand that the Person and Stock being referred to
        //are existing entries.
        this.matricNo = matricNo;
        this.stockCode = stockCode;
        this.quantity = quantity;
    }

    /**
     * Constructor for creating templates.
     * @param stockCode Stockcode of the loan.
     * @param quantity Quantity to be loaned out.
     */
    public Loan(String stockCode, String quantity) {
        this.stockCode = stockCode;
        this.quantity = Integer.parseInt(quantity);
    }

    /**
     * Gets the stockcode of the Stock being loaned.
     * @return the stockcode.
     */
    public String getStockCode() {
        return stockCode;
    }

    /**
     * Gets the matric number of the person loaning the stock.
     * @return the matric number.
     */
    public String getMatricNo() {
        return matricNo;
    }

    /**
     * Gets the quantity of Stock that this Person loaned.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the new quantity of the Stock loaned.
     * @param quantity the new amount that is loaned.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Determines if this Loan matches both the queried Stock and Person.
     * @param stockCode the stockCode of the Stock.
     * @param matricNo the matric number of the Person.
     * @return whether they are both equal.
     */
    public boolean loanEquals(String matricNo, String stockCode) {
        return this.matricNo.equals(matricNo) && this.stockCode.equals(stockCode);
    }

    /**
     * Formats the details of this loan in a user-friendly print statement.
     * @return the string containing the details.
     */
    @Override
    public String toString() {
        return getMatricNo() + " loaned " + getQuantity() + " of " + getStockCode() + ".";
    }

    /**
     * Gets the data of this stock as an arraylist.
     */
    public ArrayList<String> getStockDataAsArray() {
        ArrayList<String> data = new ArrayList<>();

        data.add(stockCode);
        data.add(String.valueOf(quantity));

        return data;
    }

    /**
     * Gets all data of this loan as an arraylist.
     * @return The array list of all data in this loan.
     */
    public ArrayList<String> getDataAsArray() {
        ArrayList<String> dataArray = new ArrayList<>();

        dataArray.add(matricNo);
        dataArray.add(PersonList.getName(matricNo));
        dataArray.add(stockCode);
        dataArray.add(Integer.toString(quantity));

        return dataArray;
    }

    public String savedLoanString() {
        return String.format("%s,%s,%s",matricNo, stockCode,quantity);
    }
    //@@author


}

