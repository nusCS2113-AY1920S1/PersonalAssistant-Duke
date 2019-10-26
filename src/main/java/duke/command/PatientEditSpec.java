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
                new Switch("name", String.class, true, ArgLevel.OPTIONAL, "n"),
                // new Switch("bed", String.class, true, ArgLevel.OPTIONAL, "b"),
                new Switch("allergies", String.class, true, ArgLevel.OPTIONAL, "a",
                        "allergy"),
                new Switch("height", Integer.class, true, ArgLevel.OPTIONAL, "h"),
                new Switch("weight", Integer.class, true, ArgLevel.OPTIONAL, "w"),
                new Switch("age", Integer.class, true, ArgLevel.OPTIONAL, "ag"),
                new Switch("number", Integer.class, true, ArgLevel.OPTIONAL, "num"),
                new Switch("address", String.class, true, ArgLevel.OPTIONAL, "ad"),
                new Switch("history", String.class, true, ArgLevel.OPTIONAL, "hi"),
                new Switch("append", String.class, true, ArgLevel.OPTIONAL, "app")
        );
    }
}
