package executor.accessors;

import storage.StorageManager;
import ui.UiCode;
import utils.AccessType;
import utils.InfoCapsule;

public abstract class Accessor {
    protected AccessType accessType;
    protected String argsStr;
    protected InfoCapsule infoCapsule;

    public Accessor() {
        this.accessType = AccessType.DENY;
        this.infoCapsule = new InfoCapsule();
        infoCapsule.setUiCode(UiCode.ERROR);
        infoCapsule.setOutputStr("Command was not executed.\n");
    }

    /**
     * Executes the Accessor Method to Obtain Information for the UI
     * @return InfoCapsule containing the desired information
     */
    public abstract void execute(StorageManager storageManager);

    // Setters & Getters

    public InfoCapsule getInfoCapsule() {
        return this.infoCapsule;
    }

    public AccessType getAccessType() {
        return this.accessType;
    }

    public String getArgsStr() {
        return this.argsStr;
    }
}
