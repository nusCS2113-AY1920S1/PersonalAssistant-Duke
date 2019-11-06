package chronologer.ui;

public class MessageBuilder {

    private static StringBuilder message;

    public static void initialiseMessage() {
        message = new StringBuilder();
    }

    public static void loadMessage(String output) {
        message.append(output);
    }

    public static String getMessage() {
        return message.toString();
    }
}
