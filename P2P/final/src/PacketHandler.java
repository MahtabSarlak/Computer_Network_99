public class PacketHandler {
    private PacketHandler() {
    }

    private static PacketHandler packetHandler =  new PacketHandler();

    public static PacketHandler getPacketHandler() {
        return packetHandler;
    }

    public String mergeIncomingChunk(String message, String data, int id) {
        String[] params = message.split(",");
        if (Integer.parseInt(params[0]) == id) {
            data += params[1];
            System.out.println(new StringBuilder("Chunk number " + id + " added successfully " + "produced data is : " + data));
        } else {
            System.out.println("Chuck with invalid id was sent");
        }
        return data;
    }
}
