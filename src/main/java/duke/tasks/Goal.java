package duke.tasks;

import duke.Duke;
import duke.autocorrect.Autocorrect;
import duke.exceptions.DukeException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Goal {
    private SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy");
    private String date;
    private HashMap<String, Integer> nutritionValue = new HashMap<String, Integer>();

    public Goal(String date, String details, Autocorrect autocorrect) throws DukeException {
        try {
            Date day;
            day = dateparser.parse(date);
            this.date = dateparser.format(day);
        } catch (Exception e) {
            throw new DukeException("It appears an invalid date has been entered");
        }
        if (details.trim().length() != 0) {
            String[] splitString1 = details.split("/");
            for (String data : splitString1) {
                if (data.trim().length() != 0) {
                    String[] partitionedData = data.split(" ", 2);
                    autocorrect.setWord(partitionedData[0]);
                    autocorrect.execute();
                    String nutrient = autocorrect.getWord();
                    int value = Integer.valueOf(partitionedData[1].trim());
                    nutritionValue.put(nutrient, value);
                }
            }
        }
    }

    public Goal(String date, String[] details) throws DukeException {
        try {
            Date day;
            day = dateparser.parse(date);
            this.date = dateparser.format(day);
        } catch (Exception e) {
            throw new DukeException("It appears the previous save file has an invalid date");
        }
        for (int i = 0; i < details.length; i += 2) {
            nutritionValue.put(details[i], Integer.valueOf(details[i + 1]));
        }
    }

}
