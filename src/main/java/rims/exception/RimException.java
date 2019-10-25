package rims.exception;

import rims.command.*;
import rims.resource.*;
import rims.core.*;

public class RimException extends Exception {
    public RimException(String error){
        super(error);
    }
}