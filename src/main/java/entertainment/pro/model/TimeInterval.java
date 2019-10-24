package entertainment.pro.model;

import java.util.Date;

public class TimeInterval {
    private Date startDate;
    private Date endDate;

    public TimeInterval(Date s, Date e) {
        startDate = s;
        endDate = e;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
