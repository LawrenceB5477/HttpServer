package com.evilfrogindustries.httpserver;

import com.evilfrogindustries.httpserver.context.IndexContext;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private HttpServer server;

    public static void main(String[] args) {
        Main main = new Main();
        main.startServer();
    }

    public void startServer() {
        //If the resources directory does not exist, create it.
        Path contentDirectory = Paths.get("./src/Resources");
        if (!Files.exists(contentDirectory)) {
            System.out.println("Content path does not exist. Creating.");
            try {
                File contentDirectoryFile = new File(contentDirectory.toString());
                contentDirectoryFile.mkdirs();
            } catch (SecurityException e) {
                System.out.println("Could not make the content path.");
            }
        }

        try {
            server = HttpServer.create(new InetSocketAddress(80), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*If I set the context to /index.html, it only reads the index.html file, and not
        any of the link files in it. If i set the context to "/" and navigate to /index.html
        in my browser, it works. I'm not sure why.
         */
        //Add the possible contexts the server can handle
        server.createContext("/", new IndexContext());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

}
