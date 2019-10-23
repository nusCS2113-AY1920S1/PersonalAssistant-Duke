package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

public class UnknownCommand extends Command {
    private String input;
    public UnknownCommand(String input){
        this.input=input;
    }
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {
        ui.ErrorPrint("Sorry, RIM does not support:" + '<' + input + ">" + " as a command yet.");
        ui.Home();
    }  
}