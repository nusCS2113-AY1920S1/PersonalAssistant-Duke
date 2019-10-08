package owlmoney.logic.parser;

import owlmoney.logic.parser.exception.ParserException;

/**
 * Parser class which provides methods that more specific parser classes will require.
 */

abstract class Parser {

    private static final int SPACE_LENGTH = 1;

    /**
     * Extracts the first field of the string based on the first space detected.
     *
     * @param input The input entered by the user.
     * @return The first element of the input string.
     */
    String parseFirstField(String input) {
        String[] inputSplit = input.split(" ", 2);
        return inputSplit[0];
    }

    /**
     * Removes the first field from the input string after extracting it with parseFirstField.
     *
     * @param input      The input entered by the user.
     * @param firstField The firstField extracted by parseFirstField.
     * @return The string after removing firstField.
     * @throws ParserException if the command is not entered to specific requirements.
     */
    //added /test to list all banks for testing
    String removeFirstField(String input, String firstField) throws ParserException {
        if (firstField.equals("/exit")) {
            return input.substring(firstField.length());
        } else if (firstField.length() + SPACE_LENGTH < input.length()) {
            return input.substring(firstField.length() + SPACE_LENGTH);
        } else {
            throw new ParserException("Incomplete command provided");
        }
    }

    String removeListFirstField(String input, String firstField) throws ParserException {
        if (firstField.equals("/savings")) {
            return "";
        } else if (firstField.length() + SPACE_LENGTH < input.length()) {
            return input.substring(firstField.length() + SPACE_LENGTH);
        } else {
            throw new ParserException("Incomplete command provided");
        }
    }
}
