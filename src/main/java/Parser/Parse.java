package Parser;

import Commands.Command;

/**
 * Abstract class Parse with methods representing all the Command subclasses to be
 * carried out when an input is entered by the user.
 */
public abstract class Parse {
    public abstract Command parse() throws Exception;

    /**
     * This method checks if user input mod code actually fits the characteristic of a mod code
     * @param string The string of mod code
     * @return true if it matches the characteristics of a mod code.
     */
    public boolean isModCode(String string) {
        if (string.length() < 6){
            return  false;
        } else if (string.substring(0, 1).matches("\\w+") && string.substring(2, 5).matches("\\d+")) {
            return true;
        } else if (string.substring(0, 2).matches("\\w+") && string.substring(3, 6).matches("\\d+")) {
            return true;
        } else {
            return false;
        }
    }
}
