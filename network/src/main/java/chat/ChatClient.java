package chat;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ChatClient {
    public static final String IP = "0.0.0.0"; // localhost
    public static final int PORT = 8000;

    public static void main(String[] args) {
        Scanner scanner = null;
        Socket socket = null;

        try {
            // 1. 키보드 연결
            scanner = new Scanner(System.in);

            // 2. Socket 생성
            socket = new Socket();

            // 3. 연결
            socket.connect(new InetSocketAddress(IP, PORT));

            // 4. reader/writer 생성
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8),true);

            // 5. join 프로토콜
            System.out.print("닉네임 >> ");
            String nickname = scanner.nextLine();
            pw.println("join:" + nickname);
            pw.flush();

            // 6. ChatClientThead 시작
            new ChatClientThread(socket, br).start();

            // 7. 키보드 입력 처리
            while(true) {
                System.out.print(">> ");
                String input = scanner.nextLine();

                if("quit".equals(input)) {
                    pw.println("quit:" + nickname);
                    break;
                } else {
                    pw.println("message:" + input);
                }
            }
        } catch(SocketException e) {
            consoleLog("Socket Exception : " + e);
        } catch(IOException e) {
            consoleLog("Exception : " + e);
        } finally {
            try {
                if(socket != null && !socket.isClosed()) {
                    socket.close();
                    scanner.close();
                }
            } catch (IOException e) {
                consoleLog("Exception : " + e);
            }
        }
    }

    public static void consoleLog(String message) {
        System.out.println("[Chat Client] " + message);
    }
}
