package rims.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import rims.exception.RimsException;

//author aarushisingh1

public class Tag extends Resource{

    /**
     * Constructor for a new Tag for item with no reservations
     */
    public Tag(int resourceId, String name, String tagName, String type) {
        super(resourceId, name, tagName, type);
    }

    /**
     * Constructor for a new Tag for item with reservations
     */
    public Tag(int resourceId, String name, ReservationList reservations, String tagName, String type) {
        super(resourceId, name, reservations, tagName, type);
    }
}
