package task;

/**
 * Represent a deadline task and inherits all the fields and methods of Task parent class
 */
public class Deadline extends Task {

    protected String by; // private instance variables

    /**
     * Constructor for class Deadline
     * @param description String containing the description of the task
     * @param by String containing the date and time of the deadline for the task
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Converts user input command to a standardized format to store in file
     * @return String containing the standardized format
     */
    @Override
    public String toSaveString(){
        return "D" + super.toSaveString() + " | " + by;
    }

    /**
     * Converts user input command to a standardized format in taskList
     * @return String containing the standardized format
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Convert the user input date and time
     * @param userInputDate String containing the user input date and time
     * @return String containing the converted date and time
     */
    public String convertDate (String userInputDate) {
        String suffix = "";// st, nd, rd, th
        String extra = "";// am, pm
        String month = "";
        String dateline = "";//final conversion

        String[] monthArray = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        String date = userInputDate.split("\\s", 2)[0];
        String time = userInputDate.split("\\s", 2)[1];
        String min = time.substring(2, 4);//last 2 digits of the time
        String yr = date.split("/", 3)[2];

        int hr;
        int day = Integer.parseInt(date.split("/", 3)[0]);
        int mth = Integer.parseInt(date.split("/", 3)[1]);
        int first = Integer.parseInt(time.substring(0, 1));//first digit of the time
        int second = Integer.parseInt(time.substring(1, 2));//second digit of the time

        if(day == 1 || day == 21 || day == 31){
            suffix = "st";
        }else if(day == 2 || day == 22){
            suffix = "nd";
        }else if(day == 3 || day == 23){
            suffix = "rd";
        }else{
            suffix = "th";
        }

        //differentiating morning and afternoon
        extra = (first == 0 || (first == 1 && (second == 0 || second == 1))) ? "am" : "pm";
        int change = Integer.parseInt(time.substring(0, 2)) - 12;
        //converting the hours
        hr = (first == 0) ? ((second == 0) ? 12 : second) :
                ((first == 1) ? ((second <= 2) ? (first*10 + second) :
                        change) : change);
        //converting the month
        for (int i = 0; i < 12; i++){
            if(mth == i + 1){
                month = monthArray[i];
            }
        }

        dateline = day + suffix + " of " + month + " " + yr + ", " + hr + "." + min + extra;
        return dateline;
    }
}