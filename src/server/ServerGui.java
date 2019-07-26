package server;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
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
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

import java.awt.Cursor;

public class ServerGui extends JFrame {

	private JPanel contentPane;
	static JTextArea terminal = new JTextArea();
	static JTextArea listConnect = new JTextArea();
	public static ArrayList<Socket> listSocket;
	static DataInputStream din = null;
	static DataOutputStream dout = null;

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		go();
	}

	/**
	 * Create the frame.
	 */
	int i, j, x = 0;

	public ServerGui() {
		super("SERVER FII CHAT");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(""));
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					String msg = "exitALL";
					String msg2 = "      < Server Disconnect !!! >";
					for (Socket item2 : ServerGui.listSocket) {
						dout = new DataOutputStream(item2.getOutputStream());
						// String msgName = (msg);
						byte[] msgByte = msg.getBytes();
						byte[] msgByte2 = msg2.getBytes();
						dout.write(msgByte);
						dout.write(msgByte2);
						dout.close();
					}
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		setSize(572, 439);
		Gui();
		setVisible(true);

	}

	private void Gui() {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 572, 439);
		// setBounds(100, 100, 572, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(60, 60, 60));
		panel.setBounds(0, 0, 572, 39);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton button = new JButton("");
		button.setBorderPainted(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (i % 2 == 0) {
					button.setIcon(new ImageIcon("../ChatRoom/src/image/night.png"));
					i++;
				} else {
					button.setIcon(new ImageIcon("../ChatRoom/src/image/sun.png"));
					i++;
				}

			}
		});
		button.setIcon(new ImageIcon("../ChatRoom/src/image/sun.png"));
		button.setBackground(new Color(60, 60, 60));
		button.setBounds(0, 0, 38, 38);
		panel.add(button);

		JLabel lblNewLabel = new JLabel("Server FII Chat");
		lblNewLabel.setFont(new Font("Dyuthi", Font.BOLD, 26));
		lblNewLabel.setForeground(new Color(60, 60, 60));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(39, 12, 191, 26);
		panel.add(lblNewLabel);

		JButton btnThoat = new JButton("");
		btnThoat.setBounds(532, 0, 40, 39);
		btnThoat.setBorderPainted(false);
		panel.add(btnThoat);
		btnThoat.setIcon(new ImageIcon("../ChatRoom/src/image/red-icon-power-off-vector-7129971.png"));
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int question = JOptionPane.showConfirmDialog(btnThoat,
						"This will make your Clients unable to connect\nAre you sure to exit ?", "Exit",
						JOptionPane.YES_NO_OPTION);
				if (question == JOptionPane.YES_OPTION) {
					try {
						String msg = "exitALL";
						String msg2 = "      < Server Disconnect !!! >";
						for (Socket item : listSocket) {
							dout = new DataOutputStream(item.getOutputStream());
							// String msgName = (msg);
							byte[] msgByte = msg.getBytes();
							byte[] msgByte2 = msg2.getBytes();
							dout.write(msgByte2);
							dout.write(msgByte);
							dout.close();
						}
						System.exit(0);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnThoat.setBackground(new Color(60, 60, 60));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(60, 60, 60));
		panel_1.setBounds(532, 35, 40, 369);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		button_4.setIcon(new ImageIcon("../ChatRoom/src/image/icons8-load-balancer-25.png"));
		button_4.setBorderPainted(false);
		button_4.setBounds(0, 116, 40, 40);
		panel_1.add(button_4);
		button_4.setBackground(new Color(60, 60, 60));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		// panel_2.setBounds(378, 36, 165, 368);
		panel_2.setBounds(378, 36, 0, 368);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblListOnline = new JLabel("List Online");
		lblListOnline.setBounds(0, 3, 154, 29);
		panel_2.add(lblListOnline);
		lblListOnline.setHorizontalAlignment(SwingConstants.CENTER);
		lblListOnline.setForeground(new Color(255, 250, 250));
		lblListOnline.setVisible(false);

		JTextArea txtrListOnline = new JTextArea();
		txtrListOnline.setBounds(0, 3, 154, 26);
		panel_2.add(txtrListOnline);
		txtrListOnline.setBackground(new Color(90, 90, 90));
		txtrListOnline.setVisible(false);

		JScrollPane scrConnect = new JScrollPane();
		scrConnect.setBounds(0, 29, 154, 339);
		scrConnect.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_2.add(scrConnect);
		listConnect.setForeground(new Color(255, 255, 255));
		listConnect.setLineWrap(true);
		listConnect.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 14));
		listConnect.setColumns(17);
		listConnect.setRows(18);
		listConnect.setEditable(false);

		// JTextArea listConnect = new JTextArea();
		listConnect.setBorder(new EmptyBorder(0, 0, 0, 0));
		listConnect.setBackground(new Color(128, 128, 128));
		scrConnect.setViewportView(listConnect);

		JScrollPane scrStatus = new JScrollPane();
		scrStatus.setBorder(null);
		// scrStatus.setBounds(0, 40, 379, 363);
		scrStatus.setBounds(0, 40, 532, 363);
		contentPane.add(scrStatus);
		terminal.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		// JTextArea terminal = new JTextArea();
		terminal.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));
		scrStatus.setViewportView(terminal);
		terminal.setEditable(false);
		terminal.setLineWrap(true);
		terminal.setRows(20);
		terminal.setColumns(20);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(60, 60, 60));
		scrStatus.setRowHeaderView(panel_4);
		panel_4.setLayout(null);

//		JList listOnline = new JList();
//		listOnline.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(255, 0, 0, 40)));
//		listOnline.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 14));
//		scrollPane_1.setViewportView(listOnline);
//		listOnline.setModel(new AbstractListModel() {
//			String[] values = new String[] { "quang", "tran", "ba" };
//
//			public int getSize() {
//				return values.length;
//			}
//
//			public Object getElementAt(int index) {
//				return values[index];
//			}
//		});
//		listOnline.setBackground(new Color(255, 255, 255));
		scrConnect.setVisible(false);

		JButton button_6 = new JButton("");
		JButton btnList = new JButton("");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (j % 2 == 0) {
					scrStatus.setBounds(0, 40, 379, 363);
					panel_2.setBounds(378, 36, 165, 368);
					scrConnect.setVisible(true);
					txtrListOnline.setVisible(true);
					lblListOnline.setVisible(true);
					btnList.setIcon(new ImageIcon("../ChatRoom/src/image/listBlack.png"));
					btnList.setBackground(new Color(211, 211, 211, 60));
					button_6.setBackground(new Color(60, 60, 60));
					j++;
				} else {
					scrStatus.setBounds(0, 40, 532, 363);
					panel_2.setBounds(378, 36, 0, 368);
					scrConnect.setVisible(false);
					txtrListOnline.setVisible(false);
					lblListOnline.setVisible(false);
					btnList.setIcon(new ImageIcon("../ChatRoom/src/image/listWhite.png"));
					btnList.setBackground(new Color(60, 60, 60));
					button_6.setBackground(new Color(255, 255, 255, 60));
					j++;
				}

			}
		});

		button_6.setBounds(0, 220, 40, 40);
		panel_1.add(button_6);
		button_6.setBackground(new Color(60, 60, 60));

		btnList.setBorderPainted(false);
		btnList.setIcon(new ImageIcon("../ChatRoom/src/image/listWhite.png"));
		btnList.setBackground(new Color(60, 60, 60));
		btnList.setBounds(0, 272, 40, 40);
		panel_1.add(btnList);

		JButton button_5 = new JButton("");
		button_5.setIcon(new ImageIcon("../ChatRoom/src/image/icons8-clock-25.png"));
		button_5.setBorderPainted(false);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date timeNow = new Date();
				DateFormat df = new SimpleDateFormat("hh:mm:ss a");
				terminal.append("[#] Now is " + df.format(timeNow) + "\n");
				JOptionPane.showMessageDialog(button_5, "Now is " + df.format(timeNow));

			}
		});
		button_5.setBounds(0, 168, 40, 40);
		panel_1.add(button_5);
		button_5.setBackground(new Color(60, 60, 60));

		JButton btnPort = new JButton("");
		btnPort.setBorderPainted(false);
		btnPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				terminal.append("[#] Server operates at PORT Address: 9999\n");
				JOptionPane.showMessageDialog(btnPort, "Server operates at PORT Address: 9999");
			}
		});
		btnPort.setIcon(new ImageIcon("../ChatRoom/src/image/rj45White.png"));
		btnPort.setBounds(0, 64, 40, 40);
		panel_1.add(btnPort);
		btnPort.setBackground(new Color(60, 60, 60));

		JButton btnIP = new JButton("");
		btnIP.setBorderPainted(false);
		btnIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				terminal.append("[#] Server operates at IP Address: 127.0.0.1\n");
				JOptionPane.showMessageDialog(btnIP, "Server operates at IP Address: 127.0.0.1");
			}
		});
		btnIP.setIcon(new ImageIcon("../ChatRoom/src/image/server.png"));
		btnIP.setBounds(0, 12, 40, 40);
		panel_1.add(btnIP);
		btnIP.setBackground(new Color(60, 60, 60));

		// JButton button_6 = new JButton("");
		button_6.setIcon(new ImageIcon("../ChatRoom/src/image/icons8-run-command-25.png"));
		button_6.setBorderPainted(false);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(60, 60, 60));
		panel_3.setBounds(0, 404, 572, 10);
		contentPane.add(panel_3);
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (x % 2 == 0) {
					button_6.setBackground(new Color(255, 255, 255, 60));
					scrStatus.setBounds(0, 40, 532, 363);
					panel_2.setBounds(378, 36, 0, 368);
					scrConnect.setVisible(false);
					txtrListOnline.setVisible(false);
					lblListOnline.setVisible(false);
					btnList.setIcon(new ImageIcon("../ChatRoom/src/image/listWhite.png"));
					btnList.setBackground(new Color(60, 60, 60));
					terminal.setText("");
					terminal.append("[!] Terminal is active !!!\n");
					x++;
				} else {
					button_6.setBackground(new Color(60, 60, 60));
					x++;
				}
			}
		});
	}

	private static void go() throws IOException {
		ServerGui.listSocket = new ArrayList<Socket>();
		ServerGui server = new ServerGui();
		server.execute();
	}

	private void execute() {
		ServerSocket server;
		try {
			server = new ServerSocket(9999);
			// System.out.println("[#] Server đã hoạt động mượt mà ^^\nNgồi hóng coi có đứa
			// nào kết nối ...");
			terminal.append("[#] The Server is running !\nWaiting for the Client to connect ...\n");
			System.out.println("[#] Server đã hoạt động mượt mà ^^\nNgồi hóng coi có đứa nào kết nối ...");
			writeServer write = new writeServer();
			write.start();
			while (true) {
				Socket client = server.accept();
				// System.out.println("[#] Có 1 đối tượng giống như con người kết nối");
				ServerGui.listSocket.add(client);
				ReadServer read = new ReadServer(client);
				read.start();
			}

		} catch (IOException e) {
			terminal.append("[#] The Server cann't running !\n");
			e.printStackTrace();
		}

	}
}

class ReadServer extends Thread {
	private Socket server;
	// private JTextArea terminal;

	public ReadServer(Socket server) {
		this.server = server;
	}

	@Override
	public void run() {
		DataInputStream din = null;
		DataOutputStream dout = null;
		Date timeNow = new Date();
		DateFormat df = new SimpleDateFormat("h:mm a");
		try {
			int count = 0;
			din = new DataInputStream(server.getInputStream());
			// String name = din.readUTF();
			byte[] data = new byte[1024];
			count = din.read(data);
			System.out.println("Count = " + count);
			byte[] real = new byte[count + 1];
			for (int i = 0; i <= count - 1; i++) {
				real[i] = data[i];

				// System.out.println(real[i]);
				// System.out.println("# "+data[i]);
			}
			String name = new String(data).trim();
			System.out.println("[#] " + name + " đã zô nhóm chat ;) ");
			ServerGui.listConnect.append("[" + df.format(timeNow) + "]: " + name + "\n");
			ServerGui.terminal
					.append("       < " + "[" + df.format(timeNow) + "]: " + name + " joined the chat room !!!>\n");

			for (Socket item : ServerGui.listSocket) {
				if ((item.getPort() != server.getPort()) && (item.getLocalAddress() != server.getLocalAddress())) {
					dout = new DataOutputStream(item.getOutputStream());
//					byte[] nameList = name.getBytes();
//					dout.write(nameList);
					String nameString = ("# " + "[" + name + "]" + " hiện hình =)) !!!");
					byte[] nameByte = nameString.getBytes();
					dout.write(nameByte);
				}
			}
			while (true) {
				// String msg = din.readUTF();
				// String msg = new String(data).trim();
				int count1 = 0;
				din = new DataInputStream(server.getInputStream());
				// String name = din.readUTF();
				byte[] data1 = new byte[1024];
				count1 = din.read(data1);
				System.out.println("Count 1 = " + count1);
				byte[] real1 = new byte[count1 + 1];
				for (int i = 0; i <= count1 - 1; i++) {
					real1[i] = data1[i];
					// System.out.println(real[i]);
					// System.out.println("# "+data[i]);
				}
				String msg = new String(data1).trim();
				if (msg.equals("exit")) {
					ServerGui.listSocket.remove(server);
					for (Socket item : ServerGui.listSocket) {
						if ((item.getPort() != server.getPort())
								&& (item.getLocalAddress() != server.getLocalAddress())) {
							dout = new DataOutputStream(item.getOutputStream());
							String msgName = ("       < " + "[" + name + "]" + " Disconnect !!!>\n");
							byte[] msgByte = msgName.getBytes();
							dout.write(msgByte);
						}
					}
					System.out.println("< " + "[" + name + "]" + " Đã ra đi T.T");
					ServerGui.terminal
							.append("       < " + "[" + df.format(timeNow) + "]: " + name + " Disconnect !!!>\n");

					din.close();
					server.close();
					continue;
				}
				for (Socket item : ServerGui.listSocket) {
					if ((item.getPort() != server.getPort()) && (item.getLocalAddress() != server.getLocalAddress())) {
						dout = new DataOutputStream(item.getOutputStream());
						String msgName = ("[" + name + "]" + ": " + msg);
						byte[] msgByte = msgName.getBytes();
						dout.write(msgByte);
					}
				}
				System.out.println("[" + name + "]" + ": " + msg + "\n");
				ServerGui.terminal.append("[" + name + "]" + ": " + msg + "\n");
//				din.close();
//				dout.close();
			}
		} catch (IOException e) {
			try {
				din.close();
				server.close();
			} catch (IOException ex) {
				System.err.println(" ");
			}
		}
	}
}

class writeServer extends Thread {

	@Override
	public void run() {
		DataOutputStream dout = null;
		DataInputStream din = null;
		Scanner scan = new Scanner(System.in);
		while (true) {
			try {
				String msg = scan.nextLine();
//				if (msg.equals("exit")) {
//					for (Socket item : Server.listSocket) {
//						Server.listSocket.remove(item);
//						dout = new DataOutputStream(item.getOutputStream());
//						din = new DataInputStream(item.getInputStream());
//						String msgName = ("[#] Đã ngắt kết nối với Server");
//						byte[] msgByte = msgName.getBytes();
//						dout.write(msgByte);
//						din.close();
//						item.close();
//					}
//					System.out.println("[#] Ngắt kết nối với tất cả các client");
//					continue;
//				}
				byte[] msgByte = null;
				for (Socket item : ServerGui.listSocket) {
					dout = new DataOutputStream(item.getOutputStream());
					// String msgName = (msg);
					msgByte = msg.getBytes();
					dout.write(msgByte);
				}
				if (msg.equals("exitALL")) {
					dout.close();
					System.out.println("Disconnect all client !!!");
					dout.write(msgByte);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}