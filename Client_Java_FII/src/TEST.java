

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.xml.internal.bind.v2.model.core.Adapter;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Window.Type;

public class TEST extends javax.swing.JFrame {

	private JPanel contentPane;
	private JTextField txtUser;
	private static JTextField txtFrom;
	private JButton btnNewButton;
	private Thread sendMessage;
	private Thread readMessage;
	private static JTextArea txtChat;
	// --------------------------
	/**
	 * Launch the application.
	 */
	private static String name;
	private static String ip = "127.0.0.1";
	private static int port = 9999;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name + " ";
	}
	// -------------------------
	private static DataInputStream dis;
	private DataOutputStream dos;
	private JTextField txtChatKiki;
	private static Socket s;

	// private boolean temb2 = false;
	// -------------------------
	public static void main(String[] args) /* throws IOException,UnknownHostException */ {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TEST frame = new TEST();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static byte[] recive() throws IOException {
		byte[] data = null;
		data = new byte[1024];
		int count = dis.read(data);
		byte[] real = new byte[count + 1];
		for (int i = 1; i <= count; i++)
			real[i] = data[i];
		return data;
	}

	public TEST() {
		setResizable(false);
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setBounds(100, 100, 665, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtUser = new JTextField();
		txtUser.setEnabled(false);
		txtUser.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtUser.setBounds(111, 441, 237, 38);
		contentPane.add(txtUser);
		txtUser.setColumns(10);

		JButton btnNut = new JButton("Send");
		btnNut.setEnabled(false);
		btnNut.setBackground(UIManager.getColor("Button.foreground"));
		btnNut.setBounds(358, 440, 66, 38);
		contentPane.add(btnNut);

		JRadioButton rdbFrom = new JRadioButton("Private chat", false);
		rdbFrom.setEnabled(false);
		rdbFrom.setForeground(Color.BLACK);
		rdbFrom.setHorizontalAlignment(SwingConstants.CENTER);
		rdbFrom.setBackground(Color.WHITE);
		rdbFrom.setBounds(502, 317, 95, 23);
		contentPane.add(rdbFrom);

		JButton btnExit = new JButton("Logout");
		btnExit.setEnabled(false);
		btnExit.setBackground(SystemColor.text);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "Would you like exit?", "EXIT", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
				if (n == JOptionPane.YES_OPTION) {
					String out = "exit";
					byte[] outB = out.getBytes();
					try {
					dos.write(outB,0,outB.length);
					System.exit(1);
					} catch (IOException e1) {}
				}else {}
			}
		});
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "Would you like exit?", "EXIT", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
				if (n == JOptionPane.YES_OPTION) {
					String out = "exit";
					byte[] outB = out.getBytes();
					try {
					dos.write(outB,0,outB.length);
					System.exit(1);
					} catch (IOException e1) {}
				}else {}
			}
		});
		
				txtFrom = new JTextField();
				txtFrom.setEnabled(false);
				txtFrom.setHorizontalAlignment(SwingConstants.CENTER);
				txtFrom.setText("name...");
				txtFrom.setFont(new Font("Times New Roman", Font.PLAIN, 11));
				txtFrom.setBackground(SystemColor.menu);
				txtFrom.setBounds(526, 347, 73, 33);
				contentPane.add(txtFrom);
				txtFrom.setColumns(10);

		btnExit.setBounds(495, 440, 102, 38);
		contentPane.add(btnExit);

		txtChatKiki = new JTextField();
		txtChatKiki.setForeground(new Color(255, 165, 0));
		txtChatKiki.setEditable(false);
		txtChatKiki.setFont(new Font("Viner Hand ITC", Font.BOLD, 21));
		txtChatKiki.setText("FII");
		txtChatKiki.setHorizontalAlignment(SwingConstants.CENTER);
		txtChatKiki.setBounds(71, 8, 106, 44);
		contentPane.add(txtChatKiki);
		txtChatKiki.setColumns(10);

		JLabel lblNewLabel = new JLabel(" To :");
		lblNewLabel.setForeground(UIManager.getColor("Button.foreground"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(502, 347, 73, 32);
		contentPane.add(lblNewLabel);
								
										JLabel lblNewLabel_1 = new JLabel("FII Chat v0.1");
										lblNewLabel_1.setBounds(495, 391, 104, 22);
										contentPane.add(lblNewLabel_1);
										lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 10));
										lblNewLabel_1.setEnabled(false);
										lblNewLabel_1.setBackground(SystemColor.windowText);
										lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
														btnNewButton = new JButton("Conect to Server");
														btnNewButton.addActionListener(new ActionListener() {
															public void actionPerformed(ActionEvent e) {
																txtUser.setEnabled(true);
																btnNut.setEnabled(true);
																txtUser.requestFocus();
																btnExit.setEnabled(true);
																btnNewButton.setEnabled(false);
																boolean temb1 = false;
																try {
																	s = new Socket(ip, port);
																	dis = new DataInputStream(s.getInputStream());
																	dos = new DataOutputStream(s.getOutputStream());
																	// temb2 = true;
																	if (temb1 == false) {
																		String name1 = name;
																		byte[] nameByte = name1.getBytes();
																		dos.write(nameByte, 0, nameByte.length);
																		temb1 = true;
																	}
																	// ---------------------------------------
																} catch (IOException k) {
																	JOptionPane.showMessageDialog(null, "Don't connect to server, please reset client !");
																	System.exit(0);
//																	try {
//																		Thread.sleep(5000);
//																		System.exit(0);
//																	} catch (InterruptedException e1) {}
																}

																sendMessage = new Thread(new Runnable() {
																	@Override
																	public void run() {
																		// String temb;
																		while (true) {

																			// read the message to deliver.
																			btnNut.addActionListener(new ActionListener() {
																				public void actionPerformed(ActionEvent e) {
																					String temb = txtUser.getText();
																					String nameFrom = txtFrom.getText();
																					String msg;
																					if (rdbFrom.isSelected()) {
																						msg ="Tobecontinued!";// "CMD_CHAT " + name + " " + nameFrom.trim() + " " + temb;
																						txtChat.append("[Tôi]: "+msg+"\n");
																					} else {
																						msg =temb;
																						txtChat.append("[Tôi]: "+msg+"\n");
																					}
																					txtUser.setText("");
																					byte[] msgByte = msg.getBytes();
																					try {
																						dos.write(msgByte, 0, msgByte.length);
																					} catch (IOException e1) {
																						JOptionPane.showMessageDialog(null, "Disconect server !");
																						System.exit(1);
																					}
																				}
																			});
																			txtUser.addKeyListener(new KeyAdapter() {
																				public void keyPressed(KeyEvent e) {
																					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
																						String temb = txtUser.getText();
																						String nameFrom = txtFrom.getText();
																						String msg;
																						if (rdbFrom.isSelected()) {
																							msg ="Tobecontinued!";// "CMD_CHAT " + name + " " + nameFrom.trim() + " " + temb;
																							txtChat.append("[Tôi]: "+msg+"\n");
																						} else {
																							msg =temb;
																							txtChat.append("[Tôi]: "+msg+"\n");
																						}
																						txtUser.setText("");
																						byte[] msgByte = msg.getBytes();
																						try {
																							dos.write(msgByte, 0, msgByte.length);
																						} catch (IOException e1) {
																							JOptionPane.showMessageDialog(null, "Disconect server !");
																							System.exit(1);
																						}
																					}
																				}
																			});
																			
																			break;
																		}
																	}
																});
																// -------------------------------------
																readMessage = new Thread(new Runnable() {
																	@Override
																	public void run() {

																		while (true) {
																			try {
																				// read the message sent to this client
																				String msg = new String(recive()).trim();
																				if (msg.contains("send to you:")) {
																					txtChat.append(msg.trim() + "\n");
																				} else
																					txtChat.append(msg.trim() + "\n");
																			} catch (IOException e) {
																				JOptionPane.showMessageDialog(null, "Disconect server !");
																				txtUser.setEnabled(false);
																				btnNut.setEnabled(false);
																				try {
																					Thread.sleep(10000);
																					System.exit(1);
																				} catch (InterruptedException e1) {}
																				break;
																			}
																		}
																	}

																});

																sendMessage.start();
																readMessage.start();
															}
														});
														btnNewButton.setBounds(186, 15, 133, 30);
														contentPane.add(btnNewButton);
																
																JScrollPane scrollPane = new JScrollPane();
																scrollPane.setBounds(71, 87, 286, 318);
																contentPane.add(scrollPane);
																				
																						txtChat = new JTextArea();
																						scrollPane.setViewportView(txtChat);
																						txtChat.setLineWrap(true);
																						txtChat.setBackground(Color.WHITE);
																						txtChat.setWrapStyleWord(true);
																						txtChat.setEditable(false);
																						txtChat.setRows(10);
																						txtChat.setColumns(20);
																						
																						JTextPane textPane = new JTextPane();
																						textPane.setText("  ");
																						scrollPane.setRowHeaderView(textPane);
																		
																				JButton btnOnline = new JButton("Online");
																				btnOnline.setBounds(491, 54, 102, 23);
																				contentPane.add(btnOnline);
																				btnOnline.setVerticalAlignment(SwingConstants.BOTTOM);
																				btnOnline.setFont(new Font("Tahoma", Font.BOLD, 12));
																				btnOnline.setForeground(Color.LIGHT_GRAY);
																				btnOnline.setEnabled(false);
																
																		JTextPane txtList = new JTextPane();
																		txtList.setForeground(Color.LIGHT_GRAY);
																		txtList.setText("Tobecontinued");
																		txtList.setBounds(502, 126, 84, 23);
																		contentPane.add(txtList);
																		txtList.setEditable(false);
																		txtList.setBackground(Color.WHITE);
																				
																				JLabel lblNewLabel_5 = new JLabel("New label");
																				lblNewLabel_5.setEnabled(false);
																				lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
																				lblNewLabel_5.setIcon(new ImageIcon(TEST.class.getResource("/png/Lovepik_com-400656489-dialog-box.png")));
																				lblNewLabel_5.setBounds(50, 435, 66, 48);
																				contentPane.add(lblNewLabel_5);
																				
																				JLabel lblNewLabel_4 = new JLabel("");
																				lblNewLabel_4.setIcon(new ImageIcon(TEST.class.getResource("/png/khung gi do.png")));
																				lblNewLabel_4.setBounds(478, 270, 151, 162);
																				contentPane.add(lblNewLabel_4);
																				
																				JLabel lblNewLabel_3 = new JLabel("");
																				lblNewLabel_3.setIcon(new ImageIcon(TEST.class.getResource("/png/khung hien thi thong tin.png")));
																				lblNewLabel_3.setBounds(461, 54, 168, 162);
																				contentPane.add(lblNewLabel_3);
																				
																				JLabel lblNewLabel_2 = new JLabel("");
																				lblNewLabel_2.setIcon(new ImageIcon(TEST.class.getResource("/png/chat.png")));
																				lblNewLabel_2.setBounds(10, -139, 787, 703);
																				contentPane.add(lblNewLabel_2);

		

	}
}
