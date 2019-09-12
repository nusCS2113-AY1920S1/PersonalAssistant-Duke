package Model_Classes;

import CustomExceptions.DukeException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Parser {
    private Scanner scanner = new Scanner(System.in);
    public Parser() {
    }
    public String getCommand() {
        String command = scanner.next().toLowerCase();
        return command;
    }
    public Integer getIndex() {
        String temp = scanner.nextLine().trim();
        int index = Integer.parseInt(temp);
        return index;
    }
    public String getDescription() throws DukeException {
        String temp = scanner.nextLine().trim();
        if (temp.equals("")) {
            throw new DukeException();
        }
        return temp;
    }
    public String[] getDescriptionWithDate() {
        String[] array = scanner.nextLine().trim().split("/", 2);
        return array;
    }
    public Date formatDate(String by) throws DukeException{
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(by);
        } catch (ParseException e) {
            throw new DukeException();
        }
    }
    public String getKey() {
        String key = scanner.nextLine().toLowerCase();
        return key;
    }
}
