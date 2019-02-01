package factory;

import com.google.gson.Gson;
import dbhandler.DBSessionFactory;
import dbhandler.dao.UserService;
import entity.Request;
import entity.Response;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import serverconfig.ServerConfig;

import java.util.List;

public class POSTHandler implements Handler {
    private Response response;
    private Request request;
    Gson gson = new Gson();
    private final String[] POST_ACTIONS = {
            "/app/login",
            "/app/newuser",
    };

    public POSTHandler(Request request) {
        this.response = new Response();
        this.request = request;
    }

    @Override
    public Response getResponseResult() {

        String filesDir = ServerConfig.getConfig().getParam("web.default_files_dir");

        if(request.getUrl().equals(filesDir + POST_ACTIONS[0])) //login
        {
            User userExists = loginCheck(request.getBody());

            response.buildResponse(200, gson.toJson(userExists), "application/json");
            return this.response;

        }

        return null;
    }
    private User loginCheck(String jsonData) {

        User tmpUser = gson.fromJson(jsonData,  User.class);

        UserService userService = new UserService();
        tmpUser = userService.findUser(tmpUser.getEmail());
        tmpUser.setCookieSessions(userService.findCookieSessionByUserId(tmpUser.getId()));

//        Session session = DBSessionFactory.getSessionFactory().openSession();
//        Transaction tr = session.beginTransaction();
//
//        String hqlSelect = "from User where email = :email";
//        Query query = session.createQuery(hqlSelect);
//
//        query.setParameter("email", tmpUser.getEmail());
//        List<User> list = query.list();
//
//        User u1 = null;
//        for(User u: list)
//        {
//            u1 = u;
//            u1.setCookieSessions();
//        }
//        tr.commit();
//        session.close();


        return tmpUser;
    }
//    private String getJsonData()
}
