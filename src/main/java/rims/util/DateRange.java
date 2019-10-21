package rims.util;

import java.util.Date;

public class DateRange {
    private Date startDate;
    private Date endDate;

    public DateRange (Date startDate, Date endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String toString(){
        String s;
        s = startDate + " - " + endDate;
        return s;
    }
}