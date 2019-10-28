package Parser;

import Commands.Command;


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
}
