package mvc;

import anno.RequestMap;

public class ControllerAppGet implements Controller{

//    public ControllerAppGet() {
//
//    }
//
//    @Request("/")
//    public
//    // нужно сделать роутер, который собирает анотации
//    // несколько контроллеров на разные строки юрл исходя из полученных анотаций
//    // роутер маршрутизирует на нужный узел

    @RequestMap(name = "find user", value = "/users/", method = "GET")
    public void getUserWithId(String[] args) {
    }


    @Override
    public String getResult() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }
}

//  GET /register HTTP/1.1