package com.algosenpai.app.exceptions;

import java.awt.*;
import java.util.ArrayList;

public class MenuExceptions extends SenpaiExceptions {
    public MenuExceptions(String message) {
        super(message);
    }

    public static void checkInput(ArrayList<String> inputs) throws MenuExceptions {
        if (inputs.size() != 2) {
            throw new MenuExceptions(ErrorMessages.INVALID_MENU_INPUT);
        }
    }
}
