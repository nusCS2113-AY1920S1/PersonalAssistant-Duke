package optix.ui;

/**
 * Reads in the input user and
 * returns the response by Optix.
 */
public class Ui {

    /**
     * Stores the appropriate response based on users input command.
     * Once user command is processed, Optix's response is stored using
     * setMessage method to store the appropriate message. Use
     * by showCommandLine method to print out the response.
     */
    private String message;

    private static final String MESSAGE_GREET = "Hello! I'm Optix\n"
            + "What can I do for you?\n";


    public Ui() {
        this.message = MESSAGE_GREET;
    }

    /**
     * Set appropriate response by Optix to be shown based on user's input.
     *
     * @param message String Optix will respond with based on user input.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
