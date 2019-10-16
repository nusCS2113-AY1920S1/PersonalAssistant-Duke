package exception;

public class ZeroHistoryRequestException extends WordUpException {
    public ZeroHistoryRequestException() {
        super(" OOPS: Please enter a number more than 0.");
    }
}
