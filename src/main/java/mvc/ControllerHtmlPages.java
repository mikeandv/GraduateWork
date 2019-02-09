package mvc;

import anno.RequestMap;
import entity.Request;
import serverconfig.ServerConfig;

public class ControllerHtmlPages implements Controller{
    private String result;
    private String contentType;

    @RequestMap(name = "default", value = "/", method = "GET", headers = "Cookie")
    public void defaultPage (Request request) {
        result = readFilesName("web.default_pages")[0];
        contentType = "text/html";
    }

    @RequestMap(name = "register", value = "/register", method = "GET")
    public void registerPage(Request request) {
    }

    @RequestMap(name = "profile", value = "/profile", method = "GET", headers = "Cookie")
    public void profilePage(Request request) {
    }

    @RequestMap(name = "login", value = "/login", method = "GET")
    public void loginPage(Request request) {
    }

    @Override
    public String getResult() {
        return this.result;
    }

    @Override
    public String getContentType() {
        return contentType;
    }
    private String[] readFilesName(String str) {
        return ServerConfig.getConfig().getParam(str).split(",");

    }
}
