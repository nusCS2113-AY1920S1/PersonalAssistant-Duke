package executor.accessors;

import storage.StorageManager;
import ui.UiCode;
import utils.AccessType;

public class AccessDeny extends Accessor {

    public AccessDeny(String argsStr) {
        super();
        this.accessType = AccessType.DENY;
        this.argsStr = argsStr;
    }

    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setCodeError();
        this.infoCapsule.setOutputStr("Access Denied");

    }
}
