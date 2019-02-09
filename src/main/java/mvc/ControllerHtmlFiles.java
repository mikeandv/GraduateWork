package mvc;

import anno.RequestMap;
import entity.Request;
import serverconfig.ServerConfig;

public class ControllerHtmlFiles implements Controller {
    private String result;
    private String contentType;
    private String fileDir = ServerConfig.getConfig().getParam("web.default_files_dir");

    @RequestMap(name = "css file", value = {"/", "/login", "/register", "/profile"}, method = "GET", filename = ".css")
    public void getCssFile(Request request) {
        result = fileDir + request.getUrl();
        contentType = "text/css";
    }

    @RequestMap(name = "js file", value = {"/", "/login", "/register", "/profile"}, method = "GET", filename = ".js")
    public void getJsFile(Request request) {
        result = fileDir + request.getUrl();
        contentType = "application/x-javascript";
    }

    @RequestMap(name = "txt file", value = {"/", "/login", "/register", "/profile"}, method = "GET", filename = ".txt")
    public void getTxtFile(Request request) {

    }

    @RequestMap(name = "ico file", value = {"/", "/login", "/register", "/profile"}, method = "GET", filename = ".ico")
    public void getIcoFile(Request request) {

    }

    @RequestMap(name = "png file", value = {"/", "/login", "/register", "/profile"}, method = "GET", filename = ".png")
    public void getPngFile(Request request) {

    }

    @RequestMap(name = "gif file", value = {"/", "/login", "/register", "/profile"}, method = "GET", filename = ".gif")
    public void getGifFile(Request request) {

    }

    @RequestMap(name = "jpeg file", value = {"/", "/login", "/register", "/profile"}, method = "GET", filename = ".jpeg")
    public void getJpegFile(Request request) {

    }

    @Override
    public String getResult() {
        return this.result;
    }
    @Override
    public String getContentType() {
        return contentType;
    }
}
