package factory;

import entity.Request;
import entity.Response;

import java.io.IOException;

public interface Handler {

    Response getResponseResult() throws IOException;
}

