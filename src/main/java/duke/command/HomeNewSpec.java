package duke.command;

public class HomeNewSpec extends ArgSpec {

    private static final HomeNewSpec spec = new HomeNewSpec();

    public static HomeNewSpec getSpec() {
        return spec;
    }

    private HomeNewSpec() {
        emptyArgMsg = "You didn't tell me anything about the patient!";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("name", String.class, false, ArgLevel.REQUIRED, "n"),
                new Switch("bed", String.class, false, ArgLevel.REQUIRED, "b"),
                new Switch("allergies", String.class, false, ArgLevel.REQUIRED, "a",
                        "allergy"),
                new Switch("go", String.class, true, ArgLevel.NONE, "g"),
                new Switch("height", Integer.class, true, ArgLevel.REQUIRED, "h"),
                new Switch("weight", Integer.class, true, ArgLevel.REQUIRED, "w"),
                new Switch("age", Integer.class, true, ArgLevel.REQUIRED, "ag"),
                new Switch("number", Integer.class, true, ArgLevel.REQUIRED, "num"),
                new Switch("address", String.class, true, ArgLevel.REQUIRED, "ad"),
                new Switch("history", String.class, true, ArgLevel.REQUIRED, "hi")
        );
    }
}
