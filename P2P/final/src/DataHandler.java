import java.io.*;

public class DataHandler {
    private static DataHandler dataHandler = new DataHandler();

    public static DataHandler getDataHandler() {
        return dataHandler;
    }

    private DataHandler() {
    }

    public String data(byte[] byteArray) {
        if (byteArray == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] != 0)
                stringBuilder.append((char) byteArray[i]);
        }
        return stringBuilder.toString();
    }

    public void saveToFile(String path, String data) throws IOException {
        FileWriter file = new FileWriter(path);
        BufferedWriter bf = new BufferedWriter(file);
        bf.write(data);
        bf.close();
    }

    public static byte[] readFile(String path)
    {
        File file = new File(path);
        FileInputStream fileInputStream = null;
        byte[] byteFile = new byte[(int) file.length()];
        try {

            fileInputStream = new FileInputStream(file);
            fileInputStream.read(byteFile);
            fileInputStream.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return byteFile;
    }
}
