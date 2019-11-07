package executor;

import duke.exception.DukeException;
import executor.accessors.AccessDeny;
import executor.accessors.Accessor;
import executor.command.Command;
import executor.command.CommandError;
import executor.command.CommandType;
import storage.StorageManager;
import utils.AccessType;
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

    public InfoCapsule access(AccessType accessType, String argsStr) {
        Accessor accessor = Executor.createAccessor(accessType, argsStr);
        accessor.execute(this.storageLayer);
        return accessor.getInfoCapsule();
    }

    public static Accessor createAccessor(AccessType accessType, String argsStr) {
        Accessor accessor;
        try {
            accessor = (Accessor) accessType.getAccessClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            accessor = new AccessDeny(argsStr);
        }
        return accessor;
    }

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
}
