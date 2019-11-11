package executor;

import duke.exception.DukeException;
import executor.accessors.AccessDeny;
import executor.accessors.Accessor;
import executor.command.Command;
import executor.command.CommandError;
import executor.command.CommandType;
import storage.StorageManager;
import ui.UiCode;
import executor.accessors.AccessType;
import utils.InfoCapsule;

public class Executor {
    private StorageManager storageLayer;

    /**
     * Constructor for 'Executor' Class.
     */
    public Executor(String taskPath, String walletPath) {
        this.storageLayer = new StorageManager(taskPath, walletPath);
    }

    /**
     * Parses the user input and executes the Command specified.
     * @param userInput User input from the CLI
     * @return True if the Command executed calls for an ExitRequest, false otherwise
     */
    public InfoCapsule runCommand(CommandType commandType, String userInput) {
        Command c = createCommand(commandType, userInput);
        c.execute(this.storageLayer);
        return c.getInfoCapsule();
    }

    /**
     * Creates a specific Command sub-Object given the CommandType.
     * @param commandType The type of command to be created
     * @param userInput The input to be initialized with the command
     * @return The generated Command
     */
    public static Command createCommand(CommandType commandType, String userInput) {
        Command c;
        try {
            c = (Command) commandType.getCommandClass().getDeclaredConstructor(String.class).newInstance(
                    userInput);
        } catch (Exception e) {
            c = new CommandError(userInput);
        }
        return c;
    }

    /**
     * Accesses the requested data from the Storage Layer.
     * @param accessType AccessType representing the data to be accessed
     * @param argsStr String any other String arguments
     * @return InfoCapsule containing the result of the request
     */
    public InfoCapsule access(AccessType accessType, String argsStr) {
        Accessor accessor = Executor.createAccessor(accessType);
        accessor.execute(this.storageLayer);
        return accessor.getInfoCapsule();
    }

    /**
     * Creates the Accessor Class given the AccessType.
     * @param accessType AccessType of the Accessor to be created
     * @return Accessor Object
     */
    public static Accessor createAccessor(AccessType accessType) {
        Accessor accessor;
        try {
            accessor = (Accessor) accessType.getAccessClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            accessor = new AccessDeny();
        }
        return accessor;
    }

    /**
     * Saves all Data.
     * @return InfoCapsule detailing any error messages that could occur.
     */
    public InfoCapsule saveAllData() {
        InfoCapsule infoCapsule = new InfoCapsule();
        try {
            this.storageLayer.saveAllData();
        } catch (DukeException e) {
            infoCapsule.setCodeError();
            infoCapsule.setOutputStr(e.getMessage());
            return infoCapsule;
        }
        infoCapsule.setCodeToast();
        infoCapsule.setOutputStr("Saved All Data Succesfully.\n");
        return infoCapsule;
    }

    /**
     * Loads test data for Testers to use.
     * @return InfoCapsule containing the success/failure of the loading.
     */
    public InfoCapsule loadTestData() {
        InfoCapsule infoCapsule = new InfoCapsule();
        try {
            this.storageLayer.loadTestData();
        } catch (DukeException e) {
            infoCapsule.setCodeError();
            infoCapsule.setOutputStr("Error starting Testing Mode");
        }
        infoCapsule.setUiCode(UiCode.TOAST);
        infoCapsule.setOutputStr("Testing Mode Enabled");
        return infoCapsule;
    }
}
