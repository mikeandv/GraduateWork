import dbhandler.DBSessionFactory;
import org.hibernate.Session;
import serverconfig.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer{

    public void start() {
        Session sessionDB = DBSessionFactory.getSessionFactory().openSession();
        if(sessionDB.isConnected())
            System.out.println("Database is connected");

        ExecutorService pool = Executors.newFixedThreadPool(2); // TODO: 06/02/2019 вынести в переменную
        try(ServerSocket serverSocket = new ServerSocket(Integer.parseInt(ServerConfig.getConfig().getParam("web.port"))))
        {
            System.out.println("Server started on port: "
                    + serverSocket.getLocalPort() + "\n");

            while(true) {
                Socket clientSocket = serverSocket.accept();

//                ClientSession session = new ClientSession(clientSocket);
//                new Thread(session).start();
                pool.execute(new ClientSession(clientSocket));
            }

        } catch (IOException e) {
            System.out.println("Failed to establish connection."); // TODO: 06/02/2019 Если долетело исключение нужно записать его в логи сервера, например ошибка отправки
            System.out.println(e.getMessage());
            pool.shutdown();
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.start();

    }
}
