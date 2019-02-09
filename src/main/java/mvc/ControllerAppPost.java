package mvc;

import anno.RequestMap;

public class ControllerAppPost implements Controller{

    @RequestMap(name = "create user", value = "/users", method = "POST")
    public void createUser(String[] args) {

    }

    @RequestMap(name = "login user", value = "/login", method = "POST")
    public void loginUser(String[] args) {

    }


    @Override
    public String getResult() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }
}
