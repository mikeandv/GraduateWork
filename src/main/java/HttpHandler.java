import java.io.IOException;

public class HttpHandler {


    public Response requestProceed(Request request) throws IOException {

        Response response = null;
        // TODO: 15.01.2019 Добавить обработку ситуации с вызовом не поддерживаемого метода в default
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
