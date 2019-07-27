package server;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class ServerGui extends JFrame {
	private JPanel contentPane;
	static JTextArea terminal = new JTextArea();
	static JTextArea listConnect = new JTextArea();
	public static ArrayList<Socket> listSocket;
	static DataInputStream din = null;
	static DataOutputStream dout = null;
	static JTextField txtMsg = new JTextField();

	int i = 0;
	int j = 0;
	int x = 0;
	int y = 0;
	public static void main(String[] args) throws IOException {
		go();
	}


	public ServerGui() {
		super("SERVER FII CHAT");
		setDefaultCloseOperation(0);
		setIconImage(Toolkit.getDefaultToolkit().getImage("../JavaChat/src/server/image/netW.png"));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					String msg = "exitALL";
					String msg2 = "      < Server Disconnect !!! >";
					for (Socket item2 : ServerGui.listSocket) {
						ServerGui.dout = new DataOutputStream(item2.getOutputStream());

						byte[] msgByte = msg.getBytes();
						byte[] msgByte2 = msg2.getBytes();
						ServerGui.dout.write(msgByte);
						ServerGui.dout.write(msgByte2);
						ServerGui.dout.close();
					}
					System.exit(0);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});

		setSize(572, 439);
		Gui();
		setVisible(true);
	}

	private void Gui() {
		setCursor(Cursor.getPredefinedCursor(0));
		setDefaultCloseOperation(3);
		setResizable(false);
		setBounds(100, 100, 572, 439);

		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(60, 60, 60));
		panel.setBounds(0, 0, 572, 39);
		this.contentPane.add(panel);
		panel.setLayout(null);

		final JButton button = new JButton("");
		button.setBorderPainted(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ServerGui.this.i % 2 == 0) {
					button.setIcon(new ImageIcon("../JavaChat/src/server/image/night.png"));
					ServerGui.this.i++;
				} else {
					button.setIcon(new ImageIcon("../JavaChat/src/server/image/sun.png"));
					ServerGui.this.i++;
				}
			}
		});

		button.setIcon(new ImageIcon("../JavaChat/src/server/image/sun.png"));
		button.setBackground(new Color(60, 60, 60));
		button.setBounds(0, 0, 38, 38);
		panel.add(button);

		JLabel lblNewLabel = new JLabel("Server FII Chat");
		lblNewLabel.setFont(new Font("Dyuthi", 1, 26));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(0);
		lblNewLabel.setBounds(39, 12, 191, 26);
		panel.add(lblNewLabel);

		final JButton btnThoat = new JButton("");
		btnThoat.setBounds(532, 0, 40, 39);
		btnThoat.setBorderPainted(false);
		panel.add(btnThoat);
		btnThoat.setIcon(new ImageIcon("../JavaChat/src/server/image/red-icon-power-off-vector-7129971.png"));
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int question = JOptionPane.showConfirmDialog(btnThoat,
						"This will make your Clients unable to connect\nAre you sure to exit ?", "Exit", 0);
				if (question == 0) {
					try {
						String msg = "exitALL";
						String msg2 = "      < Server Disconnect !!! >";
						for (Socket item : ServerGui.listSocket) {
							ServerGui.dout = new DataOutputStream(item.getOutputStream());

							byte[] msgByte = msg.getBytes();
							byte[] msgByte2 = msg2.getBytes();
							ServerGui.dout.write(msgByte2);
							ServerGui.dout.write(msgByte);
							ServerGui.dout.close();
						}
						System.exit(0);
					} catch (IOException e1) {

						e1.printStackTrace();
					}
				}
			}
		});
		btnThoat.setBackground(new Color(60, 60, 60));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(60, 60, 60));
		panel_1.setBounds(532, 35, 40, 369);
		this.contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton btnNetwork = new JButton("");
		btnNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		btnNetwork.setIcon(new ImageIcon("../JavaChat/src/server/image/icons8-load-balancer-25.png"));
		btnNetwork.setBorderPainted(false);
		btnNetwork.setBounds(0, 116, 40, 40);
		panel_1.add(btnNetwork);
		btnNetwork.setBackground(new Color(60, 60, 60));

		final JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);

		panel_2.setBounds(378, 36, 0, 329);
		this.contentPane.add(panel_2);
		panel_2.setLayout(null);

		final JLabel lblListOnline = new JLabel("Connect Time");
		lblListOnline.setBounds(0, 3, 154, 29);
		panel_2.add(lblListOnline);
		lblListOnline.setHorizontalAlignment(0);
		lblListOnline.setForeground(new Color(255, 250, 250));
		lblListOnline.setVisible(false);

		final JTextArea txtrListOnline = new JTextArea();
		txtrListOnline.setBounds(0, 3, 154, 26);
		panel_2.add(txtrListOnline);
		txtrListOnline.setBackground(new Color(90, 90, 90));
		txtrListOnline.setVisible(false);

		final JScrollPane scrConnect = new JScrollPane();
		scrConnect.setBounds(0, 29, 154, 339);
		scrConnect.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_2.add(scrConnect);
		listConnect.setForeground(new Color(255, 255, 255));
		listConnect.setLineWrap(true);
		listConnect.setWrapStyleWord(true);
		listConnect.setFont(new Font("DejaVu Sans Mono", 0, 14));
		listConnect.setColumns(17);
		listConnect.setRows(18);
		listConnect.setEditable(false);

		listConnect.setBorder(new EmptyBorder(0, 0, 0, 0));
		listConnect.setBackground(new Color(128, 128, 128));
		scrConnect.setViewportView(listConnect);

		final JScrollPane scrStatus = new JScrollPane();
		scrStatus.setCursor(Cursor.getPredefinedCursor(0));
		scrStatus.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
		scrStatus.setAutoscrolls(true);
		scrStatus.setBorder(null);

		scrStatus.setBounds(0, 40, 532, 329);
		this.contentPane.add(scrStatus);
		terminal.setToolTipText("");
		terminal.setCursor(Cursor.getPredefinedCursor(2));

		terminal.setFont(new Font("DejaVu Sans Mono", 0, 12));
		scrStatus.setViewportView(terminal);
		terminal.setEditable(false);
		terminal.setLineWrap(true);
		terminal.setWrapStyleWord(true);
		terminal.setColumns(20);
		terminal.setRows(20);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(60, 60, 60));
		scrStatus.setRowHeaderView(panel_4);
		panel_4.setLayout(null);

		scrConnect.setVisible(false);

		final JButton btnTerminal = new JButton("");
		final JButton btnList = new JButton("");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ServerGui.this.j % 2 == 0) {
					scrStatus.setBounds(0, 40, 379, 323);
					panel_2.setBounds(378, 36, 165, 329);
					scrConnect.setVisible(true);
					txtrListOnline.setVisible(true);
					lblListOnline.setVisible(true);
					btnList.setIcon(new ImageIcon("../JavaChat/src/server/image/listBlack.png"));
					btnList.setBackground(new Color(211, 211, 211));
					btnTerminal.setBackground(new Color(60, 60, 60));
					ServerGui.this.j++;
				} else {
					scrStatus.setBounds(0, 40, 532, 323);
					panel_2.setBounds(378, 36, 0, 329);
					scrConnect.setVisible(false);
					txtrListOnline.setVisible(false);
					lblListOnline.setVisible(false);
					btnList.setIcon(new ImageIcon("../JavaChat/src/server/image/listWhite.png"));
					btnList.setBackground(new Color(60, 60, 60));
					btnTerminal.setBackground(new Color(211, 211, 211));
					ServerGui.this.j++;
				}
			}
		});

		btnTerminal.setBounds(0, 220, 40, 40);
		panel_1.add(btnTerminal);
		btnTerminal.setBackground(new Color(60, 60, 60));

		btnList.setBorderPainted(false);
		btnList.setIcon(new ImageIcon("../JavaChat/src/server/image/listWhite.png"));
		btnList.setBackground(new Color(60, 60, 60));
		btnList.setBounds(0, 272, 40, 40);
		panel_1.add(btnList);

		final JButton btnClock = new JButton("");
		btnClock.setIcon(new ImageIcon("../JavaChat/src/server/image/icons8-clock-25.png"));
		btnClock.setBorderPainted(false);
		btnClock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date timeNow = new Date();
				DateFormat df = new SimpleDateFormat("hh:mm:ss a");
				ServerGui.terminal.append("[#] Now is " + df.format(timeNow) + "\n");
				JOptionPane.showMessageDialog(btnClock, "Now is " + df.format(timeNow));
			}
		});

		btnClock.setBounds(0, 168, 40, 40);
		panel_1.add(btnClock);
		btnClock.setBackground(new Color(60, 60, 60));

		final JButton btnPort = new JButton("");
		btnPort.setBorderPainted(false);
		btnPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ServerGui.terminal.append("[#] Server operates at PORT Address: 9999\n");
				JOptionPane.showMessageDialog(btnPort, "Server operates at PORT Address: 9999");
			}
		});
		btnPort.setIcon(new ImageIcon("../JavaChat/src/server/image/rj45White.png"));
		btnPort.setBounds(0, 64, 40, 40);
		panel_1.add(btnPort);
		btnPort.setBackground(new Color(60, 60, 60));

		final JButton btnIP = new JButton("");
		btnIP.setBorderPainted(false);
		btnIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ServerGui.terminal.append("[#] Server operates at IP Address: 127.0.0.1\n");
				JOptionPane.showMessageDialog(btnIP, "Server operates at IP Address: 127.0.0.1");
			}
		});
		btnIP.setIcon(new ImageIcon("../JavaChat/src/server/image/server.png"));
		btnIP.setBounds(0, 12, 40, 40);
		panel_1.add(btnIP);
		btnIP.setBackground(new Color(60, 60, 60));

		btnTerminal.setIcon(new ImageIcon("../JavaChat/src/server/image/icons8-run-command-25.png"));
		btnTerminal.setBorderPainted(false);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(60, 60, 60));
		panel_3.setBounds(0, 404, 572, 10);
		this.contentPane.add(panel_3);

		final JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_5.setBackground(new Color(169, 169, 169));
		panel_5.setBounds(1, 365, 532, 39);
		this.contentPane.add(panel_5);
		panel_5.setLayout(null);

		txtMsg.setFont(new Font("Dialog", 0, 12));
		txtMsg.setText("");
		txtMsg.setBackground(new Color(169, 169, 169));
		txtMsg.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		txtMsg.setBounds(46, 12, 431, 22);
		panel_5.add(txtMsg);
		txtMsg.setColumns(10);
		txtMsg.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10 && !ServerGui.txtMsg.getText().equals("")) {

					ServerGui.txtMsg.requestFocus();
					String msg = ServerGui.txtMsg.getText();
					ServerGui.terminal.append("<Me>: " + msg + "\n");
					byte[] msgByte = null;
					for (Socket item : ServerGui.listSocket) {
						try {
							ServerGui.dout = new DataOutputStream(item.getOutputStream());
							msgByte = msg.getBytes();
							ServerGui.dout.write(msgByte);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
					if (msg.equals("exitALL")) {
						try {
							ServerGui.dout.close();
							ServerGui.dout.write(msgByte);
						} catch (IOException ex) {

							ex.printStackTrace();
						}
						System.out.println("Disconnect all client !!!");
						ServerGui.terminal.append("Disconnect all client !!!");
					}
					ServerGui.txtMsg.setText("");
				}
			}
		});

		JButton btnMsg = new JButton("");
		btnMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ServerGui.this.y % 2 == 0) {
					panel_5.setVisible(true);
					ServerGui.this.y++;
				} else {
					panel_5.setVisible(false);
					ServerGui.this.y++;
				}
			}
		});
		btnMsg.setBounds(0, 329, 41, 40);
		panel_1.add(btnMsg);
		btnMsg.setForeground(new Color(60, 60, 60));
		btnMsg.setBorderPainted(false);
		btnMsg.setIcon(new ImageIcon("../JavaChat/src/server/image/chat.png"));
		btnMsg.setBorderPainted(false);
		btnMsg.setBackground(new Color(60, 60, 60));

		JButton btnSend = new JButton("");
		btnSend.setIcon(new ImageIcon("../JavaChat/src/server/image/send.png"));
		btnSend.setForeground(new Color(60, 60, 60));
		btnSend.setBorderPainted(false);
		btnSend.setBackground(new Color(169, 169, 169));
		btnSend.setBounds(489, 12, 31, 22);
		panel_5.add(btnSend);

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!ServerGui.txtMsg.getText().equals("")) {

					ServerGui.txtMsg.requestFocus();
					String msg = ServerGui.txtMsg.getText();
					ServerGui.terminal.append("<Me>: " + msg + "\n");
					byte[] msgByte = null;
					for (Socket item : ServerGui.listSocket) {
						try {
							ServerGui.dout = new DataOutputStream(item.getOutputStream());
							msgByte = msg.getBytes();
							ServerGui.dout.write(msgByte);
						} catch (IOException e) {

							e.printStackTrace();
						}
					}
					if (msg.equals("exitALL")) {
						try {
							ServerGui.dout.close();
							ServerGui.dout.write(msgByte);
						} catch (IOException e) {

							e.printStackTrace();
						}
						System.out.println("Disconnect all client !!!");
						ServerGui.terminal.append("Disconnect all client !!!");
					}
					ServerGui.txtMsg.setText("");
				}
			}
		});

		JButton button_3 = new JButton("");
		button_3.setIcon(new ImageIcon("../JavaChat/src/server/image/chat.png"));
		button_3.setForeground(new Color(60, 60, 60));
		button_3.setBorderPainted(false);
		button_3.setBackground(new Color(169, 169, 169));
		button_3.setBounds(5, 0, 41, 40);
		panel_5.add(button_3);

		btnTerminal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ServerGui.this.x % 2 == 0) {
					btnTerminal.setBackground(new Color(211, 211, 211));
					scrStatus.setBounds(0, 40, 532, 329);
					panel_2.setBounds(378, 36, 0, 329);
					scrConnect.setVisible(false);
					txtrListOnline.setVisible(false);
					lblListOnline.setVisible(false);
					btnList.setIcon(new ImageIcon("../JavaChat/src/server/image/listWhite.png"));
					btnList.setBackground(new Color(60, 60, 60));
					ServerGui.terminal.setText("");
					ServerGui.terminal.append("[!] Terminal is active !!!\n");
					ServerGui.this.x++;
				} else {
					btnTerminal.setBackground(new Color(60, 60, 60));
					ServerGui.this.x++;
				}
			}
		});
		scrConnect.setVisible(false);
		txtrListOnline.setVisible(false);
		lblListOnline.setVisible(false);
	}

	private static void go() {
		listSocket = new ArrayList();
		ServerGui server = new ServerGui();
		server.execute();
	}

	private void execute() {
		try {
			ServerSocket server = new ServerSocket(9999);

			terminal.append("[#] The Server is running !\nWaiting for the Client to connect ...\n");
			System.out.println(
					"[#] Server đã hoạt động mượt mà ^^\\nNgồi hóng coi có đứa nào kết nối ...");
			writeServer write = new writeServer();
			write.start();
			while (true) {
				Socket client = server.accept();

				listSocket.add(client);
				ReadServer read = new ReadServer(client);
				read.start();
			}

		} catch (IOException e) {
			terminal.append("[#] The Server cann't running !\n");
			e.printStackTrace();
			return;
		}
	}
}

class ReadServer extends Thread {
	private Socket server;

	public ReadServer(Socket server) {
		this.server = server;
	}

	public void run() {
		DataInputStream din = null;
		DataOutputStream dout = null;
		Date timeNow = new Date();
		DateFormat df = new SimpleDateFormat("h:mm a");
		try {
			int count = 0;
			din = new DataInputStream(this.server.getInputStream());

			byte[] data = new byte[1024];
			count = din.read(data);
			//System.out.println("Count = " + count);
			byte[] real = new byte[count + 1];
			for (int i = 0; i <= count - 1; i++) {
				real[i] = data[i];
			}

			String name = (new String(data)).trim();
			System.out.println("[#] " + name + " đã zô nhóm chat ;) ");
			ServerGui.listConnect.append("[" + df.format(timeNow) + "]: " + name + "\n");
			ServerGui.terminal
					.append("       < [" + df.format(timeNow) + "]: " + name + " joined the chat room !!!>\n");

			for (Socket item : ServerGui.listSocket) {
				if (item.getPort() != this.server.getPort()
						&& item.getLocalAddress() != this.server.getLocalAddress()) {
					dout = new DataOutputStream(item.getOutputStream());

					String nameString = "# [" + name + "]" + " joined the chat room ! ";
					byte[] nameByte = nameString.getBytes();
					dout.write(nameByte);
				}
			}
			System.out.println("[#] [" + name + "]" + " hiện hình =)) !!!");
			while (true) {
				int count1 = 0;
				din = new DataInputStream(this.server.getInputStream());

				byte[] data1 = new byte[1024];
				count1 = din.read(data1);
				//System.out.println("Count 1 = " + count1);
				byte[] real1 = new byte[count1 + 1];
				for (int i = 0; i <= count1 - 1; i++) {
					real1[i] = data1[i];
				}

				String msg = (new String(data1)).trim();
				if (msg.equals("exit")) {
					ServerGui.listSocket.remove(this.server);
					for (Socket item : ServerGui.listSocket) {
						if (item.getPort() != this.server.getPort()
								&& item.getLocalAddress() != this.server.getLocalAddress()) {
							dout = new DataOutputStream(item.getOutputStream());
							String msgName = "       < [" + name + "]" + " Disconnect !!!>\n";
							byte[] msgByte = msgName.getBytes();
							dout.write(msgByte);
						}
					}
					System.out.println("< [" + name + "]" + " Đã ra đi T.T");
					ServerGui.terminal.append("       < [" + df.format(timeNow) + "]: " + name + " Disconnect !!!>\n");

					din.close();
					this.server.close();
					continue;
				}
				for (Socket item : ServerGui.listSocket) {
					if (item.getPort() != this.server.getPort()
							&& item.getLocalAddress() != this.server.getLocalAddress()) {
						dout = new DataOutputStream(item.getOutputStream());
						String msgName = "[" + name + "]" + ": " + msg;
						byte[] msgByte = msgName.getBytes();
						dout.write(msgByte);
					}
				}
				System.out.println("[" + name + "]" + ": " + msg + "\n");
				ServerGui.terminal.append("[" + name + "]" + ": " + msg + "\n");
			}

		} catch (IOException e) {
			try {
				din.close();
				this.server.close();
			} catch (IOException ex) {
				System.err.println(" ");
			}
			return;
		}
	}
}

class writeServer extends Thread {
	public void run() {
		DataOutputStream dout = null;
		DataInputStream din = null;
		Scanner scan = new Scanner(System.in);
		while (true) {
			try {
				String msg = scan.nextLine();
				byte[] msgByte = null;
				for (Socket item : ServerGui.listSocket) {
					dout = new DataOutputStream(item.getOutputStream());

					msgByte = msg.getBytes();
					dout.write(msgByte);
				}
				if (msg.equals("exitALL")) {
					dout.close();
					System.out.println("Disconnect all client !!!");
					dout.write(msgByte);
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
}
