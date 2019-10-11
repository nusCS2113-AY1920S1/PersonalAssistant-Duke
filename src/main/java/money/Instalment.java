package money;

import java.time.LocalDate;
import java.lang.Math;
import java.time.format.DateTimeFormatter;

public class Instalment extends Expenditure {
    private LocalDate endDate;
    private int numOfPayments;
    private float AIR;
    private float MIR;
    private DateTimeFormatter dateTimeFormatter;
    private int  paymentsMade;
    private float percentage;
    private boolean payForTheMonth;

    public Instalment(float price, String description, String category, LocalDate boughtDate, int numOfPayments, float AnnualIR) {
        super(price, description, category, boughtDate);
        this.numOfPayments = numOfPayments;
        this.AIR = AnnualIR / 100;
        this.MIR = AIR / 12;
        this.endDate = setEndTime();
        dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        this.payForTheMonth = false;
    }

    public void isPayTheMonth() {
        payForTheMonth = true;
    }

    public void isNotPayTheMonth() {
        payForTheMonth = false;
    }

    public boolean getPayForTheMonth() {
        return payForTheMonth;
    }

    public float EqualMonthlyInstalment() {
        return (float) ((getPrice() * MIR * Math.pow(1 + MIR, numOfPayments)) / (Math.pow(1 + MIR, numOfPayments) - 1));
    }

    public void percentPay(int payments) {
        this.paymentsMade = payments;
        this.percentage = (float) paymentsMade / numOfPayments * 100;
    }

    public float getPercentage() {
        return percentage;
    }

    public float totalAmount() { return EqualMonthlyInstalment() * numOfPayments; }

    public LocalDate setEndTime () { return getDateBoughtDate().plusMonths(numOfPayments); }

    public String toString() { return "[INS]" + "$" + getPrice() + " "
            + getDescription() + "(on: " + getBoughtDate() + ")"; }

    public String getCategory() { return super.getCategory(); }

    public String getBoughtDate() { return getDateBoughtDate().format(dateTimeFormatter); }

    public LocalDate getEndDate()  { return endDate; }

    public String getDateEndDate() { return getEndDate().format(dateTimeFormatter); }

    public int getNumOfPayments() { return numOfPayments; }

    public float getAIR() { return AIR; }
}
