package chat;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ChatServerThread extends Thread {
    private String nickname;
    private Socket socket;
    private List<PrintWriter> listPrintWriter;

    public ChatServerThread(Socket socket, List<PrintWriter> listPrintWriter) {
        this.socket = socket;
        this.listPrintWriter = listPrintWriter;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;

        try {
            // 1. Remote Host Information
            InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
            String hostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
            int port = inetRemoteSocketAddress.getPort();
            ChatServer.consoleLog("Client host address : " +hostAddress + ", port : " + port);

            // 2. 스트림 얻기
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true);

            // 3. 요청 처리
            while(true) {
                String request = bufferedReader.readLine();
                if(request == null) {
                    ChatServer.consoleLog("Client Exception : Lost connection with client");
                    doQuit(printWriter);
                    break;
                }

                // 4. 프로토콜 분석
                String[] tokens = request.split(":");

                if("join".equals(tokens[0])) {
                    doJoin(tokens[1], printWriter);
                } else if("message".equals(tokens[0])) {
                    doMessage(tokens[1]);
                } else if("quit".equals(tokens[0])) {
                    doQuit(printWriter);
                    break;
                } else {
                    ChatServer.consoleLog("Exception : Not a valid request " + tokens[0]);
                }
            }
        } catch(SocketException e) {
            ChatServer.consoleLog("Socket Exception : " + e);
            doQuit(printWriter);
        } catch(IOException e) {
            ChatServer.consoleLog("Exception : " + e);
            doQuit(printWriter);
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch(IOException e) {
                    ChatServer.consoleLog("Exception : " + e);
                }
            }
        }
    }

    private void doJoin(String nickname, PrintWriter printWriter) {
        this.nickname = nickname;

        String data = nickname + "님이 참여하였습니다.";
        broadcast(data);

        // writer pool에 저장
        addWriter(printWriter);

        // ack
        printWriter.println(nickname + "님, 채팅방에 참여했습니다.");
        printWriter.flush();
    }

    private void addWriter(PrintWriter printWriter) {
        synchronized(listPrintWriter) {
            listPrintWriter.add(printWriter);
        }
    }

    private void doMessage(String message) {
        broadcast(nickname + " : " + message);
    }

    private void doQuit(PrintWriter printWriter) {
        removeWriter(printWriter);
        
        String data = nickname + "님이 퇴장하였습니다.";
        broadcast(data);
    }

    private void removeWriter(Writer writer) {
        synchronized(listPrintWriter) {
            listPrintWriter.remove(writer);
        }
    }

    private void broadcast(String data) {
        synchronized(listPrintWriter) {
            for(Writer writer : listPrintWriter) {
                PrintWriter printWriter = (PrintWriter)writer;
                printWriter.println(data);
                printWriter.flush();
            }
        }
    }
}
