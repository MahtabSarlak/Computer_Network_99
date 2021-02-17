import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Sender {
    private HashMap<String, String> storedFiles=new HashMap<>();
    private final String LOCAL_IP = "0.0.0.0";
    private final int DEFAULT_PORT = 7680;
    private final int CHUNK_SIZE = 200;
    private final int CHUNK_UDP = 8;


    public void send(String filename) {
        try {
            //create datagram socket
            InetAddress inetAddress = InetAddress.getByName(LOCAL_IP);
            DatagramSocket socket = new DatagramSocket(DEFAULT_PORT, inetAddress);
            System.out.println("Socket has been created");

            // get request
            byte[] buffer = new byte[CHUNK_SIZE];
            DatagramPacket request = new DatagramPacket(buffer, 0, buffer.length);
            socket.receive(request);

            if(!DataHandler.getDataHandler().data(buffer).equals("torrent -search "+filename)){
                System.out.println("Invalid Request Input!");
                return;
            }
            System.out.println(DataHandler.getDataHandler().data(buffer));
            System.out.println(filename);

            sendFile(socket,request,filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFile(DatagramSocket socket, DatagramPacket request, String filename) {
        String path = null;
        //check wether it has the requested file
        for (String file:storedFiles.keySet()) {
            if(file.equals(filename)){
                path = storedFiles.get(file);
            }
        }
        //doesnt have it , return
        System.out.println(path);
        if(path == null){
            System.out.println(new StringBuilder(filename+" is not available"));
            return;
        }
        try {
            //else send the file
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            long len = file.length();
            int chunkNumber=(int)(len/CHUNK_UDP);
            if(len % CHUNK_UDP != 0 ){
                chunkNumber+=1;
            }

            for (int i = 0; i < chunkNumber; i++) {
                char[] chunkChar= new char[CHUNK_UDP];
                br.read(chunkChar,0,CHUNK_UDP);
                String sendChunk=i+","+String.valueOf(chunkChar);
                byte[] buffer =sendChunk.getBytes();
                DatagramPacket  packet = new DatagramPacket(buffer,0,buffer.length,request.getAddress(),request.getPort());
                socket.send(packet);
                System.out.println("Chunk number "+i+ " has been sent.");
            }
            byte[] buffer =("end_req_").getBytes();
            DatagramPacket  packet = new DatagramPacket(buffer,0,buffer.length,request.getAddress(),request.getPort());
            socket.send(packet);
            System.out.println("Last chunk has been sent !");
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sender(HashMap<String, String> storedFiles) {
        this.storedFiles = storedFiles;
    }

    public void setStoredFiles(HashMap<String, String> storedFiles) {
        this.storedFiles = storedFiles;
    }

    public Sender() {
    }

    public HashMap<String, String> getStoredFiles() {
        return storedFiles;
    }



}
