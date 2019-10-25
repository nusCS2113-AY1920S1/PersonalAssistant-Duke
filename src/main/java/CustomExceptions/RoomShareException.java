package CustomExceptions;

import Enums.ExceptionType;

public class RoomShareException extends Exception {
    private static final String OUT_OF_BOUNDS_TEXT = "Index is out of Bounds!";
    private static final String ANOMALY_TEXT = "Anomaly Detected";
    private static final String EMPTY_LIST_TEXT = "List is empty";
    private static final String TIME_CLASH_TEXT = "Time Clash Detected";
    private static final String WRONG_FORMAT_TEXT = "Wrong Format Detected";
    private static final String WRONG_PRIORITY_TEXT = "You've entered wrong format of priority";
    private static final String SUB_TASK_TEXT = "Meetings do not support Subtasks";
    public static final String WRONG_TASK_TYPE_TEXT = "Only meeting or assignment tag are accepted";
    public static final String EMPTY_DESCRIPTION_TEXT = "You haven't included the description of you task";
    public static final String EMPTY_DATE_TEXT = "You haven't included the date of your task";
    public static final String EMPTY_USER_TEXT = "You haven't included the user of your task";
    public static final String EMPTY_TASK_TYPE_TEXT = "You haven't specified the type of your task: assignment or meeting";
    public static final String WRITE_ERROR_TEXT = "Error in writing file, cancelling write process...";
    public static final String WRONG_INDEX_FORMAT_TEXT = "The index you've enter is in the wrong format";
    public static final String WRONG_TIME_FORMAT_TEXT = "You've entered an invalid time format";


    private String message;
    /**
     * Constructor for DukeException Exception
     * Takes in the exception type thrown and prints out the specific error message
     * @param type type of exception detected
     */
    public RoomShareException(ExceptionType type){
        switch(type) {

        case emptyUser:
            message = EMPTY_USER_TEXT;
            break;

        case emptyList:
            message = EMPTY_LIST_TEXT;
            break;

        case writeError:
            message = WRITE_ERROR_TEXT;
            break;

        case wrongIndexFormat:
            message = WRONG_INDEX_FORMAT_TEXT;
            break;

        case wrongTimeFormat:
            message = WRONG_TIME_FORMAT_TEXT;
            break;

        case timeClash:
            message = TIME_CLASH_TEXT;
            break;

        case wrongFormat:
            message = WRONG_FORMAT_TEXT;
            break;

        case outOfBounds:
            message = OUT_OF_BOUNDS_TEXT;
            break;

        case wrongPriority:
            message = WRONG_PRIORITY_TEXT;
            break;

        case subTask:
            message = SUB_TASK_TEXT;
            break;

        case wrongTaskType:
            message = WRONG_TASK_TYPE_TEXT;
            break;

        case emptyDescription:
            message = EMPTY_DESCRIPTION_TEXT;
            break;

        case emptyDate:
            message = EMPTY_DATE_TEXT;
            break;

        case emptyTaskType:
            message = EMPTY_TASK_TYPE_TEXT;
            break;

        case others:
            break;

        default:
            message = ANOMALY_TEXT;
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
