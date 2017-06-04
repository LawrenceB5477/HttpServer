package com.evilfrogindustries.httpserver.context;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IndexContext implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //Get the url request, for debugging
        URI uriObject = httpExchange.getRequestURI();
        System.out.println("The requested uri: "+ uriObject);

        //Get the paths to the files I need. TODO Dynamically generate paths
        Path indexPath = Paths.get("C:/Users/Lbadv/Desktop/Java Programming/HttpServer/src/Resources/index.html");
        List<String> htmlFile = Files.readAllLines(indexPath);
        byte[] b = Files.readAllBytes(indexPath);
        List<String> fileUrls = readList(htmlFile);

        //Outputs standard header information.
        Headers header = httpExchange.getResponseHeaders();
        header.add("Content-Type", "text/html");
        httpExchange.sendResponseHeaders(200, b.length);

        //Writes to the http body
        OutputStream responseStream = httpExchange.getResponseBody();
        responseStream.write(b);
        responseStream.close();

    }

    //Returns an ArrayList of strings read from the index.html file. Imperfect.
    public ArrayList<String> readList(List<String> htmlText) {
        ArrayList<String> fileUrls = new ArrayList<String>();

        for (String text : htmlText) {
            if (text.contains("href") && (text.contains(".css") || text.contains(".images"))) {
                int finalQuote = text.lastIndexOf("\"");
                int beginning = text.indexOf("href=\"") + 6;
                String fileUrl = "C:/Users/Lbadv/Desktop/Java Programming/HttpServer/src/Resources/" + text.substring(beginning, finalQuote);
                System.out.println(fileUrl);
                fileUrls.add("fileUrl");
            }
        }
        return fileUrls;
    }


}

