import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ChatServer extends Thread {

    public final static int PORT = 88;
    protected final static int BUFFER = 1024;
    protected static DatagramSocket socket;
    protected static ArrayList<InetAddress> clientAddresses;
    protected static ArrayList<Integer> clientPorts;
    protected static HashSet<String> existingClients;
    protected static byte[] buf = new byte[BUFFER];

    public ChatServer() throws IOException {
        socket = new DatagramSocket(PORT);
        clientAddresses = new ArrayList();
        clientPorts = new ArrayList();
        existingClients = new HashSet();
    }

    public void run() {
        while (true) {

            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            try {
                Arrays.fill(buf, (byte) 0);
                socket.receive(packet);

                String content = new String(buf, buf.length);

                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                String id = clientAddress.toString() + "," + clientPort;
                if (!existingClients.contains(id)) {
                    existingClients.add(id);
                    clientPorts.add(clientPort);
                    clientAddresses.add(clientAddress);
                }

                System.out.println(id + " : " + content);
                byte[] data = (content).getBytes();
                //byte[] data = (id + " : " + content).getBytes();
                for (int i = 0; i < clientAddresses.size(); i++) {
                    InetAddress cl = clientAddresses.get(i);
                    int cp = clientPorts.get(i);
                    packet = new DatagramPacket(data, data.length, cl, cp);
                    //packet = new DatagramPacket(data, data.length, cl, cp);
                    socket.send(packet);
                }

            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public static void main(String args[]) throws Exception {

        ChatServer s = new ChatServer();
        ConnectionCheck c = new ConnectionCheck();
        s.start();
        c.start();
    }
}
