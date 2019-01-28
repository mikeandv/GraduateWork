package entity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class Response {
    private final String[][] HTTP_REPLIES = {
            {"200", "OK"},
            {"404", "Not Found"},
            {"400", "Bad entity.Request"},
            {"401", "Unauthorized"},
            {"500", "Internal Server Error"}
    };
    //private int code;
    private String header;
    private String data;
    private long contentLength = 0;
    private String contentType;

    public Response() {
    }

    private String getHeader(int code) {
        DateFormat d = DateFormat.getDateInstance();
        StringBuilder buffer = new StringBuilder();

        if(code == 200) {
            buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
//            buffer.append("WWW-Authenticate: Basic realm=\"User Visible Realm\"");
            buffer.append("Date: " + d.format(new Date()) + "\n");
            buffer.append("Content-Type: " + contentType + "\n");

            if(contentLength != 0) {
                buffer.append("Accept-Ranges: bytes\n");
                buffer.append("Content-Length: " + contentLength + "\n");
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

        for(int i = 0; i < HTTP_REPLIES.length; i++) {
            if(HTTP_REPLIES[i][0].equals(String.valueOf(code)))
                return HTTP_REPLIES[i][1];
        }
        return null; // TODO: 15.01.2019 Нужно посмотреть насколько это правильно будет работать

    }

    public InputStream getData() {
//        if (this.data.equals("")) {
//
//            String stringData = String.valueOf(this.data);
//            return new ByteArrayInputStream(stringData.getBytes())
//        } else {
        if(data == null) {
            return null;
        }
            return Response.class.getClassLoader().getResourceAsStream(data);
        //
//        }
    }

    public InputStream getHeader() {
        return new ByteArrayInputStream(header.getBytes());
    }


    public void buildResponse(int code, String data, String contentType) {
        this.data = data;
        // TODO: 21/01/2019 Для реализации инекции в хтмл можно сделать чтение файла по строками и поиск в нужной строке якорных тегов для подмены значений
        // TODO: 16.01.2019 вынести в отдельный метод
        this.contentLength = getDataBytesCount(data);
        this.contentType = contentType;
        this.header = getHeader(code);

    }
    private long  getDataBytesCount(String tempData) {

        File tempForCountBytes = new File(Objects.requireNonNull(Response.class.getClassLoader().getResource(tempData)).getFile());
        if(tempForCountBytes.exists() && !tempForCountBytes.isDirectory()){
            return tempForCountBytes.length();
        } else {
            return tempData.getBytes().length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("entity.Response:\n");
        sb.append(this.header);
        if(this.data == null)
            sb.append("No data in body");
        else
            sb.append(this.data);
        return sb.toString();
    }
}
