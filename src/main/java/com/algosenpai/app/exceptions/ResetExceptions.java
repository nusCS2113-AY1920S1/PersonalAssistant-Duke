//@@author carrieng0323852

package com.algosenpai.app.exceptions;

import com.algosenpai.app.stats.UserStats;

public class ResetExceptions extends SenpaiExceptions {
    public ResetExceptions(String message) {
        super(message);
    }

    /**
     * checking if data has already been reset.
     * @param stats user statistics
     * @throws ResetExceptions to inform user that his data has been reset already
     */

    public static void checkResetStatus(UserStats stats) throws ResetExceptions {
        int level = stats.getUserLevel();
        int exp = stats.getUserExp();

        if (level == 1 && exp == 0) {
            throw new ResetExceptions(ErrorMessage.INVALID_HAS_BEEN_RESET);
        }
    }
}
