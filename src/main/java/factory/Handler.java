package factory;

import entity.Request;
import entity.Response;

public interface Handler {

//    Response requestProceed(entity.Request request) throws IOException;
    Response getResponseResult();
}



//    entity.Response response = null;
//// TODO: 15.01.2019 Добавить обработку ситуации с вызовом не поддерживаемого метода в default
//        switch (request.getMethod()) {
//                case "GET":
//                GETHandler getHandler = new GETHandler();
//                response = getHandler.getResponseResult(request);
//                break;
//                case "POST":
//                POSTHandler postHandler = new POSTHandler();
//                break;
//default:
//
//        }
