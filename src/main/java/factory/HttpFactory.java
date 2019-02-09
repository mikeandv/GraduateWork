package factory;

import customexception.RestException;
import entity.Request;

public class HttpFactory {

    public Handler getHandler(Request request) throws RestException{

        if(request.getMethod().isEmpty())
            throw new RestException("400");


        switch (request.getMethod()) {
            case "GET":
                return new GETHandler(request);
            case "POST":
                return new POSTHandler(request);
//            case "PUT":
//                return
//            case "DELETE":
//                return
            default:
                throw new RestException("501");
        }
    }
}
