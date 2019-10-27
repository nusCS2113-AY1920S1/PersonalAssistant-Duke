package Parser;

import Commands.Command;
import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;

public abstract class Parse {
    public abstract Command execute() throws Exception;
    public boolean isModCode(String str) {
        if (str.length() < 6){
            return  false;
        } else if (str.substring(0, 1).matches("\\w+") && str.substring(2, 5).matches("\\d+")) {
            return true;
        } else if (str.substring(0, 2).matches("\\w+") && str.substring(3, 6).matches("\\d+")) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
