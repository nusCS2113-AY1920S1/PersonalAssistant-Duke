package CustomExceptions;

import Enums.ExceptionType;

public class RoomShareException extends Exception {
    private static final String outOfBounds_Text = "Index is out of Bounds!";
    private static final String anomaly_Text = "Anomaly Detected";
    private static final String emptyList_Text = "List is empty";
    private static final String timeClash_Text = "Time Clash Detected";
    private static final String wrongFormat_Text = "Wrong Format Detected";
    private static final String wrongPriority_Text = "Wrong Priority Detected";
    private static final String subTask_Text = "Meetings do not support Subtasks";
    public static final String wrongTaskType_Text = "only meeting or assignment tag are accepted";
    public static final String emptyDescription_Text = "You haven't included the description of you task";
    public static final String emptyDate_Text = "You haven't included the date of your task";
    public static final String emptyTaskType_Text = "You haven't specified the type of your task: assignment or meeting";

    /**
     * Constructor for DukeException Exception
     * Takes in the exception type thrown and prints out the specific error message
     * @param type type of exception detected
     */
    public RoomShareException(ExceptionType type){
        switch(type) {

        case emptyList:
            System.out.println(emptyList_Text);
            break;
            
        case timeClash:
            System.out.println(timeClash_Text);

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

        case wrongTaskType:
            System.out.println(wrongTaskType_Text);
            break;

        case emptyDescription:
            System.out.println(emptyDescription_Text);
            break;

        case emptyDate:
            System.out.println(emptyDate_Text);
            break;

        case emptyTaskType:
            System.out.println(emptyTaskType_Text);
            break;

        default:
            System.out.println(anomaly_Text);
            break;
        }
    }

}
