package executor.accessors;

import storage.StorageManager;
import ui.UiCode;

public class AccessTaskList extends Accessor {

    /**
     * Constructor for AccessTaskList Class.
     */
    public AccessTaskList() {
        super();
        this.accessType = AccessType.TASKLIST;
    }

    @Override
    public void execute(StorageManager storageManager) {
        infoCapsule.setUiCode(UiCode.UPDATE);
        infoCapsule.setTaskList(storageManager.getTaskList());
    }
}
