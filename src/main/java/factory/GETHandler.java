package factory;

import customexception.RestException;
import entity.Request;
import entity.Response;
import mvc.Controller;
import mvc.Router;
import serverconfig.ServerConfig;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class GETHandler implements Handler {
    private Response response;
    private Request request;
    private final List<String> FILE_EXTENSIONS = createList();

    public GETHandler(Request request) {
        this.request = request;
    }

    @Override
    public Response getResponseResult() throws InvocationTargetException, IllegalAccessException, IOException {
        String tmpRes = request.getUrl();

        if(request.getUrl().contains(".")) {

            int indexOne = request.getUrl().lastIndexOf("/");
            int indexTwo = request.getUrl().indexOf(".");
            tmpRes = request.getUrl().substring(0,indexOne);
            tmpRes = tmpRes + request.getUrl().substring(indexTwo);
        }

        Method m = Router.routeMap.get(request.getMethod() + tmpRes);
        Controller c = Router.getInstance(m);

        m.invoke(c, request);


        response.buildResponse(200, c.getResult(), c.getContentType());



//        this.response = new Response();
//        String url = this.request.getUrl();
//        String filesDir = ServerConfig.getConfig().getParam("web.default_files_dir");
//        String defaultUrl = ServerConfig.getConfig().getParam("web.default_page");
//        String appFolder = ServerConfig.getConfig().getParam("web.app_url");
//        List<String> defaultPages = Arrays.asList(ServerConfig.getConfig().getParam("web.default_page").split(","));
//        if (url.contains(appFolder)) {
//            // Если приложение то запускаем обработку данных
//            // Создаем роутер который маршрутизирует на нужный контроллер
//        }
//        if (url.contains(filesDir)) {
//            // Поиск ресурса для отображения клиенту
//            // Создаем роутер который маршрутизирует на нужную заявку
//        }
//        if (this.request.getUrl().startsWith("www/app/users/667") && !this.request.getParams().isEmpty()) {
//            String paramsData = findData(url, request.getParams());
//        }
//        if (url.equals(filesDir + "/")) {
//            this.response.buildResponse(200, defaultUrl, contentTypeChoice(defaultUrl));
//            return this.response;
//        }
//        if (fileExistsCheck(url)) {
//            this.response.buildResponse(200, url, contentTypeChoice(url));
//        } else {
//            this.response.buildResponse(404, notFoundUrl, contentTypeChoice(notFoundUrl));
//            return this.response;
//        }
//                    // TODO: 15.01.2019 Добавить проверку валидности названия запрашиваемого ресурса

        return this.response;
    }


    private String contentTypeChoice(String requestUrl) {

        int dotIndex = requestUrl.lastIndexOf(".");
        String fileExtension = requestUrl.substring(dotIndex);

        switch (fileExtension) {
            case ".txt":
                return "text/html";
            case ".html":
                return "text/html";
            case ".css":
                return "text/css";
            case ".js":
                return "application/x-javascript";
            case ".ttf":
                return "application/x-font-ttf";
            case ".woff":
                return "application/font-woff";
            case ".ico":
                return "image/webp";
            case ".png":
                return "image/png";
            case ".gif":
                return "image/gif";
            case ".jpeg":
                return "image/jpeg";
            default:
                return "text/html";
        }
    }

//    private String fileExistsCheck(String fileUrl) {
//
//        for(String s : FILE_EXTENSIONS) {
//
//            try {
//                Objects.requireNonNull(GETHandler.class.getClassLoader().getResource(fileUrl)).getFile();
//                return fileUrl + s;
//
//            } catch (NullPointerException e) {
//                return false;
//            }
//        }
//    }

    private String findData(String url, Map<String, String> params) {

        return new String();
    }

    private String findResourse() {
        String fileUrl = request.getUrl();

        try {
            Objects.requireNonNull(GETHandler.class.getClassLoader().getResource(fileUrl)).getFile();


        } catch (NullPointerException e) {
            return null;
        }
        return null;
    }

    private List<String> createList() {
        List<String> myList = new ArrayList<>();
        myList.add(".html");
        myList.add(".css");
        myList.add(".js");
        myList.add(".txt");
        myList.add(".ttf");
        myList.add(".woff");
        myList.add(".ico");
        myList.add(".png");
        myList.add(".gif");
        myList.add(".jpeg");

        return myList;
    }

}


