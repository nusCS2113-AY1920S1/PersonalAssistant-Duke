package CustomExceptions;

import Enums.ExceptionType;

public class DukeException extends Exception {
    public String anomaly = "Anomaly Detected\n";

    public DukeException(){
     System.out.println();
    }

    public DukeException(ExceptionType type){
        switch(type) {
            default:
                System.out.println(anomaly);
                break;
        }
    }
}
