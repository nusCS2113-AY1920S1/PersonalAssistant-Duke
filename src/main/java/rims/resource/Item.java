package rims.resource;

import rims.command.*;
import rims.exception.*;
import rims.core.*;

import java.util.*;
import java.io.*;
import java.text.*;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

// have a list of all possible objects / all objects currently in inventory [Frisbee, Basketball]
// return /item frisbee /qty 3 /user id1

public class Item extends Resource {
    public Item(String name) {
        super(name);
        this.type = 'I';
        // find some way to generate ID
    }

    public Item(String name, int id) {
        super(name, id);
        this.type = 'I';
    }

    public Item(String name, int id, int loanId, String stringDateFrom, String stringDateTill) throws ParseException {
        super(name, id, loanId, stringDateFrom, stringDateTill);
        System.out.println(this.loanId);
        this.type = 'I';
    }
}