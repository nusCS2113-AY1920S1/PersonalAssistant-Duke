package compal.logic.commands;

import compal.main.Duke;

public abstract class Command {
    public String cmdString;
    public Duke duke;
    public Command(Duke d){
        this.duke = d;
    }


}
