package CustomExceptions;

import Enums.ExceptionType;

public class DukeException extends Exception {
    private String anomaly_Text = "Anomaly Detected";
    private String emptylist_Text = "List is empty";

    public DukeException(){
     System.out.println();
    }

    public DukeException(ExceptionType type){
        switch(type) {
            case emptylist:
                System.out.println(emptylist_Text);

            default:
                System.out.println(anomaly_Text);
                break;
        }
    }
}
