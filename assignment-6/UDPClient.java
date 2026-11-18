import java.net.*;
import java.util.Scanner;

/**
 * Task 2: UDP calculator client for UDPServer.
 * Sends X and Y, prints the menu, sends the choice, prints the RESULT.
 */
public class UDPClient {
    static final String HOST = "localhost";
    static final int PORT = 9876;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000); // 5 s timeout in case the server is not running
        InetAddress server = InetAddress.getByName(HOST);

        try {
            System.out.print("Enter X: ");
            String x = sc.nextLine().trim();
            System.out.print("Enter Y: ");
            String y = sc.nextLine().trim();

            // 1) send operands, receive the menu
            send(socket, server, x + " " + y);
            String menu = receive(socket);
            System.out.println(menu);
            if (!menu.startsWith("Choose")) return; // server rejected the operands

            // 2) send choice, receive the result
            System.out.print("Your choice (1, 2, 3, 4 or + - * /): ");
            send(socket, server, sc.nextLine().trim());
            System.out.println("RESULT: " + receive(socket));
        } catch (SocketTimeoutException e) {
            System.out.println("No response from server (timeout).");
        } finally {
            socket.close();
        }
    }

    static void send(DatagramSocket s, InetAddress addr, String msg) throws Exception {
        byte[] data = msg.getBytes();
        s.send(new DatagramPacket(data, data.length, addr, PORT));
    }

    static String receive(DatagramSocket s) throws Exception {
        byte[] buf = new byte[1024];
        DatagramPacket p = new DatagramPacket(buf, buf.length);
        s.receive(p);
        return new String(p.getData(), 0, p.getLength());
    }
}
