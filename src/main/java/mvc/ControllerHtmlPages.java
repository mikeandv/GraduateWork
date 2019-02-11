package mvc;

import anno.RequestMap;
import customexception.RestException;
import entity.CookieSession;
import entity.Request;
import serverconfig.ServerConfig;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControllerHtmlPages implements Controller{
//    private AtomicReference<String> result = new AtomicReference<>();
//    private AtomicReference<String> contentType = new AtomicReference<>();

    @RequestMap(name = "default", value = "/", method = "GET", headers = "Cookie")
    public Map<String, String> defaultPage(Request request) throws RestException {

        return getResultMap(0, "web.default_pages");
    }

    @RequestMap(name = "register", value = "/users", method = "GET")
    public Map<String, String> registerPage(Request request) throws RestException {

        return getResultMap(2, "web.default_pages");
    }

    @RequestMap(name = "profile", value = "/users/", method = "GET", headers = "Cookie")
    public Map<String, String> profilePage(Request request) throws RestException {
        return getResultMap(1, "web.default_pages");
    }

    @RequestMap(name = "login", value = "/login", method = "GET")
    public Map<String, String> loginPage(Request request) throws RestException {

        return getResultMap(3, "web.default_pages");
    }

    private Map<String, String> getResultMap(int index, String... resource) throws RestException{

        Map<String, String> map = new HashMap<>();
        map.put("result", readFilesName(resource[0])[index]);
        findResource(map.get("result"));
        map.put("contentType", "text/html");

        return map;
    }

    private String[] readFilesName(String str) {
        return ServerConfig.getConfig().getParam(str).split(",");

    }
    private void findResource(String str) throws RestException {
        try {
            Objects.requireNonNull(ControllerHtmlPages.class.getClassLoader().getResource(str)).getFile();

        } catch (NullPointerException e) {
            throw new RestException("404");
        }
    }
    private String decodeCookieToken(byte[] b) {
        Base64.Decoder decoder = Base64.getDecoder();
        b = decoder.decode(b);
        return new String(b);
    }
    private boolean authCookieCheck(Map<String, String> headersMap) {

        if(headersMap.containsKey("cookie")) {
            if(headersMap.get("cookie").startsWith("Token=")) {
                int index = headersMap.get("cookie").indexOf("=");
                String cookieKey = headersMap.get("cookie").substring(index);
                String[] cookieFields = cookieKey.split("&");
                CookieSession cs = CookieSession.findById(Long.parseLong(cookieFields[1]));
                if(!(cs == null && cs.getStatus() == "100")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
