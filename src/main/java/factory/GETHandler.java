package factory;

import customexception.RestException;
import entity.Request;
import entity.Response;
import mvc.Controller;
import mvc.Router;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class GETHandler implements Handler {
    private Response response;
    private Request request;
//    private final List<String> FILE_EXTENSIONS = createList();

    public GETHandler(Request request) {
        this.request = request;
    }

    @Override
    public Response getResponseResult() throws InvocationTargetException, IllegalAccessException, IOException, RestException {

        this.response = new Response();
        String tmpRes = request.getUrl();

        if(request.getUrl().contains(".")) {

            int indexOne = request.getUrl().lastIndexOf("/");
            int indexTwo = request.getUrl().indexOf(".");
            tmpRes = request.getUrl().substring(0,indexOne);
            tmpRes = tmpRes + request.getUrl().substring(indexTwo);
        }


        Method m = Router.routeMap.get(request.getMethod() + tmpRes);
        if(m == null) throw new RestException("404");
        Controller c = Router.getInstance(m);

        @SuppressWarnings("unchecked")
        Map<String, String> resultMap =  (HashMap<String, String>) m.invoke(c, request);

        this.response.buildResponse(200, resultMap.get("result"), resultMap.get("contentType"));

        return this.response;
    }


//    private String contentTypeChoice(String requestUrl) {
//
//        int dotIndex = requestUrl.lastIndexOf(".");
//        String fileExtension = requestUrl.substring(dotIndex);
//
//        switch (fileExtension) {
//            case ".txt":
//                return "text/html";
//            case ".html":
//                return "text/html";
//            case ".css":
//                return "text/css";
//            case ".js":
//                return "application/x-javascript";
//            case ".ttf":
//                return "application/x-font-ttf";
//            case ".woff":
//                return "application/font-woff";
//            case ".ico":
//                return "image/webp";
//            case ".png":
//                return "image/png";
//            case ".gif":
//                return "image/gif";
//            case ".jpeg":
//                return "image/jpeg";
//            default:
//                return "text/html";
//        }
//    }
//
//    private List<String> createList() {
//        List<String> myList = new ArrayList<>();
//        myList.add(".html");
//        myList.add(".css");
//        myList.add(".js");
//        myList.add(".txt");
//        myList.add(".ttf");
//        myList.add(".woff");
//        myList.add(".ico");
//        myList.add(".png");
//        myList.add(".gif");
//        myList.add(".jpeg");
//
//        return myList;
//    }

}


