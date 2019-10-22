package parsers;

import core.Ui;
import members.Member;
import utils.DukeException;

import tasks.ToDo;
import tasks.Event;
import tasks.Deadline;
import tasks.Last;
import tasks.Period;
import tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DataParser {
    /**
     * <p>parse a line in the data text to an object.</p>
     *
     * @param line a line of String to be parsed, without \n last
     * @throws ParseException if the line cannot be parsed properly
     */
    public static Task taskDataLine(String line) throws ParseException {
        String[] splites = line.split(" \\| ");
        if (splites.length < 3 || (splites.length < 2 && (splites[0].equals("E") || splites[0].equals("D")))) {
            throw new ParseException("Invalid Duke data line, the information is incomplete.", -1);
        }
        Task temp;
        if (splites[0].equals("T")) {
            temp = new ToDo();
        } else if (splites[0].equals("E")) {
            temp = new Event();
        } else if (splites[0].equals("D")) {
            temp = new Deadline();
        } else if (splites[0].equals("L")) {
            temp = new Last();
        } else if (splites[0].equals("P")) {
            temp = new Period();
        } else {
            throw new ParseException(
                    "Invalid data line input: the first character is not T, E, D, L or P,"
                            + " which cannot represent any task type Duke support.", -1);
        }
        try {
            if (Integer.parseInt(splites[1]) != 0) {
                temp.markAsDone();
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid number format for the second column of Duke data line.", -1);
        } catch (DukeException e) {
            Ui.print(e.getMessage());
        }
        temp.setDescription(splites[2]);
        try {
            temp.addPrecondition(splites[3]);
        } catch (DukeException e) {
            throw new ParseException(e.getMessage(), -1);
        }
        if (splites[0].equals("E") || splites[0].equals("D")) {
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                temp.setTime(ft.parse(splites[4]));
            } catch (ParseException e) {
                throw e;
            }
        } else if (splites[0].equals("L")) {
            temp.setDuration(splites[4]);
        } else if (splites[0].equals("P")) {
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                temp.setStart(ft.parse(splites[4]));
                temp.setEnd(ft.parse(splites[5]));
            } catch (ParseException e) {
                throw e;
            }
        }
        return temp;
    }

    /**
     * <p>parse a line in the data text to an object.</p>
     *
     * @param line a line of String to be parsed, without \n last
     * @return a Member. Member object produced by the input line
     */
    public static Member memberDataLine(String line) {
        String[] splites = line.split(" \\| ");
        String name = splites[0].trim();
        int numOfTaskInCharge = splites.length - 1;
        ArrayList<Integer> taskInCharge = new ArrayList<>();
        for (int i = 0; i < numOfTaskInCharge; i++) {
            taskInCharge.add(Integer.parseInt(splites[1 + i].trim()));
        }
        return new Member(name, taskInCharge);
    }

}
