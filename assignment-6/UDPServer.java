import java.net.*;
import java.util.*;

/**
 * Task 1: UDP calculator server.
 * Protocol:
 *   1) Client sends "X Y" (two ints)  -> server replies with the operation menu.
 *   2) Client sends 1/2/3/4 or + - * / -> server replies with the integer RESULT,
 *      or "Wrong choice!" if the input is anything else.
 * State is kept per client (address:port), so several clients can be served.
 */
public class UDPServer {
    static final int PORT = 9876;

    static final String MENU =
            "Choose an operation\n" +
            "1. Addition (+)\n" +
            "2. Subtraction (-)\n" +
            "3. Multiplication (*)\n" +
            "4. Division (/)";

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(PORT);
        System.out.println("UDP server listening on port " + PORT);

        // Operands waiting for an operation choice, keyed by client "address:port"
        Map<String, int[]> pending = new HashMap<>();
        byte[] buf = new byte[1024];

        while (true) {
            DatagramPacket in = new DatagramPacket(buf, buf.length);
            socket.receive(in);
            String msg = new String(in.getData(), 0, in.getLength()).trim();
            String key = in.getAddress().getHostAddress() + ":" + in.getPort();
            String reply;

            if (!pending.containsKey(key)) {
                // Step 1: expect two integer operands "X Y"
                try {
                    String[] parts = msg.split("\\s+");
                    if (parts.length != 2) throw new NumberFormatException();
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    pending.put(key, new int[]{x, y});
                    reply = MENU;
                } catch (NumberFormatException e) {
                    reply = "Wrong input! Send two integers: X Y";
                }
            } else {
                // Step 2: expect the operation choice
                int[] ops = pending.remove(key);
                int x = ops[0], y = ops[1];
                switch (msg) {
                    case "1": case "+": reply = String.valueOf(x + y); break;
                    case "2": case "-": reply = String.valueOf(x - y); break;
                    case "3": case "*": reply = String.valueOf(x * y); break;
                    case "4": case "/":
                        reply = (y == 0) ? "Error: division by zero!" : String.valueOf(x / y);
                        break;
                    default: reply = "Wrong choice!";
                }
            }

            byte[] data = reply.getBytes();
            socket.send(new DatagramPacket(data, data.length, in.getAddress(), in.getPort()));
            System.out.println("[" + key + "] received: \"" + msg + "\" -> sent: \"" + reply.replace("\n", " | ") + "\"");
        }
    }
}
