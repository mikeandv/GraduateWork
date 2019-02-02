package factory;

import entity.Request;
import entity.Response;
import serverconfig.ServerConfig;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;

public class GETHandler implements Handler {
    private Response response;
    private Request request;

    public GETHandler(Request request){
        this.response = new Response();
        this.request = request;

    }

    @Override
    public Response getResponseResult() {

//        if(isAuthorized(request)) {

        String url = this.request.getUrl();
        String filesDir = ServerConfig.getConfig().getParam("web.default_files_dir");
        String defaultUrl = ServerConfig.getConfig().getParam("web.default_page");
        String notFoundUrl = ServerConfig.getConfig().getParam("web.not_found_page");
        String badRequestUrl = ServerConfig.getConfig().getParam("web.bad_request_page");


        if(this.request.getUrl().startsWith("www/app") && !this.request.getParams().isEmpty()) {
            String paramsData = findData(url, request.getParams());
        }


        if(url.equals(filesDir + "/")) {
            this.response.buildResponse(200, defaultUrl, contentTypeChoice(defaultUrl));
            return this.response;
        }

        if(fileExistsCheck(url)) {
            this.response.buildResponse(200, url, contentTypeChoice(url));
        } else {
            this.response.buildResponse(404, notFoundUrl, contentTypeChoice(notFoundUrl));
            return this.response;
        }

//                    // TODO: 15.01.2019 Добавить проверку валидности названия запрашиваемого ресурса

        return this.response;
    }

    private boolean isAuthorized(Request request){

        if(request.getHeader().containsKey("authorization")) {
            //должен быть запрос в базу на поиск соответствия пока в виде заглушки
            String[] tmpArr = request.getHeader().get("authorization").split(" ");
            String authLineD = tmpArr[1].trim();
            Base64.Decoder d = Base64.getDecoder();
            String authLineE = new String(d.decode(authLineD.getBytes()));

            String[] authLoginPass = authLineE.split(":");
            if (authLoginPass[0].equals("mike") && authLoginPass[1].equals("1234"))
                return true;
        }
        return false;
    }

    private String contentTypeChoice (String requestUrl) {

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

    private boolean fileExistsCheck (String fileUrl) {
        try
        {
            Objects.requireNonNull(GETHandler.class.getClassLoader().getResource(fileUrl)).getFile();
            return true;

        } catch (NullPointerException e) {
            return false;
        }
    }

    private String findData(String url, Map<String, String> params) {

        return new String();
    }


}
