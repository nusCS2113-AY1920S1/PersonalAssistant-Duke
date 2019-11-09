package diyeats.ui;

import diyeats.commons.exceptions.ProgramException;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//@@author

public class InputHandler {
    private Scanner in;

    public InputHandler(InputStream inputStream) {
        this.in = new Scanner(inputStream);
    }

    public InputHandler(String inputStream) {
        if (inputStream != null) {
            this.in = new Scanner(inputStream);
        } else {
            this.in = new Scanner("");
        }
    }

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

    public double getDouble() throws ProgramException {
        try {
            double value = Double.parseDouble(in.nextLine());
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

    public boolean getApproval() throws ProgramException {
        String userInput = getString();
        if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
            return true;
        } else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
            return false;
        } else {
            throw new ProgramException("Sorry, but DIYeats was unable to understand your command");
        }
    }


}
