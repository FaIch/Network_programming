package oving_3;

import java.io.*;
import java.net.*;

public class SocketServer {
    public static void main(String[] args) throws IOException{

        final int PORT_NR = 1250;
         InetAddress inetAddress = InetAddress.getByName("192.168.0.77");
        int nrOfConnections = 0;
        try (ServerSocket serverSocket = new ServerSocket(PORT_NR)){
            System.out.println("Server is online");
            while (true) {
                new SocketThread(serverSocket.accept()).start();
                nrOfConnections++;
                System.out.println("New connection: " + nrOfConnections);
            }
        } catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
