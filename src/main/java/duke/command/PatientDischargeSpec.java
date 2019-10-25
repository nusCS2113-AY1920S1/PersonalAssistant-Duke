package duke.command;

public class PatientDischargeSpec extends ArgSpec {
    private static final PatientDischargeSpec spec = new PatientDischargeSpec();

    public static PatientDischargeSpec getSpec() {
        return spec;
    }

    private PatientDischargeSpec() {
        emptyArgMsg = "";
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(new Switch("summary", String.class, true, ArgLevel.OPTIONAL, "sum"));
    }
}