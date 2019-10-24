package CustomExceptions;

import Enums.ExceptionType;

public class RoomShareException extends Exception {
    private static final String outOfBounds_Text = "Index is out of Bounds!";
    private static final String anomaly_Text = "Anomaly Detected";
    private static final String emptyList_Text = "List is empty";
    private static final String timeClash_Text = "Time Clash Detected";
    private static final String wrongFormat_Text = "Wrong Format Detected";
    private static final String wrongPriority_Text = "You've entered wrong format of priority";
    private static final String subTask_Text = "Meetings do not support Subtasks";
    public static final String wrongTaskType_Text = "Only meeting or assignment tag are accepted";
    public static final String emptyDescription_Text = "You haven't included the description of you task";
    public static final String emptyDate_Text = "You haven't included the date of your task";
    public static final String emptyUser_Text = "You haven't included the user of your task";
    public static final String emptyTaskType_Text = "You haven't specified the type of your task: assignment or meeting";
    public static final String writeError_Text = "Error in writing file, cancelling write process...";
    public static final String wrongIndexFormat_Text = "The index you've enter is in the wrong format";
    public static final String wrongTimeFormat_Text = "You've entered an invalid time format";


    private String message;
    /**
     * Constructor for DukeException Exception
     * Takes in the exception type thrown and prints out the specific error message
     * @param type type of exception detected
     */
    public RoomShareException(ExceptionType type){
        switch(type) {

        case emptyUser:
            message = emptyUser_Text;
            break;

        case emptyList:
            message = emptyList_Text;
            break;

        case writeError:
            message = writeError_Text;
            break;

        case wrongIndexFormat:
            message = wrongIndexFormat_Text;
            break;

        case wrongTimeFormat:
            message = wrongTimeFormat_Text;
            break;

        case timeClash:
            message = timeClash_Text;
            break;

        case wrongFormat:
            message = wrongFormat_Text;
            break;

        case outOfBounds:
            message = outOfBounds_Text;
            break;

        case wrongPriority:
            message = wrongPriority_Text;
            break;

        case subTask:
            message = subTask_Text;
            break;

        case wrongTaskType:
            message = wrongTaskType_Text;
            break;

        case emptyDescription:
            message = emptyDescription_Text;
            break;

        case emptyDate:
            message = emptyDate_Text;
            break;

        case emptyTaskType:
            message = emptyTaskType_Text;
            break;

        case others:
            break;

        default:
            message = anomaly_Text;
            break;
        }
    }

    /**
     * toString() method returning the message of the Exception
     * @return the message of the Exception
     */
    @Override
    public String toString(){
        return message;
    }
}
