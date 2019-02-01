package entity;

import serverconfig.ServerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    private Map<String, String> header;
    private Map<String, String> params;
    private String body;
    private String url;
    private String method;
    private String firstLine;
//    private boolean isBadRequest;


    public Request() {
        this.header = new LinkedHashMap<>();
        this.params = new LinkedHashMap<>();
        this.firstLine = null;
        this.body = null;
        this.url = null;
        this.method = null;


    }

    public void readRequestData(InputStream inputStream) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();

        String line;
        String[] tmp;

        //Читаем первую строку запроса

        while(!reader.ready()) {
            //System.out.println("Ожидание потока данных от клиента");
            //ждем пока данные в потоке будут готовы для чиения
        }

        this.firstLine = reader.readLine();

        if(!this.firstLine.isEmpty() && !this.firstLine.startsWith(" ")) {
            // проверяем что строка имеет корректный вид и разбиваем ее на 3 части 1. метод 2. Url 3.
            tmp = this.firstLine.split(" ");
            if(!(tmp.length < 3)) {
                this.method = tmp[0];  //Вычисляем метод запроса

                getUrlAndParams(tmp[1]); // парсим Uri на URl и параметры строки если они есть
            }
        }

        // читаем хеадеры запроса пока не встретим разделитель в виде пустой строки и складываем их в мап по порядку
        while(!(line = reader.readLine()).isEmpty()) {
            int separator = line.indexOf(":");
            this.header.put(line.substring(0, separator).toLowerCase(), (line.substring(separator + 1)).trim());
        }

        // если есть тело запроса то продолжаем чтение уже по байтам
        while(reader.ready()) {
            builder.append((char) reader.read());
        }

        //помещаем в результирующую строку
        this.body = builder.toString();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("entity.Request:\n");
        sb.append(firstLine).append("\n");

        for(Map.Entry entry : header.entrySet()) {
            sb.append(entry.getKey() + ": " + entry.getValue()).append("\n");
        }
        sb.append("\r\n");
        if(!(body == null))
            sb.append(body);
        return sb.toString();
    }

    private void getUrlAndParams(String s) throws Exception{

        int paramIndex = s.indexOf("?");
        if (paramIndex != -1) {

            this.url = ServerConfig.getConfig().getParam("web.default_files_dir") + s.substring(0, paramIndex);
            int len = s.length();
            String paramsString = s.substring(paramIndex + 1, len);

            if(paramsString.contains("&")) {

                String[] a = paramsString.split("&");

                for (String str : a) {

                    if(str.contains("=")) {

                        String[] b = str.split("=");

                        if(b.length == 2) {
                            this.params.put(b[0], b[1]);
                        }
                    }// TODO: 19.01.2019 добавить в ответ что параметры не корректны 
                }
            } else if(paramsString.contains("=")) {

                String[] b = paramsString.split("=");

                if(b.length == 2) {
                    this.params.put(b[0], b[1]);
                }
            } else {

                // TODO: 28/01/2019  заглушка
            }
        } else {
            this.url = ServerConfig.getConfig().getParam("web.default_files_dir") + s.trim();

        }
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
