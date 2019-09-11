/**
 * Enumeration containing the names of each month in order.
 */
public enum Months {
    Jan("January", "1"),
    Feb("February", "2"),
    Mar("March", "3"),
    April("April", "4"),
    May("May", "5"),
    June("June", "6"),
    July("July", "7"),
    Aug("August", "8"),
    Sep("September", "9"),
    Oct("October", "10"),
    Nov("November", "11"),
    Dec("December", "12");

    /**
     * Name of month
     */
    private final String month;

    /**
     * Month index
     */
    private final String num;

    /**
     * Creates new month.
     * @param inputMonth Name of month
     * @param inputNum Index of month
     */
    Months(String inputMonth, String inputNum){
        month = inputMonth;
        num = inputNum;
    }

    /**
     * @return current month
     */
    public String getMonth(){
        return month;
    }

    /**
     * Gets index of current month.
     * @return index of current month
     */
    public int getNum(){
        return Integer.parseInt(num);
    }
}
