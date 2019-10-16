package rims.resource;

import rims.command.*;
import rims.exception.*;
import rims.core.*;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public class Room extends Resource {
    public Room(String name) {
        super(name);
        this.type = 'R';
        // find some way to generate ID
    }

    public Room(String name, int id) {
        super(name, id);
        this.type = 'R';
    }

    public Room(String name, int id, int loanId, String stringDateFrom, String stringDateTill) throws ParseException {
        super(name, id, loanId, stringDateFrom, stringDateTill);
        this.type = 'R';
    }
}