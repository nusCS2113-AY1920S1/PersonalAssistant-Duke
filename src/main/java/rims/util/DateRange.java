package rims.util;

import java.util.Date;

public class DateRange {
    private Date startDate;
    private Date endDate;

    public DateRange(){
        ;
    }

    public DateRange (Date startDate, Date endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate(){
        return startDate;
    }

    public Date getEndDate(){
        return endDate;
    }
    
    public String toString(){
        String s;
        s = startDate + " - " + endDate;
        return s;
    }
}