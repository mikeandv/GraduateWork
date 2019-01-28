package factory;

import entity.Request;
import entity.Response;

public class POSTHandler implements Handler {
    Response response;
    Request request;

    public POSTHandler(Request request) {
        this.response = new Response();
        this.request = request;
    }

    @Override
    public Response getResponseResult() {
        return null;
    }


//    public entity.Response getResponseResult(entity.Request request) {
//        if(!request.getBody().isEmpty()){
//
//        }
//    }
}
