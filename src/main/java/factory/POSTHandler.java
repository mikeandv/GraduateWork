package factory;

import com.google.gson.Gson;
import customexception.RestException;
import entity.Request;
import entity.Response;
import mvc.Controller;
import mvc.Router;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class POSTHandler implements Handler {
    private Response response;
    private Request request;
    private static Gson gson = new Gson();



    public POSTHandler(Request request) {
        this.response = new Response();// TODO: 08/02/2019 переделать, убрать из конструктора
        this.request = request;
    }

    @Override
    public Response getResponseResult() throws IOException, InvocationTargetException, IllegalAccessException, RestException {

        this.response = new Response();
        String tmpRes = request.getUrl();

        Method m = Router.routeMap.get(request.getMethod() + tmpRes);
        if(m == null) throw new RestException("500");
        Controller c = Router.getInstance(m);



        @SuppressWarnings("unchecked")
        Map<String, String> resultMap =  (HashMap<String, String>) m.invoke(c, request);

        List<String> cookies = new ArrayList<>();

        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            if(entry.getKey().startsWith("cookie")) {
                cookies.add(entry.getValue());
            }
        }

        this.response.buildResponse(200, gson.toJson(resultMap.get("result")), resultMap.get("contentType"), cookies);
        return this.response;
    }
}
