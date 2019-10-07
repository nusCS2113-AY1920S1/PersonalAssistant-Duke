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

    public Instalment(float price, String description, String category, LocalDate boughtDate, int numOfPayments, float AnnualIR) {
        super(price, description, category, boughtDate);
        this.numOfPayments = numOfPayments;
        this.AIR = AnnualIR / 100;
        this.MIR = AIR / 12;
        this.endDate = setEndTime();
        dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    public float EqualMonthlyInstalment() {
        return (float) ((getPrice() * MIR * Math.pow(1 + MIR, numOfPayments)) / (Math.pow(1 + MIR, numOfPayments) - 1));
    }

    public float totalAmount() { return EqualMonthlyInstalment() * numOfPayments; }

    public LocalDate setEndTime () { return getDateBoughtDate().plusMonths(numOfPayments); }

    public String toString() { return "[INS]" + "$" + getPrice() + " " + getDescription() + "(on: " + getBoughtDate() + ")"; }

    public String getCategory() { return super.getCategory(); }

    public String getBoughtDate() { return getDateBoughtDate().format(dateTimeFormatter); }

    public LocalDate getEndDate()  { return endDate; }

    public String getDateEndDate() { return getEndDate().format(dateTimeFormatter); }

    public int getNumOfPayments() { return numOfPayments; }

    public float getAIR() { return AIR; }
}
