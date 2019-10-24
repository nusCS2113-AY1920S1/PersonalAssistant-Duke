package money;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.lang.Math;
import java.time.format.DateTimeFormatter;

public class Instalment extends Expenditure {
    private LocalDate endDate;
    private int numOfPaymentsReq;
    private float annualInterestRate;
    private float monthlyInterestRate;
    private DateTimeFormatter dateTimeFormatter;
    private int  paymentsMade;
    private float percentage;
    private boolean payForTheMonth;

    //@@author ChenChao19
    public Instalment(float price, String description, String category,
                      LocalDate boughtDate, int numOfPaymentsReq, float annualIR) {
        super(price, description, category, boughtDate);
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        this.numOfPaymentsReq = numOfPaymentsReq;
        this.annualInterestRate = annualIR / 100;
        this.monthlyInterestRate = annualInterestRate / 12;
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

    public float equalMonthlyInstalment() {
        return (float) ((getPrice() * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numOfPaymentsReq))
                / (Math.pow(1 + monthlyInterestRate, numOfPaymentsReq) - 1));
    }

    public void percentPay(int payments) {
        this.paymentsMade = payments;
        this.percentage = (float) paymentsMade / numOfPaymentsReq * 100;
    }

    public float getPercentage() {
        return percentage;
    }

    public float totalAmount() {
        return equalMonthlyInstalment() * numOfPaymentsReq;
    }

    public LocalDate setEndTime() {
        return getDateBoughtDate().plusMonths(numOfPaymentsReq);
    }

    public String toString() {
        return "[INS]" + "$" + getPrice() + " "
            + getDescription() + "(on: " + getBoughtDate() + ")";
    }

    public String getCategory() {
        return super.getCategory();
    }

    public String getBoughtDate() {
        return getDateBoughtDate().format(dateTimeFormatter);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDateEndDate() {
        return getEndDate().format(dateTimeFormatter);
    }

    public int getNumOfPayments() {
        return numOfPaymentsReq;
    }

    public float getAnnualInterestRate() {
        return annualInterestRate;
    }
}