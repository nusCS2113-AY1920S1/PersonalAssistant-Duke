package RemindTest;

import Commons.Storage;

public class StorageStub extends Storage {

    private ReminderStub reminderStub = new ReminderStub();

    public ReminderStub getReminderObject() {
        return reminderStub;
    }
}
