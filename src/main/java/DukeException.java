import  java.lang.Exception;
import java.lang.StringIndexOutOfBoundsException;

public class DukeException extends Exception {
    public DukeException (String message) {
        System.out.println(message);
    }
}
