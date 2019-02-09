package mvc;

import anno.RequestMap;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Router {
    public static final Map<String, Method> routeMap = new HashMap<>();
    private static final Controller appGet = new ControllerAppGet();
    private static final Controller appPost = new ControllerAppPost();
    private static final Controller htmlFiles = new ControllerHtmlFiles();
    private static final Controller htmlPages = new ControllerHtmlPages();

    static {
        try {
            for (Method m : appGet.getClass().getDeclaredMethods()) {
                RequestMap requestMap = m.getAnnotation(RequestMap.class);
                routeMap.put(requestMap.method() + requestMap.value(), m);
            }
            for (Method m : appPost.getClass().getDeclaredMethods()) {
                RequestMap requestMap = m.getAnnotation(RequestMap.class);
                routeMap.put(requestMap.method() + requestMap.value(), m);
            }
            for (Method m : htmlFiles.getClass().getDeclaredMethods()) {
                RequestMap requestMap = m.getAnnotation(RequestMap.class);
                if (requestMap.value().length > 1) {
                    for (int i = 0; i < requestMap.value().length; i++) {
                        routeMap.put(requestMap.method() + requestMap.value()[i] + requestMap.filename(), m);
                    }
                }
                routeMap.put(requestMap.method() + requestMap.value() + requestMap.filename(), m);
            }
            for (Method m : htmlPages.getClass().getDeclaredMethods()) {
                RequestMap requestMap = m.getAnnotation(RequestMap.class);
                routeMap.put(requestMap.method() + requestMap.value(), m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Controller getInstance(Method method) {
        if (method.getDeclaringClass() == ControllerAppGet.class) {
            return appGet;
        }
        if (method.getDeclaringClass() == ControllerAppPost.class) {
            return appPost;
        }
        if (method.getDeclaringClass() == ControllerHtmlPages.class) {
            return htmlPages;
        }
        return htmlFiles;
    }
}
