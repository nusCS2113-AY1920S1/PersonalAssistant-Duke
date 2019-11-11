package owlmoney.logic.parser;

import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the third layer of parsing for raw data after removing command and type.
 * This forms the baseline raw data parsing that specific data level parsers can extend from.
 */
public class ParseRawData {
    private static final int NEXT_INDEX = 1;
    private static final int PREVIOUS_INDEX = -1;

    /**
     * Extracts specific keyword values.
     *
     * @param data        The raw data that requires parsing to extract keyword values.
     * @param keyword     The keyword that we want to extract.
     * @param keywordList The list of keywords that are available in the command.
     * @return The extracted keyword value.
     * @throws ParserException if duplicated keywords are detected.
     */
    public String extractParameter(String data, String keyword, String[] keywordList) throws ParserException {
        String[] splitTypeSpecificArguments = data.split(" ");
        String parameter = "";
        checkDuplicateKeywords(splitTypeSpecificArguments, keywordList);

        int startIndex = findStartIndex(splitTypeSpecificArguments, keyword);
        if (startIndex >= 0) {
            int endIndex = findEndIndex(startIndex, splitTypeSpecificArguments, keywordList);
            parameter = concatenateParameter(startIndex, endIndex, splitTypeSpecificArguments);
        }
        return parameter;
    }

    /**
     * Check the user entered data from the command for duplicated keywords.
     *
     * @param splitArray    The array of the data that has been split out from the user command.
     * @param keywordList   The list of all available keywords for a particular command.
     * @throws ParserException  If duplicated keyword is found.
     */
    private void checkDuplicateKeywords(String[] splitArray, String[] keywordList) throws ParserException {
        for (int i = 0; i < keywordList.length; i++) {
            checkEachKeyword(keywordList[i], splitArray);
        }
    }

    /**
     * Throws ParserException if duplicated keyword is found.
     *
     * @param keyword       The keyword to be checked for duplicates.
     * @param splitArray    The array of the data that has been split out from the user command.
     * @throws ParserException  If duplicated keyword is found.
     */
    private void checkEachKeyword(String keyword, String[] splitArray) throws ParserException {
        int keywordCounter = 0;
        for (int i = 0; i < splitArray.length; i++) {
            if (splitArray[i].equals(keyword)) {
                keywordCounter++;
            }
            if (keywordCounter > 1) {
                throw new ParserException("Duplicate " + keyword + " found!");
            }
        }
    }

    /**
     * Get the starting index number of the keyword in the user entered data.
     *
     * @param splitTypeSpecificArguments    The user entered data.
     * @param keyword   The keyword to get the starting index of.
     * @return  The starting index number of the keyword in the user entered data.
     */
    private int findStartIndex(String[] splitTypeSpecificArguments, String keyword) {
        for (int i = 0; i < splitTypeSpecificArguments.length; i++) {
            if (splitTypeSpecificArguments[i].equals(keyword)) {
                return i;
            }
        }
        return PREVIOUS_INDEX;
    }

    /**
     * Get the ending index number of the last keyword parameter in the user entered data.
     *
     * @param startIndex    The start index of the keyword.
     * @param splitTypeSpecificArguments    The user entered data.
     * @param keywordList   The list of all available keywords for a particular command.
     * @return  The ending index number of the last keyword parameter in the user entered data.
     */
    private int findEndIndex(int startIndex, String[] splitTypeSpecificArguments, String[] keywordList) {
        for (int i = startIndex + NEXT_INDEX; i < splitTypeSpecificArguments.length; i++) {
            for (int k = 0; k < keywordList.length; k++) {
                if (splitTypeSpecificArguments[i].equals(keywordList[k])) {
                    return i;
                }
            }
        }
        return splitTypeSpecificArguments.length;
    }

    /**
     * Concatenates the string array of parameters belonging to the same keyword.
     *
     * @param startIndex    The start index of the keyword.
     * @param endIndex      The end index of the last keyword parameter.
     * @param splitTypeSpecificArguments    The array of keywords and it's parameters.
     * @return  Concatenated string of the parameters of the keyword.
     */
    private String concatenateParameter(int startIndex, int endIndex, String[] splitTypeSpecificArguments) {
        StringBuilder individualParameter = new StringBuilder();
        for (int i = startIndex + NEXT_INDEX; i < endIndex; i++) {
            if (individualParameter.toString().isBlank()) {
                individualParameter.append(splitTypeSpecificArguments[i]);
            } else {
                individualParameter.append(" ").append(splitTypeSpecificArguments[i]);
            }
        }
        return individualParameter.toString();
    }

}
