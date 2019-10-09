package exception;

public class ZeroHistoryRequestException extends WrongFormatException {
    public ZeroHistoryRequestException() {
        super("     ☹ OOPS: Please enter a number more than 0.");
    }
}
