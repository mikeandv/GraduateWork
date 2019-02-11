package mvc;

import anno.RequestMap;
import entity.CookieSession;
import entity.Request;
import entity.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerAppPost implements Controller{

    @RequestMap(name = "create user", value = "/users", method = "POST")
    public Map<String, String> createUser(Request request) {

        User userExists = User.userExistsCheck(request.getBody());

        if(userExists == null) {
            User.createNewUser(request.getBody());
            return getResultMap("success", "application/json");
        } else {
            return getResultMap("user-already-exists", "application/json");

        }
    }

    @RequestMap(name = "login user", value = "/login", method = "POST")
    public Map<String, String> loginUser(Request request) {

        User userExists = User.userExistsCheck(request.getBody());

        if (userExists == null) {
//
            return getResultMap("no-such-user", "application/json");

        } else if (userExists.getCookieSessions().isEmpty()) {

            Set<CookieSession> csSet = CookieSession.createCookieSession(userExists);
            return getResultMap("enter-site", "application/json", createCookie(csSet));

        } else {
            Set<CookieSession> csSet = userExists.getCookieSessions();

            for (CookieSession cookieSession : csSet) {
                CookieSession.statusChange(cookieSession);
            }

            csSet = CookieSession.createCookieSession(userExists);

            return getResultMap("enter-site", "application/json", createCookie(csSet)/*, createDisableCookie()*/);
        }
    }

    private Map<String, String> getResultMap(String... resource) {

        Map<String, String> map = new HashMap<>();
        map.put("result", resource[0]);
        map.put("contentType", resource[1]);
        if (resource.length > 2) {
            for (int i = 2; i < resource.length; i++) {
                map.put("cookie[" + i + "]", resource[i]);
            }
        }
        return map;
    }

    private String encodeCookieToken(byte[] b) {
        Base64.Encoder encoder = Base64.getEncoder();
         b = encoder.encode(b);
         return new String(b);
    }

    private String createCookie(Set<CookieSession> csSet) {

        StringBuilder sb = new StringBuilder();
        for (CookieSession cookieSession : csSet) {
            String token = cookieSession.getId() + "&"
                    + cookieSession.getSessionStartDTM().toString() + "&"
                    + cookieSession.getUser().getId();

            sb
                    .append("Token=").append(encodeCookieToken(token.getBytes())).append("; ")
                    .append("Expires=").append(DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime
                    .ofInstant(cookieSession.getSessionEndDTM().toInstant(), ZoneId.of("GMT")))).append("; ");
        }
        return sb.toString();
    }
    private String createDisableCookie() {

        StringBuilder sb = new StringBuilder();
        sb.append("Token=; ").append("Expires=Thu, 01 Jan 1970 00:00:00 GMT; ");

        return sb.toString();
    }

}
