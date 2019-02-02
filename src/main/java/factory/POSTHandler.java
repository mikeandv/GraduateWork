package factory;

import com.google.gson.Gson;
import dbhandler.dao.CookieSessionService;
import dbhandler.dao.UserService;
import entity.CookieSession;
import entity.Request;
import entity.Response;
import entity.User;
import serverconfig.ServerConfig;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class POSTHandler implements Handler {
    private Response response;
    private Request request;
    Gson gson = new Gson();
    private final String[] POST_ACTIONS = {
            ServerConfig.getConfig().getParam("web.default_files_dir") + "/app/login",
            ServerConfig.getConfig().getParam("web.default_files_dir") + "/app/register",
    };

    public POSTHandler(Request request) {
        this.response = new Response();
        this.request = request;
    }

    @Override
    public Response getResponseResult() {

        String filesDir = ServerConfig.getConfig().getParam("web.default_files_dir");

        if(request.getUrl().equals(POST_ACTIONS[0])) //  /app/login
        {
            User userExists = loginCheck(request.getBody());

            if(userExists == null) {
                response.buildResponse(200, gson.toJson("nosuchuser"), "application/json");
                return this.response;
            } else if(userExists.getCookieSessions().isEmpty()) {

                Set<CookieSession> csSet = createCookieSession(userExists);
                Map<String, String> cookieMap = new HashMap<>();

                for(CookieSession cookieSession : csSet) {
                    cookieMap.put("sessionid", String.valueOf(cookieSession.getId()));
                    cookieMap.put("Expires", DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime
                                    .ofInstant(cookieSession.getSessionEndDTM().toInstant(), ZoneId.of("GMT"))));
                }
                response.buildResponse(200, gson.toJson("entersite"), "application/json", cookieMap);
                return this.response;

            }

            response.buildResponse(200, gson.toJson(userExists), "application/json");
            return this.response;

        } else if(request.getUrl().equals(POST_ACTIONS[1])) // /app/register
        {
            User userExists = loginCheck(request.getBody());

            if(userExists == null) {
                userExists = createNewUser(request.getBody());
                response.buildResponse(200, gson.toJson("success"), "application/json");
                return this.response;
            } else {
                response.buildResponse(200, gson.toJson("userexists"), "application/json");
                return this.response;
            }
        }

        return null;
    }
    private User loginCheck(String jsonData) {

        User tmpUser = gson.fromJson(jsonData, User.class);

        UserService userService = new UserService();
        tmpUser = userService.findUser(tmpUser.getEmail());
        if( !(tmpUser == null) ) {
            tmpUser.setCookieSessions(userService.findCookieSessionByUserIdAndCode(tmpUser.getId(), "100"));
        }

        return tmpUser;
    }

    private User createNewUser(String jsonData) {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();

        User userTmp = gson.fromJson(jsonData, User.class);
        userTmp.setCreateDTM(date);
        userTmp.setLastLoginDTM(date);
        UserService userService = new UserService();
        userService.saveUser(userTmp);

        User user = userService.findUser(userTmp.getEmail());

        user.setCookieSessions(createCookieSession(user));
        return user;
    }

    private Set<CookieSession> createCookieSession(User user) {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();

        CookieSession cs = new CookieSession();
        cs.setUser(user);
        cs.setSessionStartDTM(date);

        c.add(Calendar.DAY_OF_YEAR, 2);  // advances day by 2
        date = c.getTime();

        cs.setSessionEndDTM(date);
        cs.setStatus("100");
        CookieSessionService csService = new CookieSessionService();
        csService.saveCookieSession(cs);

        return csService.findCookiSessByUserIdAndCode(user.getId(), "100");
    }
}
