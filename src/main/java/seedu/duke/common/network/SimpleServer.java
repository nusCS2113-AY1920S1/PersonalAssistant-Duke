package seedu.duke.common.network;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import seedu.duke.Duke;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * A simple local server designed to receive authorization code from Outlook through redirect.
 */
//@@author BalusC
//code adapted from https://stackoverflow.com/questions/3732109/simple-http-server-in-java-using-only-java-se-api
public class SimpleServer {
    private static HttpServer server;

    /**
     * Starts the server on port 3000.
     */
    static void startServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(3000), 0);
            server.createContext("/", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException e) {
            Duke.getUI().showError("Server setup failed...");
        }
    }

    /**
     * Handler used to handle the response of the http request from Outlook.
     */
    static class MyHandler implements HttpHandler {
        /**
         * Handles the exchange/response of the http request from Outlook, which contains the authorization
         * code. Stops the server once received the code.
         *
         * @param exchange incoming exchange from the Outlook
         * @throws IOException exception if the exchange fails to receive the information from request
         */
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
//@@author