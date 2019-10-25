package rims.exception;

import rims.command.*;
import rims.resource.*;
import rims.core.*;

public class InvalidInputException extends RimException {
    InvalidInputException(String error){
        super(error);
    }
}