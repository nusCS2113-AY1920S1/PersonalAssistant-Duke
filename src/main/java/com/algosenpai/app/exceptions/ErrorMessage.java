package com.algosenpai.app.exceptions;

public class ErrorMessage {
    public static final String INVALID_MENU_INPUT = "OOPS!!! Error occurred. Please enter the command which you'd "
                                                    + "like to view in the following format: menu print\n"
                                                    + "Otherwise, enter `menu` to view "
                                                    + "the list of commands that we have";
    public static final String INVALID_HISTORY_INPUT = "OOPS!!! Error occurred. Please key in the "
                                                       + "number of commands you'd like to view in the "
                                                       + "following format: e.g history 5";
    public static final String INVALID_TOO_MANY_INPUTS = "OOPS!!! Error occurred. Too many inputs entered!";
    public static final String INVALID_TYPE_OF_ARGUMENT = "OOPS!!! Error occurred. "
                                                          + "Please key in a valid number of commands "
                                                          + "you'd like to view!";
    public static final String INVALID_SIZE_OF_ARGUMENT = "OOPS!!! Error occurred. "
                                                          + "You don't have that many past commands!";
    public static final String INVALID_NEGATIVE_ARGUMENT = "OOPS!!! Error occurred. "
                                                           + "Please key in a valid number of commands "
                                                           + "you'd like to view!";
}
