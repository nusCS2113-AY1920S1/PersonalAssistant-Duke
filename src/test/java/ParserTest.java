import exceptions.FarmioException;
import farmio.Farmio;
import org.junit.jupiter.api.Test;
import farmio.Parser;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void doTest(){
        try {
            Parser.parse("do", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("do ", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("do go to market", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("do risaen", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("do gotomarket", Farmio.Stage.TASK_ADD);
            Parser.parse("do gotowheatfarm", Farmio.Stage.TASK_ADD);
            Parser.parse("do buyseeds", Farmio.Stage.TASK_ADD);
            Parser.parse("do plantseeds", Farmio.Stage.TASK_ADD);
            Parser.parse("do harvestwheat", Farmio.Stage.TASK_ADD);
            Parser.parse("do sellgrain", Farmio.Stage.TASK_ADD);
            assert true;
        } catch (FarmioException e) {
            assert false;
        }
    }

    @Test
    public void ifTest() {
        try {
            Parser.parse("if", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if ", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if hasseeds", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if hasseeds do", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if hasseeds do ", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if riant do rian", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if gold greater than or equals 10", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if gold greater than -10 do buyseeds", Farmio.Stage.TASK_ADD);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            Parser.parse("if gold greater than 10 do buyseeds", Farmio.Stage.TASK_ADD);
            Parser.parse("if hasseeds do plantseeds", Farmio.Stage.TASK_ADD);
            Parser.parse("if gold lessthanorequals 10 do buyseeds", Farmio.Stage.TASK_ADD);
            Parser.parse("if gold LESS THAN 20 do buyseeds", Farmio.Stage.TASK_ADD);
            Parser.parse("if gold greater thanorequals 100 do buyseeds", Farmio.Stage.TASK_ADD);
            assert true;
        } catch (FarmioException e) {
            assert false;
        }
    }
}
