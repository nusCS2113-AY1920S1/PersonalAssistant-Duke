package sgtravel.logic;

import sgtravel.ModelStub;
import sgtravel.commons.enumerations.Constraint;
import sgtravel.commons.exceptions.FileLoadFailException;
import sgtravel.commons.exceptions.RouteGenerateFailException;
import sgtravel.model.locations.Venue;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PathFinderTest {
    @Test
    void execute() throws FileLoadFailException, RouteGenerateFailException {
        ModelStub modelStub = new ModelStub();
        PathFinder pathFinder = new PathFinder(modelStub.getMap());
        Venue buonaVistaMrt = new Venue("Buona Vista",1.3073, 103.8077,0,0);
        Venue yewTeeMrt = new Venue("yewtee", 1.3973, 103.7475,0,0);

        assertNotNull(pathFinder.execute(buonaVistaMrt, yewTeeMrt, Constraint.MRT));
        assertNotNull(pathFinder.execute(buonaVistaMrt, yewTeeMrt, Constraint.BUS));


    }
}
