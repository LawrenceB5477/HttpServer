package com.evilfrogindustries.httpserver.context;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;

//TODO send proper file-type, get rid of .. input
public class IndexContext implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) {
        URI uriObject = httpExchange.getRequestURI();
        System.out.println("The requested uri: " + uriObject);

        //Sends the requested file.
        try {
            File test = null;

            if (uriObject.toString().equals("/") || uriObject.toString().equals("\\")) {
                uriObject = URI.create(httpExchange.getRequestURI().toString() + "index.html");
                test = new File("./Resources" + uriObject.getPath());

            } else {
                test = new File("./Resources" + httpExchange.getRequestURI().getPath());
            }

            //Send the response.
            byte[] b = createByteArray(test);
            httpExchange.sendResponseHeaders(200, b.length);
            writeHttpBody(httpExchange.getResponseBody(), b);

        } catch (IOException e) {
            e.printStackTrace();
            sendResponse404(httpExchange, "<h1>404 Not Found.</h1>");

        } finally {
            try {
                httpExchange.getResponseBody().close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }



    //Methods that drive the main context.
    //Will be used to set the file type
    public void setHttpHeaderFile(String canonPath) {

    }

    public void writeHttpHeader(HttpExchange httpExchange, byte[] b, String text) throws IOException {
        Headers header = httpExchange.getResponseHeaders();
        header.add("Content-Type", text);
        httpExchange.sendResponseHeaders(200, b.length);
    }

    public void writeHttpBody(OutputStream responseStream, byte[] b) throws IOException {
        responseStream = new BufferedOutputStream(responseStream);
        responseStream.write(b, 0, b.length);
        responseStream.flush();
    }

    public void sendResponse404(HttpExchange httpExchange, String errorMessage) {
        try {
            httpExchange.sendResponseHeaders(404, 0);
            byte[] b = errorMessage.getBytes();
            writeHttpBody(httpExchange.getResponseBody(), b);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] createByteArray(File file) throws IOException{
        InputStream in = null;
        ByteArrayOutputStream ous = null;
        try {
            byte[] buffer = new byte[4096];
            in = new BufferedInputStream(new FileInputStream(file));
            ous = new ByteArrayOutputStream();
            int read = 0;

            //Use the buffered InputStream so that the method keeps reading until the buffer is full.
            while ((read = in.read(buffer, 0, buffer.length)) != -1) {
                ous.write(buffer, 0, read);
            }

        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println("Problems closing the file we are reading from.");
            }

            try {
                if(ous != null) {
                    ous.close();
                }
            } catch (IOException e) {
                System.out.println("Problems closing the output stream to create the byte array.");
            }
        }
        return ous.toByteArray();
    }
}

