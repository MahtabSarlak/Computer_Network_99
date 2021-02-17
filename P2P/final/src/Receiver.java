import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class Receiver {
    private HashMap<String, String> storedFiles= new HashMap<>();
    private final String BROADCAST_IP = "255.255.255.255";
    private final int BROADCAST_PORT = 7680;
    private final int CHUNK_SIZE = 200;

    public void receive(String filename) {
        try {
            // create datagram socket
            DatagramSocket socket = new DatagramSocket();
            System.out.println("Socket has been created");

            //broadcast torrent -search filename request
            String request = "torrent -search " + filename;

            broadcastRequest(request, socket);

            //wait for receiving
            String received_data = handleRecieving(socket);

            //save received file
            String path = new StringBuilder("C:/Users/ASUS/Desktop/network_final/" + filename + "_received").toString();
            DataHandler.getDataHandler().saveToFile(path, received_data);
            storedFiles.put(filename, path);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private String handleRecieving(DatagramSocket socket) throws IOException {
        byte[] buffer = new byte[CHUNK_SIZE];
        String data = "";
        int id = 0;
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer,0, buffer.length);
            socket.receive(packet);
            String tmp = DataHandler.getDataHandler().data(buffer);
            if (!tmp.equals("end_req_")) {
                data = PacketHandler.getPacketHandler().mergeIncomingChunk(tmp, data, id);
                id++;
            } else {
                System.out.println("Packet has been received");
                return data;
            }
        }
    }

    private void broadcastRequest(String request, DatagramSocket socket) throws IOException {
        byte[] buffer = request.getBytes();
        InetAddress inetAddress = InetAddress.getByName(BROADCAST_IP);
        DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length, inetAddress, BROADCAST_PORT);
        socket.send(datagramPacket);
        System.out.println("Broadcast request packet has been sent");
    }

    public HashMap<String, String> getStoredFiles() {
        return storedFiles;
    }

    public void setStoredFiles(HashMap<String, String> storedFiles) {
        this.storedFiles = storedFiles;
    }

    public Receiver() {
    }

    public Receiver(HashMap<String, String> storedFiles) {
        this.storedFiles = storedFiles;
    }

}
