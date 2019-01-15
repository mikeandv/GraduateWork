import java.io.*;
import java.net.Socket;


public class ClientSession implements Runnable {
    private Socket socket;
    private Request request;
    private Response response;

    public ClientSession(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {

        try(
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream()
        ) {

            this.request = new Request();
            this.request.readRequestData(in);
            System.out.println(this.request.toString());

            HttpHandler handler = new HttpHandler();

            this.response = handler.requestProceed(this.request);
            sendResponse(response, out);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(Response response, OutputStream out) throws IOException{

//        InputStream strm = HttpServer.class.getResourceAsStream(request.getUrl());
//        int code = (strm != null) ? 200 : 404;
//        String header = getHeader(code);
//        PrintStream answer = new PrintStream(out, true, "UTF-8");
//        answer.print(header);
//        if (code == 200) {
//            int count = 0;
//            byte[] buffer = new byte[1024];
//            while((count = strm.read(buffer)) != -1) {
//                out.write(buffer, 0, count);
//            }
//            strm.close();
//        }
        writeData(response.getHeader(), out);
        writeData(response.getData(), out);
    }

    private void writeData(InputStream inRead, OutputStream outWrite) throws IOException {


        BufferedInputStream buffRead = new BufferedInputStream(inRead);
        BufferedOutputStream buffOut = new BufferedOutputStream(outWrite);

        byte[] buffer = new byte[1024];
        int len;

        while ((len = buffRead.read(buffer)) != -1) {
            buffOut.write(buffer, 0, len);
            buffOut.flush();
        }
        buffRead.close();
    }
}

