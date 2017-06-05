package com.evilfrogindustries.httpserver.context;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexContext implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //Get the url request, for debugging
        URI uriObject = httpExchange.getRequestURI();
        System.out.println("The requested uri: "+ uriObject);

        //Get the paths to the files I need.
        File test = new File("./src/Resources" + httpExchange.getRequestURI().getPath());
        Path indexPath = Paths.get(test.getCanonicalPath());
        byte[] b = Files.readAllBytes(indexPath);

        System.out.println("This is where we are.: " + test.getCanonicalPath());

        //Outputs standard header information.
        //Headers header = httpExchange.getResponseHeaders();
        //header.add("Content-Type", "text/html");
        httpExchange.sendResponseHeaders(200, b.length);

        //Writes to the http body
        OutputStream responseStream = httpExchange.getResponseBody();
        responseStream.write(b);
        responseStream.close();

    }

}

