package entertainment.pro.model;

public class Period extends Tasks {
    private String start;
    private String end;
    private MyDate date;

    /**
     * Constructor for a period class where the user watches a movie within a certain period of time
     *
     * @param description: which is the name of movie
     * @param type: which is the type of movie
     * @param start: which is the time by which the user first wants to watch the movie
     * @param end: which is the time by which the movie needs to be watched
     */
    public Period(String description, String type, String start, String end) {
        super(description, type);
        this.start = start;
        this.end = end;
        date = new MyDate(start, end);
    }

    /**
     * returns the time interval of the task
     * @return time interval of the task
     */
    public String getPeriod() {
        return date.toString();
    }

    /**
     * returns the interval of the task in date format
     * @return date format of the task interval
     */
    public MyDate getDate() {
        return date;
    }

    /**
     * description of the task
     * @return String format of the description of the task
     */
    public String toMessage() {
        return description
                + " (Period: " + date.toString() + ")";
    }
}
