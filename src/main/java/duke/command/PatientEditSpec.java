package duke.command;

public class PatientEditSpec extends ArgSpec {
    private static final PatientEditSpec spec = new PatientEditSpec();

    public static PatientEditSpec getSpec() {
        return spec;
    }

    private PatientEditSpec() {
        emptyArgMsg = "You didn't tell me what to edit!";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                // new Switch("name", String.class, true, ArgLevel.REQUIRED, "n"),
                // new Switch("bed", String.class, true, ArgLevel.OPTIONAL, "b"),
                // new Switch("append", String.class, true, ArgLevel.NONE, "app"),
                new Switch("allergies", String.class, true, ArgLevel.REQUIRED, "a",
                        "allergy"),
                new Switch("height", Integer.class, true, ArgLevel.REQUIRED, "h"),
                new Switch("weight", Integer.class, true, ArgLevel.REQUIRED, "w"),
                new Switch("age", Integer.class, true, ArgLevel.REQUIRED, "ag"),
                new Switch("number", Integer.class, true, ArgLevel.REQUIRED, "num"),
                new Switch("address", String.class, true, ArgLevel.REQUIRED, "ad"),
                new Switch("history", String.class, true, ArgLevel.REQUIRED, "hi")
        );
    }
}
