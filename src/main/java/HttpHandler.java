import java.io.IOException;

public class HttpHandler {


    public Response requestProceed(Request request) throws IOException {

        Response response = null;

        switch (request.getMethod()) {
            case "GET":
                GETHandler getHandler = new GETHandler();
                response = getHandler.getResponseResult(request);
                break;
            case "POST":
                POSTHandler postHandler = new POSTHandler();
                break;
            default:

        }
        return response;
    }
}
