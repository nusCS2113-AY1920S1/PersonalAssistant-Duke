package sgtravel.commons.exceptions;

public class ApiException extends ParseException {

    public ApiException() {
        super("API failed.");
    }
}
