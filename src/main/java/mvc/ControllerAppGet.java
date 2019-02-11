package mvc;

import anno.RequestMap;
import entity.Request;

public class ControllerAppGet implements Controller{


    @RequestMap(name = "find user", value = "/users/", method = "GET")
    public void getUserWithId(Request request) {
    }
}
