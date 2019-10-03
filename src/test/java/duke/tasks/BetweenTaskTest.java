package duke.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BetweenTaskTest {


    @Test
    public void toString_testingToString_success() {
        assertEquals("[B][x] complete your ippt (between: 19/05/2018 and 19/05/2019)",
                new BetweenTask("complete your ippt", "19/05/2018", "19/05/2019")
                        .toString());
        assertEquals("[B][x] study for midterms (between: 27/09/2019 and 29/09/2019)",
                new BetweenTask("study for midterms", "27/09/2019", "29/09/2019")
                        .toString());
        assertEquals("[B][x] study for cs2113T (between: 01/08/2019 and 27/11/2019)",
                new BetweenTask("study for cs2113T", "01/08/2019", "27/11/2019")
                        .toString());
        assertEquals("[B][x] eat pizzaaaa (between: 19/05/2018 and 19/05/2019)",
                new BetweenTask("eat pizzaaaa", "19/05/2018", "19/05/2019")
                        .toString());
    }
}
