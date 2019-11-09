package owlmoney.model.bond;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

class BondTest {
    private static final String NEWLINE = System.lineSeparator();

    @Test
    void bondStub_normalBondStub_success() throws ParseException {
        BondStub testBond = new BondStub();

        int actualYear = testBond.getYear();
        int expectedYear = 1;
        assertEquals(expectedYear,actualYear);

        String actualDate = testBond.getDate();
        String expectedDate = "01 January 2019";
        assertEquals(expectedDate,actualDate);

        double actualYearlyCouponRate = testBond.getYearlyCouponRate();
        double expectedYearlyCouponRate = 2.0;
        assertEquals(expectedYearlyCouponRate,actualYearlyCouponRate);

        double actualHalfYearlyCouponRate = testBond.getHalfYearlyCouponRate();
        double expectedHalfYearlyCouponRate = 1.0;
        assertEquals(expectedHalfYearlyCouponRate,actualHalfYearlyCouponRate);

        double actualAmount = testBond.getAmount();
        double expectedAmount = 500.00;
        assertEquals(expectedAmount,actualAmount);

        String actualName = testBond.getName();
        String expectedName = "TEST BOND";
        assertEquals(expectedName,actualName);

        String actualCategory = testBond.getCategory();
        String expectedCategory = "bonds";
        assertEquals(expectedCategory,actualCategory);

        String actualResultOfBondDescription = testBond.getBondDescription();
        String expectedResultOfBondDescription = "Name: TEST BOND Amount: $500.00 Rate: 2.00 "
                + "Date Purchased: 01 January 2019 Number of years: 1";
        assertEquals(expectedResultOfBondDescription,actualResultOfBondDescription);
    }

    @Test
    void bondStubSetRate_bondStubNewRate_success() throws ParseException {
        BondStub testBond = new BondStub();
        testBond.setRate(5.0);
        double actualNewRate = testBond.getNewRate();
        double expectedNewRate = 5.0;
        assertEquals(expectedNewRate, actualNewRate);
    }

    @Test
    void bondStubSetYear_bondStubNewYear_success() throws ParseException {
        BondStub testBond = new BondStub();
        testBond.setYear(5);
        int actualNewYear = testBond.getNewYear();
        int expectedNewYear = 5;
        assertEquals(expectedNewYear, actualNewYear);
    }

    @Test
    void bond_normalBond_success() {
        Bond testBond = new Bond("TEST BOND",1000,1.8,new Date("1/3/2019"),3);

        int actualYear = testBond.getYear();
        int expectedYear = 3;
        assertEquals(expectedYear,actualYear);

        String actualDate = testBond.getDate();
        String expectedDate = "03 January 2019";
        assertEquals(expectedDate,actualDate);

        double actualYearlyCouponRate = testBond.getYearlyCouponRate();
        double expectedYearlyCouponRate = 1.8;
        assertEquals(expectedYearlyCouponRate,actualYearlyCouponRate);

        double actualHalfYearlyCouponRate = testBond.getHalfYearlyCouponRate();
        double expectedHalfYearlyCouponRate = 0.9;
        assertEquals(expectedHalfYearlyCouponRate,actualHalfYearlyCouponRate);

        double actualAmount = testBond.getAmount();
        double expectedAmount = 1000.00;
        assertEquals(expectedAmount,actualAmount);

        String actualName = testBond.getName();
        String expectedName = "TEST BOND";
        assertEquals(expectedName,actualName);

        String actualCategory = testBond.getCategory();
        String expectedCategory = "bonds";
        assertEquals(expectedCategory,actualCategory);

        String actualResultOfBondDescription = testBond.getBondDescription();
        String expectedResultOfBondDescription = "Name: TEST BOND" + NEWLINE
                + "Amount: $1000.00" + NEWLINE
                + "Rate: 1.80" + NEWLINE
                + "Date Purchased: 03 January 2019" + NEWLINE
                + "Number of years: 3" + NEWLINE;
        assertEquals(expectedResultOfBondDescription,actualResultOfBondDescription);
    }

    @Test
    void bondSetRate_newRate_success() {
        Bond testBond = new Bond("TEST BOND",1000,1.8,new Date("1/3/2019"),3);
        testBond.setRate(2.0);
        double actualNewRate = testBond.getYearlyCouponRate();
        double expectedNewRate = 2.0;
        assertEquals(expectedNewRate, actualNewRate);
    }

    @Test
    void bondSetYear_newYear_success() {
        Bond testBond = new Bond("TEST BOND",1000,1.8,new Date("1/3/2019"),3);
        testBond.setYear(8);
        int actualNewYear = testBond.getYear();
        int expectedNewYear = 8;
        assertEquals(expectedNewYear, actualNewYear);
    }

    @Test
    void getNextDateToCreditInterest_oldDate_success() {
        Calendar calendarTestDate = Calendar.getInstance();
        calendarTestDate.add(Calendar.MONTH,-6);
        Date testDate = calendarTestDate.getTime();
        Bond testBond = new Bond("TEST BOND",1000,1.8,testDate,3);
        Date actualDate = testBond.getNextDateToCreditInterest();
        Calendar calendarExpectedDate = Calendar.getInstance();
        calendarExpectedDate.add(Calendar.MONTH,-6);
        calendarExpectedDate.add(Calendar.MONTH,6);
        Date expectedDate = calendarExpectedDate.getTime();
        assertEquals(expectedDate.toString(), actualDate.toString());
    }

    @Test
    void setNextDateToCreditInterest_oldDateToNewDate_success() {
        Calendar calendarTestDate = Calendar.getInstance();
        calendarTestDate.add(Calendar.MONTH,-6);
        Date testDate = calendarTestDate.getTime();
        Bond testBond = new Bond("TEST BOND",1000,1.8,testDate,3);
        Calendar calendarExpectedDate = Calendar.getInstance();
        calendarExpectedDate.add(Calendar.MONTH,-6);
        calendarExpectedDate.add(Calendar.MONTH,6);
        Date expectedDate = calendarExpectedDate.getTime();
        testBond.setNextDateToCreditInterest(expectedDate);
        assertEquals(expectedDate.toString(), testBond.getNextDateToCreditInterest().toString());
    }

    @Test
    void getBondEndDate_oldDateToEndDate_success() {
        Calendar calendarTestDate = Calendar.getInstance();
        calendarTestDate.add(Calendar.YEAR,-3);
        Date testDate = calendarTestDate.getTime();
        Bond testBond = new Bond("TEST BOND",1000,1.8,testDate,3);
        Calendar calendarExpectedDate = Calendar.getInstance();
        Date expectedDate = calendarExpectedDate.getTime();
        testBond.setNextDateToCreditInterest(expectedDate);
        assertEquals(expectedDate.toString(), testBond.getBondEndDate().toString());
    }

    @Test
    void getDateInDateFormat_oldDateToEndDate_success() {
        Calendar calendarTestDate = Calendar.getInstance();
        calendarTestDate.add(Calendar.YEAR,-3);
        Date testDate = calendarTestDate.getTime();
        Bond testBond = new Bond("TEST BOND",1000,1.8,testDate,3);
        assertEquals(testDate.toString(), testBond.getDateInDateFormat().toString());
    }
}
