import customexception.RestException;
import dbhandler.dao.ServiceLogService;
import entity.ServiceLog;
import entity.Request;
import entity.Response;
import factory.HttpFactory;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class ClientSession implements Runnable {
    private Socket socket;
    private Request request;
    private Response response;
    private InputStream inv2;// for method runWithException()
    private OutputStream outv2;//
    Date dateNow = Calendar.getInstance().getTime();

    public ClientSession(Socket socket) {
        this.socket = socket;
    }


    //@Override
//    public void run22() {
//
//        try(
//                InputStream in = socket.getInputStream();
//                OutputStream out = socket.getOutputStream()
//        ) {
//
//            this.request = new Request();
//            this.request.readRequestData(in);
//            System.out.println(this.request.toString());
//
//            HttpFactory factory = new HttpFactory();
//
//            this.response = factory.getHandler(this.request).getResponseResult();
//
//            System.out.println(this.response.toString());
//            sendResponse(this.response, out);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            }
//        }
        @Override
        public void run() {

            try {
                inv2 = socket.getInputStream();
                outv2 = socket.getOutputStream();

                this.request = new Request();
                this.request.readRequestData(inv2);

                System.out.println(this.request.toString());

                HttpFactory factory = new HttpFactory();

                this.response = factory.getHandler(this.request).getResponseResult();

                System.out.println(this.response.toString());

                sendResponse(this.response, outv2);

                //если все ок, то записываем в сервисый лог результаты
                ServiceLogService logService = new ServiceLogService();
                logService.save(new ServiceLog(this.request.toString(), this.response.toString(),null, dateNow));

            } catch (IOException e) {
                // TODO: 06/02/2019 нужно сделать свой класс исключений
                ServiceLogService logService = new ServiceLogService();
                logService.save(new ServiceLog(this.request.toString(),null, e.getMessage() + Arrays.toString(e.getStackTrace()), dateNow));

//                sendResponse(Response.getInstanceError(400, e.getMessage() + Arrays.toString(e.getStackTrace())), outv2);

            } catch (RestException e) {
                // TODO: 06/02/2019 нужно сделать свой класс исключений
                ServiceLogService logService = new ServiceLogService();
                logService.save(new ServiceLog(this.request.toString(), null, e.getMessage() + Arrays.toString(e.getStackTrace()), dateNow));

                String errorCode = e.getMessage().substring(0,3).trim();
                String errorMessage = e.getMessage().substring(3).trim();

                switch (errorCode) {
                    case "400":
                        sendResponse(Response.getInstanceError(400, errorMessage), outv2);
                        break;
                    case "501":
                        sendResponse(Response.getInstanceError(501, errorMessage), outv2);
                    default:
                }

            } catch (Exception e) {
                // TODO: 06/02/2019 Запись в лог веб сервера
            } finally {

                try {
                    inv2.close();
                    outv2.close();
                } catch (IOException e) {
                    //запись в лог сервера
                }
            }
        }


    private void sendResponse(Response response, OutputStream out) {

        try {
            writeData(response.getHeaderStream(), out);
            InputStream tmp = response.getDataStream();

            if (!(tmp == null))
                writeData(tmp, out);

        } catch (IOException e) {
            //запись в лог сервера .txt
        }
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

