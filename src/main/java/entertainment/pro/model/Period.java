package entertainment.pro.model;

public class Period extends Tasks {
    private String start;
    private String end;
    private MyDate date;
    public Period(String description, String type, String start, String end) {
        super(description, type);
        this.start = start;
        this.end = end;
        date = new MyDate(start, end);
    }
    public String getPeriod() {
        return date.toString();
    }
    public MyDate getDate() {
        return date;
    }
    public String toMessage() {
        return description
                + " (Period: " + date.toString() + ")";
    }
}
