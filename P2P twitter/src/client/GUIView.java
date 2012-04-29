package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;

public class GUIView extends JFrame implements Runnable{

	private ClientController cc;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel regStatus;
	private JLabel loginStatus;
	private JTextField textField_1;
	private JPasswordField passwordField_1;

	private void init(ClientController cc)
	{
		this.cc = cc;
	}
	/**
	 * Create the frame.
	 */
	public GUIView(final ClientController cc) {
		init(cc);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("P2P Twitter Client");
		setBounds(100, 100, 530, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 494, 275);
		contentPane.add(tabbedPane);
		
		JPanel registerTab = new JPanel();
		tabbedPane.addTab("register", null, registerTab, null);
		registerTab.setLayout(null);
		{
			JLabel lblUsername = new JLabel("Username");
			lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
			lblUsername.setBounds(117, 54, 71, 24);
			registerTab.add(lblUsername);
			
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
			lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			lblPassword.setBounds(117, 88, 71, 24);
			registerTab.add(lblPassword);
			
			textField = new JTextField();
			textField.setBounds(204, 55, 186, 24);
			registerTab.add(textField);
			textField.setColumns(10);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(204, 89, 186, 24);
			registerTab.add(passwordField);
			
			JButton btnRegister = new JButton("register");
			btnRegister.setBounds(204, 134, 93, 23);
			registerTab.add(btnRegister);
			btnRegister.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String username = textField.getText();
					String password = new String(passwordField.getPassword());
					cc.register(username, password);
				}
			});
			
			regStatus = new JLabel("reg");
			regStatus.setFont(new Font("SimSun-ExtB", Font.PLAIN, 15));
			regStatus.setHorizontalAlignment(SwingConstants.CENTER);
			regStatus.setBounds(176, 180, 152, 29);
			registerTab.add(regStatus);
		}
		
		JPanel loginTab = new JPanel();
		tabbedPane.addTab("log in", null, loginTab, null);
		loginTab.setLayout(null);
		{
			JLabel label = new JLabel("Username");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			label.setBounds(117, 54, 71, 24);
			loginTab.add(label);
			
			JLabel label_1 = new JLabel("Password");
			label_1.setHorizontalAlignment(SwingConstants.CENTER);
			label_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			label_1.setBounds(117, 88, 71, 24);
			loginTab.add(label_1);
			
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(204, 55, 186, 24);
			loginTab.add(textField_1);
			
			passwordField_1 = new JPasswordField();
			passwordField_1.setBounds(204, 89, 186, 24);
			loginTab.add(passwordField_1);
			
			JButton btnLogin = new JButton("login");
			btnLogin.setBounds(204, 134, 93, 23);
			loginTab.add(btnLogin);
			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String username = textField.getText();
					String password = new String(passwordField.getPassword());
					cc.login(username, password);
				}
			});
			
			loginStatus = new JLabel("");
			loginStatus.setHorizontalAlignment(SwingConstants.CENTER);
			loginStatus.setFont(new Font("SimSun-ExtB", Font.PLAIN, 15));
			loginStatus.setBounds(171, 169, 152, 29);
			loginTab.add(loginStatus);
		}
		
		JPanel postTab = new JPanel();
		tabbedPane.addTab("post", null, postTab, null);
		
		JPanel groupTab = new JPanel();
		tabbedPane.addTab("group", null, groupTab, null);
		
		JPanel infoTab = new JPanel();
		tabbedPane.addTab("info", null, infoTab, null);
	}
	
	public void setRegStatus(String str) {
		System.out.println(regStatus.getText());
		System.out.println(str);
		regStatus.setText(str);
		this.validate();
	}
	
	public JLabel getLoginStatus() {
		return loginStatus;
	}
	
	/**
	 * Launch the application.
	 */
	@Override
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIView frame = new GUIView(cc);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
