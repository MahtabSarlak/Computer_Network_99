import java.net.SocketException;
        import java.util.HashMap;
        import java.util.Scanner;

public class Peer {
    private static PeerState state;
    private static HashMap<String, String> storedFiles;

    public static void main(String[] args) throws SocketException {
        storedFiles = init_files();
        Sender sender = new Sender();
        sender.setStoredFiles(storedFiles);
        Receiver receiver= new Receiver();
        System.out.print("[System] Enter your command ->  ");
        Scanner scanner = new Scanner(System.in);
        while(true){
            String input = scanner.nextLine();
            if(input.contains("torrent -setMode upload")){
                state = PeerState.SENDER;
            }
            else if( input.contains("torrent -search")){
                state= PeerState.RECEIVER;
            }

            switch (state){
                case SENDER:
                    for (String filename : sender.getStoredFiles().keySet()) {
                        System.out.println(new StringBuilder("File "+filename+" is available"));
                    }
                    String uploaded_file = input.split(" ")[3];
                    System.out.println(new StringBuilder(uploaded_file + " is uploading!"));
                    sender.send(uploaded_file);
                    break;
                case RECEIVER:
                    for (String filename : receiver.getStoredFiles().keySet()) {
                        System.out.println(new StringBuilder("File "+filename+" is available"));
                    }
                    String requested_file = input.split(" ")[2];
                    System.out.println(new StringBuilder(requested_file + " is requested!"));
                    receiver.receive(requested_file);
                    break;
                default:
                    break;
            }
        }
    }

    private static HashMap<String, String> init_files() {
        HashMap<String, String> files = new HashMap<>();
        files.put("sample1.txt","C:/Users/ASUS/Desktop/network_final/sample1.txt");
//        files.put("sample2.txt","C:/Users/ASUS/Desktop/network_final/sample2.txt");
        return files;
    }
}
