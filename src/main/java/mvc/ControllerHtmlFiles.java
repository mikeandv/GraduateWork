package mvc;

import anno.RequestMap;
import customexception.RestException;
import entity.Request;
import serverconfig.ServerConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControllerHtmlFiles implements Controller {
    private String fileDir = ServerConfig.getConfig().getParam("web.default_files_dir");

    @RequestMap(name = "css file", value = {"/", "/login", "/register", "/profile", "/styles"}, method = "GET", filename = ".css")
    public Map<String, String> getCssFile(Request request) throws RestException {

        return getResultMap(fileDir + request.getUrl(), "text/css");
    }

    @RequestMap(name = "js file", value = {"/", "/login", "/register", "/profile", "/scripts"}, method = "GET", filename = ".js")
    public Map<String, String> getJsFile(Request request) throws RestException {

        return getResultMap(fileDir + request.getUrl(), "application/x-javascript");
    }

    @RequestMap(name = "txt file", value = {"/", "/login", "/register", "/profile"}, method = "GET", filename = ".txt")
    public Map<String, String> getTxtFile(Request request) throws RestException{

        return getResultMap(fileDir + request.getUrl(), "text/html");
    }

    @RequestMap(name = "ico file", value = {"/", "/login", "/register", "/profile", "/images"}, method = "GET", filename = ".ico")
    public Map<String, String> getIcoFile(Request request) throws RestException{

        return getResultMap(fileDir + request.getUrl(), "image/webp");
    }

    @RequestMap(name = "png file", value = {"/", "/login", "/register", "/profile", "/images"}, method = "GET", filename = ".png")
    public Map<String, String> getPngFile(Request request) throws RestException{

        return getResultMap(fileDir + request.getUrl(), "image/png");
    }

    @RequestMap(name = "gif file", value = {"/", "/login", "/register", "/profile", "/images"}, method = "GET", filename = ".gif")
    public Map<String, String> getGifFile(Request request) throws RestException{

        return getResultMap(fileDir + request.getUrl(), "image/gif");
    }

    @RequestMap(name = "jpeg file", value = {"/", "/login", "/register", "/profile", "/images"}, method = "GET", filename = ".jpeg")
    public Map<String, String> getJpegFile(Request request) throws RestException{

        return getResultMap(fileDir + request.getUrl(), "image/jpeg");
    }

    @RequestMap(name = "jpeg file", value = {"/", "/login", "/register", "/profile", "/images"}, method = "GET", filename = ".jpg")
    public Map<String, String> getJpgFile(Request request) throws RestException{

        return getResultMap(fileDir + request.getUrl(), "image/jpg");
    }

    public Map<String, String> getResultMap(String... resource) throws RestException {

        Map<String, String> map = new HashMap<>();
        map.put("result", resource[0]);
        findResource(map.get("result"));
        map.put("contentType", resource[1]);

        return map;
    }

    private void findResource(String str) throws RestException {
        try {
            Objects.requireNonNull(ControllerHtmlPages.class.getClassLoader().getResource(str)).getFile();

        } catch (NullPointerException e) {
            throw new RestException("404");
        }
    }
}
