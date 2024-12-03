package chat.gui;
import chat.ChatClient;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ChatWindow {
	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;

	private Socket socket;
	private PrintWriter pw;

	public static final String IP = "0.0.0.0"; // localhost
	public static final int PORT = 8000;

	public ChatWindow(String name) {
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		buttonSend.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent actionEvent ) {
				sendMessage();
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if(keyChar == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});

		// Textfield
		textField.setColumns(80);

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit();
			}
		});
		frame.setVisible(true);
		frame.pack();

		try {
			// 1. 서버 연결 작업
			socket = new Socket();

			socket.connect(new InetSocketAddress(IP, PORT));

			// 2. IO Stream
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8), true);

			// 3. JOIN Protocol
			pw.println("join:" + frame.getTitle());

			// 4. ChatClientThread 생성
			new ChatClientThread(socket, new BufferedReader(new InputStreamReader(socket.getInputStream()))).start();
		} catch(SocketException e) {
			consoleLog("Socket Exception : " + e);
		} catch(IOException e) {
			consoleLog("Exception : " + e);
		}
	}
	
	private void sendMessage() {
		String message = textField.getText();

		if(!message.isEmpty()) {
			pw.println("message:" + message);
			pw.flush();
		}

		// 리셋
		textField.setText("");
		textField.requestFocus();
	}

	private void updateTextArea(String message) {
		textArea.append(message);
		textArea.append("\n");
	}

	private class ChatClientThread extends Thread {
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

					if(message == null) {
						consoleLog("Can not send blank message");
						break;
					} else {
						updateTextArea(message);
					}
				}
			} catch(SocketException e) {
				ChatClient.consoleLog("Socket Exception : " + e);
			} catch(IOException e) {
				ChatClient.consoleLog("Exception : " + e);
			} finally {
				quit();
			}
		}
	}

	private void quit() {
		try {
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch(IOException e) {
			consoleLog("Exception : " + e);
		}

		pw.println("quit");
		pw.flush();
		System.exit(0);
	}

	private void consoleLog(String message) {
		System.out.println("[ChatWindow] " + message);
	}
}
