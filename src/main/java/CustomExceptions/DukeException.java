package CustomExceptions;

import Enums.ExceptionType;

public class DukeException extends Exception {
    private String anomaly_Text = "Anomaly Detected";
    private String emptylist_Text = "List is empty";
    private String timeclash_Text = "Time Clash Detected";
    private String wrongFormat_Text = "Wrong Format Detected";

    /**
     * Constructor for DukeException Exception
     * Takes in the exception type thrown and prints out the specific error message
     * @param type type of exception detected
     */
    public DukeException(ExceptionType type){
        switch(type) {
            case emptylist:
                System.out.println(emptylist_Text);
                break;

            case timeClash:
                System.out.println(timeclash_Text);
                break;

            case wrongFormat:
                System.out.println(wrongFormat_Text);
                break;

            default:
                System.out.println(anomaly_Text);
                break;
        }
    }


}
