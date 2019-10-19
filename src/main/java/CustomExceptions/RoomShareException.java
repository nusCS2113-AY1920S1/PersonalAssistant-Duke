package CustomExceptions;

import Enums.ExceptionType;

public class RoomShareException extends Exception {
    private static final String outOfBounds_Text = "Index is out of Bounds!";
    private static final String anomaly_Text = "Anomaly Detected";
    private static final String emptylist_Text = "List is empty";
    private static final String timeclash_Text = "Time Clash Detected";
    private static final String wrongFormat_Text = "Wrong Format Detected";
    private static final String wrongPriority_Text = "Wrong Priority Detected";
    private static final String subTask_Text = "Meetings do not support Subtasks";

    /**
     * Constructor for DukeException Exception
     * Takes in the exception type thrown and prints out the specific error message
     * @param type type of exception detected
     */
    public RoomShareException(ExceptionType type){
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

        case outOfBounds:
            System.out.println(outOfBounds_Text);
            break;

        case wrongPriority:
            System.out.println(wrongPriority_Text);
            break;

        case subTask:
            System.out.println(subTask_Text);
            break;

        default:
            System.out.println(anomaly_Text);
            break;
        }
    }


}
