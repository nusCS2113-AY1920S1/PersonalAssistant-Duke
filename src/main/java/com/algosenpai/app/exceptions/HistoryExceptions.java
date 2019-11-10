//@@author carrieng0323852

package com.algosenpai.app.exceptions;

import com.algosenpai.app.logic.parser.Parser;

import java.util.ArrayList;

public class HistoryExceptions extends SenpaiExceptions {
    private HistoryExceptions(String message) {
        super(message);
    }

    /**
     * To check if the user entered more than one arguments behind the `history` command.
     * @param inputs history command that user entered
     * @throws HistoryExceptions informs the user that input is invalid
     */

    public static void checkInput(ArrayList<String> inputs) throws HistoryExceptions {
        if (inputs.size() == 1) {
            throw new HistoryExceptions(ErrorMessage.INVALID_HISTORY_INPUT);
        } else if (inputs.size() > 2) {
            throw new HistoryExceptions(ErrorMessage.INVALID_TOO_MANY_INPUTS);
        }
    }

    /**
     * To check if the argument entered is an integer greater than 0 and fewer than the number of past commands made.
     * @param historyList list of past commands user entered.
     * @param arg whatever that comes after `history` entered by the user
     * @throws HistoryExceptions informs user the respective error
     */

    public static void checkArgument(ArrayList<String> historyList, String arg) throws HistoryExceptions {
        if (!Parser.isInteger(arg)) {
            throw new HistoryExceptions(ErrorMessage.INVALID_TYPE_OF_ARGUMENT);
        } else {
            int num = Integer.parseInt(arg);
            if (num > historyList.size()) {
                throw new HistoryExceptions(ErrorMessage.INVALID_SIZE_OF_ARGUMENT);
            } else if (num < 1) {
                throw new HistoryExceptions(ErrorMessage.INVALID_NEGATIVE_ARGUMENT);
            }
        }
    }
}
