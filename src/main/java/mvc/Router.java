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

            for (Method m : appGet.getClass().getDeclaredMethods()) {
                RequestMap requestMap = m.getAnnotation(RequestMap.class);
                if(requestMap == null) {continue;}
                for (int i = 0; i < requestMap.value().length; i++) {
                    routeMap.put(requestMap.method() + requestMap.value()[i], m);
                }
            }
            for (Method m : appPost.getClass().getDeclaredMethods()) {
                RequestMap requestMap = m.getAnnotation(RequestMap.class);
                if(requestMap == null) {continue;}
                for (int i = 0; i < requestMap.value().length; i++) {
                    routeMap.put(requestMap.method() + requestMap.value()[i], m);
                }
            }
            for (Method m : htmlFiles.getClass().getDeclaredMethods()) {
                RequestMap requestMap = m.getAnnotation(RequestMap.class);
                if(requestMap == null) {continue;}
                for (int i = 0; i < requestMap.value().length; i++) {
                        routeMap.put(requestMap.method() + requestMap.value()[i] + requestMap.filename(), m);
                }
            }
            for (Method m : htmlPages.getClass().getDeclaredMethods()) {
                RequestMap requestMap = m.getAnnotation(RequestMap.class);
                if (requestMap == null) {continue;}
                for (int i = 0; i < requestMap.value().length; i++) {
                    routeMap.put(requestMap.method() + requestMap.value()[i], m);
                }
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
        if (method.getDeclaringClass() == ControllerHtmlFiles.class) {
            return htmlFiles;
        }
        return null;
    }
}
