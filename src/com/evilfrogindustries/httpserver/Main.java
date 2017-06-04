package com.evilfrogindustries.httpserver;

import com.evilfrogindustries.httpserver.context.IndexContext;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    private HttpServer server;
    public static void main(String[] args) {
        Main main = new Main();
        main.startServer();
    }

    public void startServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(80), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Add the possible contexts the server can handle
        server.createContext("/", new IndexContext());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

}
