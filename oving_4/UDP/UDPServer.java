package oving_4.UDP;

import java.io.IOException;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) throws IOException{

        final int PORT_NR = 1250;
        try (DatagramSocket serverSocket = new DatagramSocket(PORT_NR)){
            System.out.println("Server is online");
            while (true) {
                new UDPThread(serverSocket).run();
            }
        } catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
