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
    String removeFirstField(String input, String firstField) throws ParserException {
        if ("/exit".equals(firstField) || "/update".equals(firstField)) {
            return input.substring(firstField.length());
        } else if (firstField.length() + SPACE_LENGTH < input.length()) {
            return input.substring(firstField.length() + SPACE_LENGTH);
        } else {
            throw new ParserException("Incomplete command provided");
        }
    }

    /**
     * Modified method which removes the first field from the input string after extracting it
     * with parseFirstField if the command is List.
     *
     * @param input      The input entered by the user.
     * @param firstField The firstField extracted by parseFirstField.
     * @return The string after removing firstField.
     * @throws ParserException if the command is not entered to specific requirements.
     */
    String removeListFirstField(String input, String firstField) throws ParserException {

        if ("/savings".equals(firstField) || "/card".equals(firstField)
                || "/investment".equals(firstField) || "/goals".equals(firstField)) {
            if (input.equals(firstField)) {
                return "";
            } else {
                throw new ParserException("/list " + firstField + " cannot have trailing arguments");
            }
        } else if (firstField.length() + SPACE_LENGTH < input.length()) {
            return input.substring(firstField.length() + SPACE_LENGTH);
        } else {
            throw new ParserException("Incomplete command provided");
        }
    }
}
