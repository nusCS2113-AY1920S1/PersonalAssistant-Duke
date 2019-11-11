package chronologer.command;

//@@author fauzt
/**
 * Handles building a message based on several different strings from the command.
 *
 * @author Fauzan Adipratama
 * @version 1.4
 */
public class MessageBuilder {

    MessageBuilder() {
        message = new StringBuilder();
    }

    private StringBuilder message;

    /**
     * Initialises the string builder to an empty string and await for incoming string.
     */
    void initialiseMessage() {
        message = new StringBuilder();
    }

    /**
     * Appends the incoming string to the existing string.
     * @param output is the incoming string
     */
    void loadMessage(String output) {
        message.append(output);
    }

    /**
     * Retrieves the message at its current state.
     * @return the message to be outputted
     */
    public String getMessage() {
        return message.toString();
    }
}
