package seedu.duke.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import seedu.duke.Duke;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

//code adapted from https://stackoverflow.com/questions/3732109/simple-http-server-in-java-using-only-java
// -se-api
public class SimpleServer {
    private static HttpServer server;

    public static void startServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(3000), 0);
            server.createContext("/", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException e) {
            Duke.getUI().showError("Server setup failed...");
        }
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Authorization/Authentication finished";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            parseAuthCode(exchange.getRequestURI().getQuery());
            server.stop(200);
        }
    }

    private static void parseAuthCode(String url) {
        int index = url.indexOf("code=");
        if (index == -1) {
            Duke.getUI().showError("Auth code parsing failed: " + url);
            return;
        }
        index += 5;
        String code = url.substring(index);
        Http.setAuthCode(code);
    }
}
