package duke.command;

public class PatientFindSpec extends ArgSpec {

    private static final PatientFindSpec spec = new PatientFindSpec();

    public static PatientFindSpec getSpec() {
        return spec;
    }

    private PatientFindSpec() {
        emptyArgMsg = "You didn't tell me anything about the patient!";
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("impression", String.class, false, ArgLevel.REQUIRED, "n"),
                new Switch("bed", String.class, false, ArgLevel.REQUIRED, "b"),
                new Switch("allergies", String.class, false, ArgLevel.REQUIRED, "a"),
                new Switch("go", String.class, true, ArgLevel.NONE, "g"),
                new Switch("height", Integer.class, true, ArgLevel.REQUIRED, "h"),
                new Switch("weight", Integer.class,true , ArgLevel.REQUIRED, "w"),
                new Switch("age", Integer.class, true, ArgLevel.REQUIRED, "ag"),
                new Switch("number", Integer.class,true, ArgLevel.REQUIRED, "num"),
                new Switch("address", String.class,true, ArgLevel.REQUIRED, "ad"),
                new Switch("history", String.class,true, ArgLevel.REQUIRED, "hi")
        );
    }
}
