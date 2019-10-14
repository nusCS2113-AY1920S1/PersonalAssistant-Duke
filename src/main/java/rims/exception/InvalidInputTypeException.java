package rims.exception;

import rims.command.*;
import rims.resource.*;
import rims.core.*;

public class InvalidInputTypeException extends RimException {
    InvalidInputTypeException(String error){
        super(error);
    }
}