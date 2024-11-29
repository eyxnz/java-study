package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static final int PORT = 8000;

    public static void main(String[] args) {
        List<PrintWriter> listPrintWriter = new ArrayList<PrintWriter>();

        ServerSocket serverSocket = null;

        try {
            // 1. 서버 소켓 생성
            serverSocket = new ServerSocket();

            // 2. 바인딩
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
            consoleLog("Waiting for a client");

            // 3. 요청 대기
            while(true) {
                Socket socket = serverSocket.accept();
                new ChatServerThread(socket, listPrintWriter).start();
            }
        } catch(SocketException e) {
            consoleLog("Socket Exception : " + e);
        } catch(IOException e) {
            consoleLog("Exception : " + e);
        } finally {
            if(serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    consoleLog("Exception : " + e);
                }
            }
        }
    }

    public static void consoleLog(String message) {
        System.out.println("[Chat Server] " + message);
    }
}
