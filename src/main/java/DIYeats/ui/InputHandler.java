package DIYeats.ui;

import DIYeats.commons.exceptions.DukeException;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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

    public String getString() throws DukeException {
        try {
            String string = in.nextLine();
            return string;
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    public int getInt() throws DukeException {
        try {
            int value = Integer.parseInt(in.nextLine());
            return value;
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
    }

    public double getDouble() throws DukeException {
        try {
            double value = Double.parseDouble(in.nextLine());
            return value;
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
    }

    public String getDate() throws DukeException {
        SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy");
        String date;
        try {
            Date day;
            day = dateparser.parse(in.nextLine());
            date = dateparser.format(day);
            return date;
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    public boolean getApproval() throws DukeException {
        String userInput = getString();
        if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
            return true;
        } else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
            return false;
        } else {
            throw new DukeException("Sorry, but DIYeats was unable to understand your command");
        }
    }


}
