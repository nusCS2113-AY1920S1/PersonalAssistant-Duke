package Commands;

import MovieUI.Controller;

public class SetCommand extends CommandSuper{
    public SetCommand(Controller UIController) {
        super(COMMAND_KEYS.set, CommandStructure.cmdStructure.get(COMMAND_KEYS.set) , UIController);
    }

    @Override
    public void executeCommands() {
        switch (this.getSubRootCommand()){
            case name:
                executeSetName();
                break;
            case age:
                executeSetAge();
                break;
            case preference:
                executeSetPreference();
                break;
            default:
                break;
        }
    }

    private void executeSetName
}
