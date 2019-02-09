package entity;

import serverconfig.ServerConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Response {
    private final Map<Integer, String> HTTP_REPLIES = createMap();
    private String header;
    private byte[] data;
    private long contentLength = 0;
    private String contentType;
    private Map<String, String> cookie = new HashMap<>();

    public Response() {
    }


    public static Response getInstanceError(int errorCode, String message) {

        Response r = new Response();
        String responseStatus = r.getAnswer(errorCode);
//        for(int i = 0; i < r.HTTP_REPLIES.length; i++) {
//            if(r.HTTP_REPLIES[i][0].equals(errorCode)) {
//                responseStatus = r.HTTP_REPLIES[i][1];
//                break;
//            }
//        }

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"ru\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n")
                .append("<title>" + responseStatus + "</title>\n")
                .append("</head> "+
                        "<body>")
                .append("<h1>" + responseStatus + "</h1>")
                .append("<div>" + message + "</div>")
                .append("</body>\n" +
                        "</html>");

        try {
            r.buildResponse(errorCode, sb.toString(), "text/html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return r;
    }

    private String getHeaderStream(int code) {
        DateFormat d = DateFormat.getDateInstance();
        StringBuilder buffer = new StringBuilder();

        if(code == 200) {
            buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
//            buffer.append("WWW-Authenticate: Basic realm=\"User Visible Realm\"");
            buffer.append("Date: " + DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT"))) + "\n");
            buffer.append("Content-Type: " + contentType + "\n");

            if(contentLength != 0) {
                buffer.append("Accept-Ranges: bytes\n");
                buffer.append("Content-Length: " + contentLength + "\n");
            }

            if(!cookie.isEmpty()) {
                buffer.append("Set-Cookie: ");
                for (Map.Entry<String, String> entry : cookie.entrySet()) {

                    buffer.append(entry.getKey() + "=" + entry.getValue() + "; ");
                }
                buffer.append("Domain="+ ServerConfig.getConfig().getParam("web.domain") + "; ")
                        .append("Path=/; ").append("HttpOnly").append("\n");
            }
            buffer.append("\r\n");
        } else if(code == 401){
            buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
//            buffer.append("WWW-Authenticate: Basic realm=\"User Visible Realm\"\n");
            buffer.append("\r\n");
        } else {
            buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
            buffer.append("\r\n");
        }
        return buffer.toString();
    }

    private String getAnswer(int code) {

        for(Map.Entry<Integer, String> entry: HTTP_REPLIES.entrySet()){
            if(entry.getKey() == code) {
                return entry.getValue();
            }
        }
        return null; // TODO: 15.01.2019 Нужно посмотреть насколько это правильно будет работать

    }

    public InputStream getDataStream() {
        if(data == null) {
            return null;
        } else {
            return new ByteArrayInputStream(this.data);
        }
    }

    public InputStream getHeaderStream() {
        return new ByteArrayInputStream(header.getBytes());
    }


    public void buildResponse(int code, String data, String contentType) throws IOException {
        this.data = getDataBytes(data);
        // TODO: 21/01/2019 Для реализации инекции в хтмл можно сделать чтение файла по строками и поиск в нужной строке якорных тегов для подмены значений
        // TODO: 16.01.2019 вынести в отдельный метод
        this.contentLength = this.data.length;
        this.contentType = contentType;
        this.header = getHeaderStream(code);

    }

    public void buildResponse(int code, String data, String contentType, Map<String, String> cookie) throws IOException {
        this.cookie = cookie;
        buildResponse(code, data, contentType);
    }

    private byte[] getDataBytes(String tempData) throws IOException {

        byte[] dataBytes = null;

        try
        {
            File tempFile = new File(Objects.requireNonNull(Response.class.getClassLoader().getResource(tempData)).getFile());
            if(tempFile.exists() && !tempFile.isDirectory()){
                dataBytes = Files.readAllBytes(tempFile.toPath());
            }

        } catch (NullPointerException e) {
            dataBytes =  tempData.getBytes();

        }

        return dataBytes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("entity.Response:\n");
        sb.append(this.header);
        if(this.data == null)
            sb.append("No data in body");
        else
            sb.append(new String(this.data));
        return sb.toString();
    }

    private static Map<Integer, String> createMap() {
        Map<Integer, String> myMap = new HashMap<>();
        myMap.put(200, "OK");
        myMap.put(400, "Bad Request");
        myMap.put(404, "Not Found");
        myMap.put(401, "Unauthorized");
        myMap.put(405, "Method Not Allowed");
        myMap.put(500, "Internal Server Error");
        myMap.put(501, "Not Implemented");

        return myMap;

    }
}
