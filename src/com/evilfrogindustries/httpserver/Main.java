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
        new Main().run();
    }

    private void run() {
        startServer();
        makeResourceDirectory();
    }

    /*
    If I set the context to /index.html, it only reads the index.html file, and not
    any of the link files in it. If i set the context to "/" and navigate to /index.html
    in my browser, it works. I'm not sure why.
    */
    private void startServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(80), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/", new IndexContext());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    private void makeResourceDirectory() {
        //If the resources directory does not exist, create it.
        Path contentDirectory = Paths.get("./Resources");
        if (!Files.exists(contentDirectory)) {
            System.out.println("Content path does not exist. Creating.");
            try {
                File contentDirectoryFile = new File(contentDirectory.toString());
                contentDirectoryFile.mkdirs();
            } catch (SecurityException e) {
                System.out.println("Could not make the content path.");
            }
        }
    }

}
