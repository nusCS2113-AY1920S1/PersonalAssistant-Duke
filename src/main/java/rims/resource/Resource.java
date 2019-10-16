package rims.resource;

import rims.command.*;
import rims.exception.*;
import rims.core.*;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public abstract class Resource {
    protected String name;
    protected int id;
    protected char type;
    protected boolean booked;
    protected Date bookedFrom;
    protected Date bookedTill;
    protected int loanId;

    // for creation of new resource
    public Resource(String name) {
        this.name = name;
        this.booked = false;
        this.bookedFrom = null;
        this.bookedTill = null;
        this.loanId = -1; // change magic number!
    }

    // for loading an unbooked resource from data list when RIMS is started up
    public Resource(String name, int id, boolean isBooked) {
        this.name = name;
        this.id = id;
        this.booked = isBooked;
        this.loanId = -1;
    }

    // for loading a booked resource from data list when RIMS is started up
    public Resource(String name, int id, boolean isBooked, int loanId, String stringDateFrom, String stringDateTill) throws ParseException {
        this.name = name;
        this.id = id;
        this.booked = isBooked;
        setLoanId(loanId);
        this.bookedFrom = stringToDate(stringDateFrom);
        this.bookedTill = stringToDate(stringDateTill);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getLoanId() {
        return loanId;
    }

    public char getType() {
        return type;
    }

    public Date getDateBookedFrom() {
        return bookedFrom;
    }

    public Date getDateBookedTill() {
        return bookedTill;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date dateValue = formatter.parse(stringDate);
        return dateValue;
    }

    public String dateToString(Date thisDate) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
        String stringDate = format.format(thisDate);
        return stringDate;
    }

    public void markAsBooked(String stringDateTill, int loanId) throws ParseException {
        booked = true;
        bookedFrom = new Date(System.currentTimeMillis());
        bookedTill = stringToDate(stringDateTill);
        this.loanId = loanId;
    }

    public void markAsBooked(String StringDateFrom, String stringDateTill, int loanId) throws ParseException {
        booked = true;
        bookedFrom = stringToDate(StringDateFrom);
        bookedTill = stringToDate(stringDateTill);
        this.loanId = loanId;
    }

    public void markAsReturned() {
        this.loanId = -1;
        this.booked = false;
        this.bookedFrom = null;
        this.bookedTill = null;
    }

    @Override
    public String toString() {
        return "[" + getType() + "] " + getName();
    }

}