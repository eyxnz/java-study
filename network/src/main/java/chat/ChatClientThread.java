package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ChatClientThread extends Thread {
    private Socket socket;
    private BufferedReader br;

    public ChatClientThread(Socket socket, BufferedReader br) {
        this.socket = socket;
        this.br = br;
    }

    @Override
    public void run() {
        try {
            while(true) {
                String message = br.readLine();

                if("quit".equals(message)) {
                    break;
                } else if(message == null) {
                    ChatClient.consoleLog("Can not send blank message");
                    break;
                } else {
                    System.out.println(message);
                }
            }
        } catch(SocketException e) {
            ChatClient.consoleLog("Socket Exception : " + e);
        } catch(IOException e) {
            ChatClient.consoleLog("Exception : " + e);
        } finally {
            try {
                if(socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                ChatClient.consoleLog("Exception : " + e);
            }
        }
    }
}
