package Commands;

import MovieUI.Controller;
import MovieUI.MovieHandler;
import task.Tasks;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class HelpCommand extends CommandSuper{
    public HelpCommand(Controller UIController) {
        super(COMMAND_KEYS.help, CommandStructure.cmdStructure.get(COMMAND_KEYS.help) ,  UIController);
    }

    @Override
    public void executeCommands() {
        //TODO Display help options
        System.out.println("Send Help please");
        ((MovieHandler)this.getUIController()).setFeedbackText(getHelp());
    }

    private String getHelp() {
        String printer = "";
        for(Map.Entry<COMMAND_KEYS, COMMAND_KEYS[]> entry : CommandStructure.cmdStructure.entrySet()){
            if(entry.getKey() == this.getSubRootCommand()) {
                for(COMMAND_KEYS c: entry.getValue() ){
                    printer += ("\n\t" + c.toString() + " : Do for fun lol");
                }
            }
        }

        return this.getSubRootCommand().toString() + printer;
    }




}
