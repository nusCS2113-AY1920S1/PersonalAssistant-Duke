package diyeats.ui;

import diyeats.commons.exceptions.ProgramException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//@@author

public class InputHandler {
    private Scanner in;

    public String getString() throws ProgramException {
        try {
            String string = in.nextLine();
            return string;
        } catch (Exception e) {
            throw new ProgramException(e.getMessage());
        }
    }

    public int getInt() throws ProgramException {
        try {
            int value = Integer.parseInt(in.nextLine());
            return value;
        } catch (NumberFormatException e) {
            throw new ProgramException(e.getMessage());
        }
    }

    public String getDate() throws ProgramException {
        SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy");
        String date;
        try {
            Date day;
            day = dateparser.parse(in.nextLine());
            date = dateparser.format(day);
            return date;
        } catch (Exception e) {
            throw new ProgramException(e.getMessage());
        }
    }
}
