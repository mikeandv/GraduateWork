import factory.Handler;
import entity.Request;
import entity.Response;
import factory.HttpFactory;

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

            HttpFactory factory = new HttpFactory();


            this.response = factory.getHandler(this.request).getResponseResult();
                    //handler.requestProceed(this.request);

            System.out.println(this.response.toString());
            sendResponse(response, out);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(Response response, OutputStream out) throws IOException {

        writeData(response.getHeader(), out);

        InputStream tmp = response.getData();
        if(!(tmp == null))
            writeData(tmp, out);
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

