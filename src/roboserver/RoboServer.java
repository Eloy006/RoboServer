/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roboserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Evgeny.Alekseev
 */
public class RoboServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Throwable {
       /*   ServerSocket ss = new ServerSocket(8081);
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            new Thread(new HttpServer.SocketProcessor(s)).start();
        }*/
       WebServer.main(args);
        
        // TODO code application logic here
    }
    
}
