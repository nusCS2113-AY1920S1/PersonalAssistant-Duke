package commands;

import MovieUI.Controller;
import MovieUI.MovieHandler;

import java.util.Map;

public class HelpCommand extends CommandSuper {
    public HelpCommand(Controller uicontroller) {
        super(COMMANDKEYS.help, CommandStructure.cmdStructure.get(COMMANDKEYS.help), uicontroller);
    }

    @Override
    public void executeCommands() {
        //TODO Display help options
        ((MovieHandler) this.getUIController()).setFeedbackText(getHelp());
    }

    private String getHelp() {
        if (getPayload() == "") {
            return "Try one of the following commands : \n" + CommandStructure.AllRoots.toString();
        }
        String printer = "";
        for (Map.Entry<COMMANDKEYS, COMMANDKEYS[]> entry : CommandStructure.cmdStructure.entrySet()) {
            if (entry.getKey() == this.getSubRootCommand()) {
                for (COMMANDKEYS c: entry.getValue()) {
                    printer += ("\n\t" + c.toString() + " " + CommandStructure.cmdHelp.get(getSubRootCommand()));
                }
            }
        }

        return this.getSubRootCommand().toString() + printer;
    }


}