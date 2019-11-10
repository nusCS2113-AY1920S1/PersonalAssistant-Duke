package com.algosenpai.app.exceptions;

/**
 * Thrown when the file contains invalid or corrupted data.
 * The program can choose to show an error message, etc.
 */
public class FileParsingException extends SenpaiExceptions {

    private static final String ERROR_MSG = "User Data file is corrupted";

    public FileParsingException() {
        super(ERROR_MSG);
    }

    public FileParsingException(String message) {
        super(message);
    }
}
