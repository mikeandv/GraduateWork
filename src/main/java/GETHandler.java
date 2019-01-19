import java.util.Base64;
import java.util.Objects;

public class GETHandler extends HttpHandler {
    Response response;

    public GETHandler(){
        this.response = new Response();

    }

    public Response getResponseResult(Request request) {

//        if(isAuthorized(request)) {

        String url = request.getUrl();
        String filesDir = ServerConfig.getConfig().getParam("web.default_files_dir");
        String defaultUrl = ServerConfig.getConfig().getParam("web.default_page");
        String notFoundUrl = ServerConfig.getConfig().getParam("web.not_found_page");
        String badRequestUrl = ServerConfig.getConfig().getParam("web.bad_request_page");


        if(url.equals(filesDir + "/")) {
            this.response.buildResponse(200, defaultUrl, contentTypeChoice(defaultUrl));
            return this.response;
        }

        if(fileExistsCheck(url)) {
            response.buildResponse(200, url, contentTypeChoice(url));
        } else {
            this.response.buildResponse(404, notFoundUrl, contentTypeChoice(notFoundUrl));
            return this.response;
        }





//            if (request.getParams().isEmpty() && !request.getUrl().isEmpty()) {
//
//                String tmp1 = request.getHeader().get("accept");
//                int tmp2 = request.getUrl().lastIndexOf(".");
//
//
//                if (tmp1.contains("text/html")) {
//
//                    // TODO: 15.01.2019 Добавить проверку валидности названия запрашиваемого ресурса
//                    if ((request.getUrl().equals(ServerConfig.getConfig().getParam("web.default_files_dir") + "/"))) {
//
//                        this.response.buildResponse(200, (request.getUrl() + ServerConfig.getConfig().getParam("web.default_page")), "text/html");
//
//                    } else {
//
//                        this.response.buildResponse(200, request.getUrl(), "text/html");
//                    }
//
//                    return this.response;
//
//                } else if (tmp1.contains("image/")) {
//
//                    // TODO: 15.01.2019 Добавить проверку валидности названия запрашиваемого ресурса
//                    this.response.buildResponse(200, request.getUrl(), "image/" + request.getUrl().substring(tmp2 + 1));
//                    return this.response;
//
//                } else if (tmp1.contains("text/css")) {
//
//                    this.response.buildResponse(200, request.getUrl(), "text/css");
//
//                } else if (request.getUrl().substring(tmp2).equals(".js")) {
//
//                    this.response.buildResponse(200, request.getUrl(), "application/x-javascript");
//
//                } else if (request.getUrl().substring(tmp2).equals(".ttf")) {
//
//                    this.response.buildResponse(200, request.getUrl(), "application/x-font-ttf");
//
//                } else if (request.getUrl().substring(tmp2).equals(".woff")) {
//
//                    this.response.buildResponse(200, request.getUrl(), "application/font-woff");
//
//                } else if (request.getUrl().substring(tmp2).equals(".woff2")) {
//
//                    this.response.buildResponse(200, request.getUrl(), "application/font-woff2");
//
//                } else if (request.getUrl().substring(tmp2).equals(".ico")) {
//
//                    this.response.buildResponse(200, request.getUrl(), "application/font-woff2");
//
//                } else if (request.getUrl().substring(tmp2).equals(".txt")) {
//
//                    this.response.buildResponse(200, request.getUrl(), "text/html");
//
//                } else {
//
//                    this.response.buildResponse(400, ServerConfig.getConfig().getParam("web.bad_request_page"), "text/html");
//                }
//
//            }
//        } else {
//            this.response.buildResponse(401, null, "text/html");
//            return this.response;
//        }


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

    private String contentTypeChoice (String reqestUrl) {

        int dotIndex = reqestUrl.lastIndexOf(".");
        String fileExtension = reqestUrl.substring(dotIndex);

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
}
