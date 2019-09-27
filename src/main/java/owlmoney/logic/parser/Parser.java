package owlmoney.logic.parser;

import owlmoney.logic.parser.exception.ParserException;

abstract class Parser {

    private static final int SPACE_LENGTH = 1;

    String parseFirstField(String input) {
        String [] inputSplit = input.split(" ",2);
        return inputSplit[0];
    }

    String removeFirstField(String input, String firstField) throws ParserException {
        if(firstField.equals("/exit")) {
            return input.substring(firstField.length());
        } else if(firstField.length() + SPACE_LENGTH < input.length()) {
            return input.substring(firstField.length() + SPACE_LENGTH);
        } else {
            throw new ParserException("Incomplete command provided");
        }
    }
}
