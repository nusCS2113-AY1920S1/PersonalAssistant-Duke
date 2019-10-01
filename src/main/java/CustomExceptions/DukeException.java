package CustomExceptions;

import Enums.ExceptionType;

public class DukeException extends Exception {
    private String anomaly_Text = "Anomaly Detected";
    private String emptylist_Text = "List is empty";
    private String timeclash_Text = "Time Clash Detected";

    public DukeException(){
     System.out.println();
    }

    public DukeException(ExceptionType type){
        switch(type) {
            case emptylist:
                System.out.println(emptylist_Text);
                break;

            case timeClash:
                System.out.println(timeclash_Text);
                break;

            default:
                System.out.println(anomaly_Text);
                break;
        }
    }
}
