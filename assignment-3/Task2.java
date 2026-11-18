import java.io.*;
import java.net.*;
public class Task2 {
    public static void main(String[] args) throws IOException {
        String source = args.length > 0 ? args[0] : "https://example.com/";
        URL url = URI.create(source).toURL();
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        try (InputStream input = connection.getInputStream();
             OutputStream output = new FileOutputStream("URLRawData.txt")) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
        }
        System.out.println("Saved raw data from " + source + " to URLRawData.txt");
    }
}
