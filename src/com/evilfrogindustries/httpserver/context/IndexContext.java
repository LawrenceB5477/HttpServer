package com.evilfrogindustries.httpserver.context;

import com.sun.net.httpserver.Headers;
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

        URI uriObject = httpExchange.getRequestURI();
        System.out.println("The requested uri: " + uriObject);

        //Test to see if the index should be sent.
        if (uriObject.toString().equals("/") || uriObject.toString().equals("\\")) {
            try {
                uriObject = URI.create(httpExchange.getRequestURI().toString() + "index.html");

                //Get the data for the file requested by the path.
                File test = new File("./src/Resources" + uriObject.getPath());
                Path indexPath = Paths.get(test.getCanonicalPath());
                byte[] b = Files.readAllBytes(indexPath);

                //Outputs standard header information.
                Headers header = httpExchange.getResponseHeaders();
                header.add("Content-Type", "text/html");
                httpExchange.sendResponseHeaders(200, b.length);

                //Writes to the http body
                OutputStream responseStream = httpExchange.getResponseBody();
                responseStream.write(b);
            } catch (Exception e) {
                httpExchange.sendResponseHeaders(404, 0);
                String error = "<h1>Put the index file in /Resources/.</h1>";
                byte[] b = error.getBytes();
                httpExchange.getResponseBody().write(b);
            } finally {
                httpExchange.getResponseBody().close();
            }
        }
        //Handles sending all files besides the index.
        //TODO send codes for different file types.
        try {
            //Read all files index.html needs.
            File test = new File("./src/Resources" + httpExchange.getRequestURI().getPath());
            Path indexPath = Paths.get(test.getCanonicalPath());
            byte[] b = Files.readAllBytes(indexPath);

            httpExchange.sendResponseHeaders(200, b.length);

            //Writes to the http body
            OutputStream responseStream = httpExchange.getResponseBody();
            responseStream.write(b);
        } catch (Exception e) {
            httpExchange.sendResponseHeaders(404, 0);
            String error = "<h1>404 not found.</h1>";
            byte[] b = error.getBytes();
            httpExchange.getResponseBody().write(b);
        } finally {
            httpExchange.getResponseBody().close();
        }

    }

}

