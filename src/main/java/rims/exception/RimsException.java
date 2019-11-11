package rims.exception;

//@@author rabhijit
/**
 * A custom exception for RIMS, when inputs don't fit the desired format or have
 * no meaning.
 */
public class RimsException extends Exception {
    protected String tab = "\t";
    protected String line = "______________________________________________"
            + "______________________________________________________________________________________________";
    protected String hash = "******************************************************"
            + "**************************************************************************************";
    protected String error;

    /**
     * Constructor for a RimsException. Every RimsException contains an error
     * message.
     *
     * @param error the error message of this RimsException.
     */
    public RimsException(String error) {
        this.error = error;
    }

    /**
     * Prints the error message in the standard RIMS format.
     */
    public void displayError() {
        System.out.println(tab + hash);
        System.out.println(tab + "ERROR: " + error);
        System.out.println(tab + line + "\n");
        System.out.println(tab + "Please re-type the command with the necessary changes, "
                + "or type in any other command.");
        System.out.println(tab + hash);
    }

    /**
     * Returns the error message.
     */
    @Override
    public String getMessage() {
        return this.error;
    }
}