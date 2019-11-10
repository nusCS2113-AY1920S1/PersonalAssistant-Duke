//@@author carrieng0323852

package com.algosenpai.app.exceptions;

class ErrorMessage {
    //menu
    static final String INVALID_MENU_INPUT = "OOPS!!! Error occurred. Please enter the command which you'd "
            + "like to view in the following format: menu print\n"
            + "Otherwise, enter `menu` to view "
            + "the list of commands that we have";
    //history
    static final String INVALID_HISTORY_INPUT = "OOPS!!! Error occurred. Please key in the "
            + "number of commands you'd like to view in the "
            + "following format: e.g history 5";
    static final String INVALID_TOO_MANY_INPUTS = "OOPS!!! Error occurred. Too many inputs entered!";
    static final String INVALID_TYPE_OF_ARGUMENT = "OOPS!!! Error occurred. "
            + "Please key in a valid number of commands "
            + "you'd like to view!";
    static final String INVALID_SIZE_OF_ARGUMENT = "OOPS!!! Error occurred. "
            + "You don't have that many past commands!";
    static final String INVALID_NEGATIVE_ARGUMENT = "OOPS!!! Error occurred. "
            + "Please key in a valid number of commands "
            + "you'd like to view!";
    //reset
    static final String INVALID_HAS_BEEN_RESET = "OOPS!!! Error occurred. Your data has already been reset.";
}