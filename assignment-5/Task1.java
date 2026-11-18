import java.io.*;
import java.net.*;

// Task 1: TCP server (calculator).
// Protocol: every server message ends with an empty line.
public class Task1 {
    static final int PORT = 5000;

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT + "...");
            while (true) {
                Socket socket = server.accept();
                new Thread(() -> handleClient(socket)).start();
            }
        }
    }

    static void send(PrintWriter out, String text) {
        out.println(text);
        out.println();   // empty line = end of message
    }

    static void handleClient(Socket socket) {
        System.out.println("Connected: " + socket.getRemoteSocketAddress());
        try (socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)) {

            // 1) Receive operands X and Y
            String line = in.readLine();
            if (line == null) return;
            int x, y;
            try {
                String[] parts = line.trim().split("\\s+");
                x = Integer.parseInt(parts[0]);
                y = Integer.parseInt(parts[1]);
            } catch (Exception e) {
                send(out, "Error: please send two integers, e.g. 10 5");
                return;
            }

            // 2) Send the menu
            send(out, "Choose an operation\n1. Addition (+)\n2. Subtraction (-)\n"
                    + "3. Multiplication (*)\n4. Division (/)");

            // 3) Repeat until a proper choice is received
            while (true) {
                String choice = in.readLine();
                if (choice == null) return;
                choice = choice.trim();

                char op;
                switch (choice) {
                    case "1": case "+": op = '+'; break;
                    case "2": case "-": op = '-'; break;
                    case "3": case "*": op = '*'; break;
                    case "4": case "/": op = '/'; break;
                    default:
                        send(out, "Wrong choice! Choose again");
                        continue;
                }

                if (op == '/' && y == 0) {
                    send(out, "Error: division by zero");
                } else {
                    int result = switch (op) {
                        case '+' -> x + y;
                        case '-' -> x - y;
                        case '*' -> x * y;
                        default  -> x / y;   // int division
                    };
                    send(out, "RESULT: " + result);
                }
                return;
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
