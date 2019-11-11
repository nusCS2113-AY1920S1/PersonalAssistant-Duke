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
