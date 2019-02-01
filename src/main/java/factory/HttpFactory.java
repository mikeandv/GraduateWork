package factory;

import entity.Request;

public class HttpFactory {

    public Handler getHandler(Request request) {

// TODO: 15.01.2019 Добавить обработку ситуации с вызовом не поддерживаемого метода в default

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
                return () -> null;
        }
    }
}
