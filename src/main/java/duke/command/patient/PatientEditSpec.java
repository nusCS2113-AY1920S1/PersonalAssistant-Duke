package duke.command.patient;

import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;

public class PatientEditSpec extends ArgSpec {
    private static final PatientEditSpec spec = new PatientEditSpec();

    public static PatientEditSpec getSpec() {
        return spec;
    }

    private PatientEditSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                // TODO: Changes need to be made to Patient class. Update section in User Guide
                // new Switch("name", String.class, true, ArgLevel.REQUIRED, "n"),
                // new Switch("bed", String.class, true, ArgLevel.OPTIONAL, "b"),
                new Switch("allergies", String.class, true, ArgLevel.REQUIRED, "a",
                        "allergy"),
                new Switch("height", Integer.class, true, ArgLevel.REQUIRED, "h"),
                new Switch("weight", Integer.class, true, ArgLevel.REQUIRED, "w"),
                new Switch("age", Integer.class, true, ArgLevel.REQUIRED, "ag"),
                new Switch("number", Integer.class, true, ArgLevel.REQUIRED, "num"),
                new Switch("address", String.class, true, ArgLevel.REQUIRED, "ad"),
                new Switch("history", String.class, true, ArgLevel.REQUIRED, "hi"),
                new Switch("append", String.class, true, ArgLevel.NONE, "app")
        );
    }
}
