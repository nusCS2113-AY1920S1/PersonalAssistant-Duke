package chronologer.ui;

public class MessageBuilder {

    private static StringBuilder message;

    /**
     * Initialises the string builder to an empty string and await for incoming string.
     */
    public static void initialiseMessage() {
        message = new StringBuilder();
    }

    /**
     * Appends the incoming string to the existing string.
     * @param output is the incoming string
     */
    public static void loadMessage(String output) {
        message.append(output);
    }

    /**
     * Retrieves the message at its current state.
     * @return the message to be outputted
     */
    public static String getMessage() {
        return message.toString();
    }
}
