package com.algosenpai.app.exceptions;

import com.algosenpai.app.logic.constant.Commands;

import java.util.ArrayList;

public class HistoryExceptions extends SenpaiExceptions {
    public HistoryExceptions (String message) {
        super(message);
    }

    public static void checkInput(ArrayList<String> historyList) throws HistoryExceptions {
        if (historyList.size() != 2) {
            throw new HistoryExceptions(ErrorMessages.INVALID_HISTORY_INPUT);
        }
    }

    public static void checkArgument(ArrayList<String> historyList, String arg) throws HistoryExceptions {
        if (!Commands.isInteger(arg)) {
            throw new HistoryExceptions(ErrorMessages.INVALID_TYPE_OF_ARGUMENT);
        } else {
            int num = Integer.parseInt(arg);
            if (num > historyList.size()) {
                throw new HistoryExceptions(ErrorMessages.INVALID_SIZE_OF_ARGUMENT);
            }
        }
    }
}
