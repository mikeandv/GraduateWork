import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;

public class Response {
    private final String[][] HTTP_REPLIES = {
            {"200", "OK"},
            {"404", "Not Found"},
            {"400", "Bad Request"},
            {"401", "Unauthorized"},
            {"500", "Internal Server Error"}
    };
    //private int code;
    private String header;
    private String data;

    public Response() {
    }

    private String getHeader(int code) {
        DateFormat d = DateFormat.getDateInstance();
        StringBuilder buffer = new StringBuilder();

        if(code == 200) {
            buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
            buffer.append("WWW-Authenticate: Basic realm=\"User Visible Realm\"");
            buffer.append("Date: " + d.format(new Date()) + "\n");
            buffer.append("Accept-Ranges: none\n");
            buffer.append("Content-Type: " + "text/html" + "\n");
            buffer.append("\n");
        } else if(code == 401){
            buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
            buffer.append("WWW-Authenticate: Basic realm=\"User Visible Realm\"");
        } else {
            buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
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
            return Response.class.getClassLoader().getResourceAsStream(data);
//        }
    }

    public InputStream getHeader() {
        return new ByteArrayInputStream(header.getBytes());
    }


    public void buildResponse(int code, String data) {
        this.header = getHeader(code);
        this.data = data;
    }

}
