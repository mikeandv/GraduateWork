public class GETHandler extends HttpHandler {
    Response response;

    public GETHandler(){
        this.response = new Response();

    }

    public Response getResponseResult(Request request) {


        if(request.getParams().isEmpty() && !request.getUrl().isEmpty()) {

            String tmp1 = request.getHeader().get("accept");
            if (tmp1.contains("text/html")) {
                // TODO: 15.01.2019 Добавить проверку валидности названия запрашиваемого ресурса
                    this.response.buildResponse(200, request.getUrl());
                    return this.response;
            }

            if (tmp1.contains("image/")) {
                    this.response.buildResponse(200, request.getUrl());
                    return this.response;
            }
        }

        return this.response;
    }
}
