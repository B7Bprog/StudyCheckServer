import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ConnectionCheck extends Thread {


    protected DatagramSocket socket;


    @Override
    public void run() {
        System.out.println("New thread started");


        while (true) {
            System.out.println("Iterated in server triggerThread");

            try {
                Thread.sleep(58000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            byte[] data2 = ("*** Connection check - LIVE ***").getBytes();

            for (int i = 0; i < ChatServer.clientAddresses.size(); i++) {
                InetAddress cl = ChatServer.clientAddresses.get(i);
                int cp = ChatServer.clientPorts.get(i);
                DatagramPacket packet = new DatagramPacket(data2, data2.length, cl, cp);
                try {
                    ChatServer.socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }


    }
}
