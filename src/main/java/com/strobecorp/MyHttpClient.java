package com.strobecorp;

import java.io.File;
import java.io.FileInputStream;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.client.HttpClient;
import java.io.*;


public class MyHttpClient  {
    public static void main(String[] args) throws java.io.IOException {
        HttpClient client = new HttpClient();
        client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
        try {
            client.start();
        } catch(java.lang.Exception e) {
            System.out.print(e);            
        }

        HttpExchange exchange = new HttpExchange();
        exchange.setMethod("POST");
        exchange.setURL("http://127.0.0.1:4001/");

        final PipedInputStream in = new PipedInputStream();
        final PipedOutputStream out = new PipedOutputStream(in);

        Thread thread = new Thread(
                   new Runnable(){
                       public void run(){
                           try {
                               File file = null;
                               FileInputStream stream = null;
                               file = new File("/Users/drogus/projekty/strobe/kirk/data");
                               try {
                                   stream = new FileInputStream(file);
                               } catch(FileNotFoundException e) {
                                   System.out.print(e);
                               }
                               byte[] buf = new byte[4096];
                               int length;
                               BufferedInputStream bif = new BufferedInputStream(stream, 4096);
                               while ( (length = bif.read(buf) ) >= 0) {
                                   System.out.println(length);
                                   out.write(buf, 0, length);
                               }

                               in.close();
                               out.close();
                           } catch(java.io.IOException e) {
                               System.out.print(e);
                           }
                       }
                   });

        thread.start();

        exchange.setRequestHeader("Transfer-Encoding", "chunked");
        exchange.setRequestContentSource(in);;
 
        try {
            client.send(exchange);
        } catch(java.io.IOException e) {
            System.out.print(e);
        }

        try {
            thread.join();
        } catch(java.lang.InterruptedException e) {
            System.out.print(e);
        }

        try {
            exchange.waitForDone();
        } catch(java.lang.InterruptedException e) {
            System.out.print(e);
        }
    }
}
