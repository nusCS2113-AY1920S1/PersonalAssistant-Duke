package duke.util.argparse4j.action;

import java.util.Map;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentParser;

public class Join implements ArgumentAction {

    @Override
    public void run(ArgumentParser parser, Argument arg, Map<String, Object> attrs, String flag, Object value) {
        if (value instanceof Iterable) {
            attrs.put(arg.getDest(), String.join(" ", (Iterable) value));
        }
    }

    @Override
    public void onAttach(Argument arg) {
    }

    @Override
    public boolean consumeArgument() {
        return true;
    }
}