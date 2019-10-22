package money;

import java.time.LocalDate;
import java.lang.Math;
import java.time.format.DateTimeFormatter;

public class Instalment extends Expenditure {
    private LocalDate endDate;
    private int numOfPaymentsReq;
    private float AIR;
    private float MIR;
    private DateTimeFormatter dateTimeFormatter;
    private int  paymentsMade;
    private float percentage;
    private boolean payForTheMonth;

    //@@ ChenChao19
    public Instalment(float price, String description, String category, LocalDate boughtDate, int numOfPaymentsReq, float AnnualIR) {
        super(price, description, category, boughtDate);
        this.numOfPaymentsReq = numOfPaymentsReq;
        this.AIR = AnnualIR / 100;
        this.MIR = AIR / 12;
        this.endDate = setEndTime();
        dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        this.payForTheMonth = false;
    }

    public void isPayTheMonth() { payForTheMonth = true; }

    public void isNotPayTheMonth() { payForTheMonth = false; }

    public boolean getPayForTheMonth() { return payForTheMonth; }

    public float EqualMonthlyInstalment() {
        return (float) ((getPrice() * MIR * Math.pow(1 + MIR, numOfPaymentsReq)) / (Math.pow(1 + MIR, numOfPaymentsReq) - 1));
    }

    public void percentPay(int payments) {
        this.paymentsMade = payments;
        this.percentage = (float) paymentsMade / numOfPaymentsReq * 100;
    }

    public float getPercentage() { return percentage; }

    public float totalAmount() { return EqualMonthlyInstalment() * numOfPaymentsReq; }

    public LocalDate setEndTime () { return getDateBoughtDate().plusMonths(numOfPaymentsReq); }

    public String toString() { return "[INS]" + "$" + getPrice() + " "
            + getDescription() + "(on: " + getBoughtDate() + ")"; }

    public String getCategory() { return super.getCategory(); }

    public String getBoughtDate() { return getDateBoughtDate().format(dateTimeFormatter); }

    public LocalDate getEndDate()  { return endDate; }

    public String getDateEndDate() { return getEndDate().format(dateTimeFormatter); }

    public int getNumOfPayments() { return numOfPaymentsReq; }

    public float getAIR() { return AIR; }
}