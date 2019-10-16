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
    protected Date bookedFrom;
    protected Date bookedTill;
    protected int loanId;

    // for creation of new resource
    public Resource(String name) {
        this.name = name;
        this.bookedFrom = null;
        this.bookedTill = null;
        this.loanId = -1; // magic number!
        // find some way to auto generate id
    }

    // for loading an unbooked resource from data list when RIMS is started up
    public Resource(String name, int id) {
        this.name = name;
        this.id = id;
        this.bookedFrom = null;
        this.bookedTill = null;
        this.loanId = -1;
    }

    // for loading a booked resource from data list when RIMS is started up
    public Resource(String name, int id, int loanId, String stringDateFrom, String stringDateTill) throws ParseException {
        this.name = name;
        this.id = id;
        setLoanId(loanId);
        this.bookedFrom = stringToDate(stringDateFrom);
        this.bookedTill = stringToDate(stringDateTill);
        if (!(isBookedOrReserved())) {
            markAsReturned();
        }
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

    public boolean isBookedNow() {
        if (bookedFrom == null && bookedTill == null) {
            return false;
        }
        Date currentDate = new Date(System.currentTimeMillis());
        return (currentDate.after(bookedFrom) && currentDate.before(bookedTill));
    }

    public boolean isBookedOn(Date date) {
        if (bookedFrom == null && bookedTill == null) {
            return false;
        }
        return (date.after(bookedFrom) && date.before(bookedTill));
    }

    public boolean isBookedOrReserved() {
        if (bookedFrom == null && bookedFrom == null) {
            return false;
        }
        Date currentDate = new Date(System.currentTimeMillis());
        return (isBookedNow() || currentDate.before(bookedFrom));
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

    // for loaning: from now till stringDateTill
    public void markAsBooked(String stringDateTill, int loanId) throws ParseException {
        bookedFrom = new Date(System.currentTimeMillis());
        bookedTill = stringToDate(stringDateTill);
        this.loanId = loanId;
    }

    // for reserving: from stringDateFrom till stringDateTill
    public void markAsBooked(String stringDateFrom, String stringDateTill, int loanId) throws ParseException {
        bookedFrom = stringToDate(stringDateFrom);
        bookedTill = stringToDate(stringDateTill);
        this.loanId = loanId;
    }

    public void markAsReturned() {
        this.loanId = -1;
        this.bookedFrom = null;
        this.bookedTill = null;
    }

    @Override
    public String toString() {
        return "[" + getType() + "] " + getName();
    }

}