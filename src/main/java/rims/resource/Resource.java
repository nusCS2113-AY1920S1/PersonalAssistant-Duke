package rims.resource;

import rims.core.Ui;

public abstract class Resource {
    protected String name;
    protected int id;
    protected String type;
    // protected Date bookedFrom;
    // protected Date bookedTill;
    protected int qty;
    protected Ui ui;

    // for adding from CLI
    public Resource(int id, String type, String name, int qty) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.qty = qty;
    }

    // for data loading
    public Resource(String id, String type, String name, String qty) {
        this.id = Integer.parseInt(id);
        this.type = type;
        this.name = name;
        this.qty = Integer.parseInt(qty);
    }

    public String toString() {
        String s;
        s = "ID: " + id + " [" + type + "]" + name + " Total Quantity: " + qty;
        return s;
    }

    public String toDataString() {
        String s;
        s = id + "," + type + "," + name + "," + qty;
        return s;
    }

    // // for loading an unbooked resource from data list when RIMS is started up
    // public Resource(String name, int id) {
    // this.name = name;
    // this.id = id;
    // this.bookedFrom = null;
    // this.bookedTill = null;
    // this.loanId = -1;
    // }

    // // for loading a booked resource from data list when RIMS is started up
    // public Resource(String name, int id, int loanId, String stringDateFrom,
    // String stringDateTill)
    // throws ParseException {
    // this.name = name;
    // this.id = id;
    // setLoanId(loanId);
    // this.bookedFrom = stringToDate(stringDateFrom);
    // this.bookedTill = stringToDate(stringDateTill);
    // if (!(isBookedOrReserved())) {
    // markAsReturned();
    // }
    // }

    // public String getName() {
    // return name;
    // }

    public int getResourceId() {
        return id;
    }

    public int getTotalQty() {
        return qty;
    }

    public String getResourceName(){
        return name;
    }

    // public int getLoanId() {
    // return loanId;
    // }

    // public char getType() {
    // return type;
    // }

    // public Date getDateBookedFrom() {
    // return bookedFrom;
    // }

    // public Date getDateBookedTill() {
    // return bookedTill;
    // }

    // public boolean isBookedNow() {
    // if (bookedFrom == null && bookedTill == null) {
    // return false;
    // }
    // Date currentDate = new Date(System.currentTimeMillis());
    // return (currentDate.after(bookedFrom) && currentDate.before(bookedTill));
    // }

    // public boolean isBookedOn(Date date) {
    // if (bookedFrom == null && bookedTill == null) {
    // return false;
    // }
    // return (date.after(bookedFrom) && date.before(bookedTill));
    // }

    // public boolean isBookedOrReserved() {
    // if (bookedFrom == null && bookedFrom == null) {
    // return false;
    // }
    // else{
    // return true;
    // }
    // // Date currentDate = new Date(System.currentTimeMillis());
    // // return (isBookedNow() || currentDate.before(bookedFrom));
    // }

    // public void setLoanId(int loanId) {
    // this.loanId = loanId;
    // }

    // public Date stringToDate(String stringDate) throws ParseException {
    // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
    // Date dateValue = formatter.parse(stringDate);
    // return dateValue;
    // }

    // public String dateToString(Date thisDate) {
    // DateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");
    // String stringDate = format.format(thisDate);
    // return stringDate;
    // }

    // // for loaning: from now till stringDateTill
    // public void markAsBooked(String stringDateTill, int loanId, int qty, Ui ui)
    // throws ParseException {
    // this.bookedFrom = new Date(System.currentTimeMillis());
    // this.bookedTill = stringToDate(stringDateTill);
    // this.loanId = loanId;

    // this.ui=ui;
    // ui.printLine();
    // ui.print("Done! I've marked this resource as loaned:");
    // if (this.getType() == 'I') {
    // ui.print(this.toString() + " (qty: " + Integer.toString(qty) + ")" + " Item
    // id:"
    // + Integer.toString(this.getId()));
    // ui.print("Requested date: " + this.getDateBookedFrom() + " - " +
    // this.getDateBookedTill());
    // } else if (this.getType() == 'R') {
    // ui.print(this.toString() + " Room id:" + Integer.toString(this.getId()));
    // }
    // ui.printLine();
    // }

    // // for reserving: from stringDateFrom till stringDateTill
    // public void markAsBooked(String stringDateFrom, String stringDateTill, int
    // loanId) throws ParseException {
    // this.bookedFrom = stringToDate(stringDateFrom);
    // this.bookedTill = stringToDate(stringDateTill);
    // this.loanId = loanId;
    // }

    // public void markAsReturned() {
    // this.loanId = -1;
    // this.bookedFrom = null;
    // this.bookedTill = null;
    // }

    // @Override
    // public String toString() {
    // return "[" + getType() + "] " + getName();
    // }
}