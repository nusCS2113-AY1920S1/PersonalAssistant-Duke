package money;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.lang.Math;
import java.time.format.DateTimeFormatter;

/**
 * This class is created for the Instalments of the user.
 */
public class Instalment extends Expenditure {
    private LocalDate endDate;
    private int numOfPaymentsReq;
    private float annualInterestRate;
    private float monthlyInterestRate;
    private DateTimeFormatter dateTimeFormatter;
    private int  paymentsMade;
    private float percentage;
    private boolean payForTheMonth;
    private boolean fullyPaid;

    //@@author ChenChao19
    /**
     * Constructor of the Instalment Object to record the instalments.
     * @param price Total cost of the item that the user bought
     * @param description The item that the Instalment is paying for
     * @param category The type of money object that the user is recording
     * @param boughtDate The date when the user buy the item
     * @param numOfPaymentsReq The total number of months of payment required for the user to pay his Instalment
     * @param annualIR The Annual Interest Rate that the user is paying
     */
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
        this.fullyPaid = false;
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

    /**
     * This method is the internal algorithm used to calculate the monthly payment
     * which is called the equal monthly instalment paying method.
     * @return the monthly payment required by the user for the particular item that
     *         the user bought
     */
    public float equalMonthlyInstalment() {
        return (float) ((getPrice() * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numOfPaymentsReq))
                / (Math.pow(1 + monthlyInterestRate, numOfPaymentsReq) - 1));
    }

    public int getPaymentsMade() {
        return paymentsMade;
    }

    /**
     * This method sets the percentage that the user has paid for the Instalment.
     * @param payments The number of payments made by the user in total
     */
    public void percentPay(int payments) {
        this.paymentsMade = payments;
        if (paymentsMade == numOfPaymentsReq) {
            this.percentage = 100;
            return;
        }
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
        return "[INS]" + "$" + getPriceStr() + " "
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

    /**
     * This method is called when the Instalment is fully paid for.
     */
    public void setFullyPaid() {
        fullyPaid = true;
    }

    public boolean getFullyPaid() {
        return fullyPaid;
    }
}