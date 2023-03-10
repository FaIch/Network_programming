package oving_4.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPClient {

    public static void main(String[] args) {
        final int PORT_NR = 1250;
        try{
            final DatagramSocket clientSocket = new DatagramSocket();
            InetAddress address = InetAddress.getLocalHost();
            Scanner in = new Scanner(System.in);
            byte[] bytes;

            while(true){
                System.out.println("Input complete math expression (only adding and subtracting): ");
                
                String input = in.nextLine();
                input = input.replaceAll("\\s", "");
                bytes = new byte[65535];
                bytes = input.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, address, PORT_NR);
                clientSocket.send(sendPacket);

                if(input.trim().isBlank()){
                    break;
                }

                bytes = new byte[65535];
                DatagramPacket DpReceive = new DatagramPacket(bytes, bytes.length);
                clientSocket.receive(DpReceive);

                String result = new String(bytes, 0, bytes.length);
                    result = result.trim();
                    System.out.println(result);
            }
        }catch(IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
