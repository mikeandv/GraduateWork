package factory;

import customexception.RestException;
import entity.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface Handler {

    Response getResponseResult() throws IOException, InvocationTargetException, IllegalAccessException, RestException;
}

