package com.algosenpai.app.exceptions;

import java.util.ArrayList;

public class MenuExceptions extends SenpaiExceptions {
    public MenuExceptions(String message) {
        super(message);
    }

    /**
     * Checks if user inputs an invalid menu eg menu pri nt.
     * @param inputs user's input
     * @throws MenuExceptions informs user that there is an error
     */

    public static void checkInput(ArrayList<String> inputs) throws MenuExceptions {
        if (inputs.size() != 2) {
            throw new MenuExceptions(ErrorMessage.INVALID_MENU_INPUT);
        }
    }
}
