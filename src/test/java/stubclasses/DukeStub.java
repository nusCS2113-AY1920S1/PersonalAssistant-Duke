package stubclasses;

import commons.Duke;

public class DukeStub extends Duke {
    @Override
    public String getResponse(String input) {
        return super.getResponse(input);
    }

    public void runGetResponse(){
        getResponse("find/time 3");
        getResponse("find/time 3");
    }
}
