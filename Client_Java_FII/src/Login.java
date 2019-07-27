

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jdk.internal.dynalink.linker.LinkerServices.Implementation;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Window.Type;
public class Login  extends javax.swing.JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Login() {
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Đăng nhập - Ahihi Chat");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 644, 397);
		contentPane = new JPanel();
		contentPane.setToolTipText("Ahihi Chat");
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);                                                                
		contentPane.setLayout(null);
		
		JLabel lblV = new JLabel("v0.1");
		lblV.setForeground(UIManager.getColor("Button.light"));
		lblV.setBounds(10, 449, 46, 14);
		contentPane.add(lblV);

		txtID = new JTextField();
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtID.setHorizontalAlignment(SwingConstants.CENTER);
		txtID.setText("Enter account");
		txtID.setBounds(220, 146, 199, 42);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		JButton btnLogin = new JButton("ACCEPT");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLogin.setForeground(UIManager.getColor("Button.foreground"));
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					String name = txtID.getText();
					if (name.equals("Enter account")) {
						JOptionPane.showMessageDialog(null, "Please enter account !","Thông báo",JOptionPane.WARNING_MESSAGE);
					}
					else if(name.equals("")) {
						JOptionPane.showMessageDialog(null, "Please enter user name in box Singin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
					}
					else if(name.length()>30){
						JOptionPane.showMessageDialog(null, "The name isn't more than 30 characters!", "Thông báo", JOptionPane.WARNING_MESSAGE);
					}else {
						String username = txtID.getText();
						TEST test = new TEST();
						test.setVisible(true);
						setVisible(false);
						test.setName(username);
					}
				}
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtID.getText();
				if (name.equals("Enter account")) {
					JOptionPane.showMessageDialog(null, "Please enter account !","Thông báo",JOptionPane.WARNING_MESSAGE);
				}
				else if(name.equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter user name in box Singin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				}
				else if(name.length()>30){
					JOptionPane.showMessageDialog(null, "The name isn't more than 30 characters!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				}else {
					String username = txtID.getText();
					TEST test = new TEST();
					test.setVisible(true);
					setVisible(false);
					test.setName(username);
				}
			}
		});
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setBounds(265, 207, 104, 31);
		contentPane.add(btnLogin);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/png/rsz_presentation1_2.png")));
		lblNewLabel.setBounds(0, 0, 640, 390);
		contentPane.add(lblNewLabel);
	}
}
