import serverconfig.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer{

    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(Integer.parseInt(ServerConfig.getConfig().getParam("web.port"))))
        {
            System.out.println("Server started on port: "
                    + serverSocket.getLocalPort() + "\n");

            while(true) {
                Socket clientSocket = serverSocket.accept();

                ClientSession session = new ClientSession(clientSocket);
                new Thread(session).start();
            }

        } catch (IOException e) {
            System.out.println("Failed to establish connection.");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.start();
        //System.out.println(serverconfig.ServerConfig.getConfig().getParam("web.port"));

//        File tempForCountBytes = new File(entity.Response.class.getClassLoader().getResource("www/vendor/bootstrap/css/bootstrap.min.css").getFile());
//        CookieSession session = DBSessionFactory.getSessionFactory().openSession();
//        System.out.println(session.isConnected());
//        entity.User u1 = new User();
//        u1.setEmail("mail");
//        u1.setPassword("222");
//
//        Transaction tr = session.beginTransaction();
//        session.save(u1);
//        tr.commit();
//        session.close();

    }
}
