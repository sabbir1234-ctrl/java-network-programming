import java.io.*;
import java.net.*;
import java.util.Scanner;

// Task 2: TCP client for the calculator server from Task 1.
public class Task2 {
    static final int PORT = 5000;

    // Reads lines until an empty line (end of one server message).
    static String readMessage(BufferedReader in) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            if (sb.length() > 0) sb.append("\n");
            sb.append(line);
        }
        return sb.toString();
    }

    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "localhost";
        Scanner sc = new Scanner(System.in);

        try (Socket socket = new Socket(host, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)) {

            // 1) Send X and Y
            int x = readInt(sc, "Enter X: ");
            int y = readInt(sc, "Enter Y: ");
            out.println(x + " " + y);

            // 2) Show the menu
            System.out.println(readMessage(in));

            // 3) Send choice until the server returns the result
            while (true) {
                System.out.print("Your choice (1-4 or + - * /): ");
                out.println(sc.nextLine().trim());
                String reply = readMessage(in);
                System.out.println(reply);
                if (!reply.startsWith("Wrong choice")) break;
            }
        }
    }
}
