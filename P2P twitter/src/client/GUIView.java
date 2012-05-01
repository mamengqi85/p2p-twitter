package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.util.Set;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class GUIView extends JFrame implements Runnable, WindowListener{

	private ClientController cc;
	private JPanel contentPane;
	private JTextField textFieldR;
	private JPasswordField passwordField;
	private JTextField textFieldL;
	private JPasswordField passwordField_1;
	private JTextField textFieldS;
	private JTextField textFieldG1;
	private JTextField textFieldG2;
	private JTextArea textArea_1;
	private JTextField txtGroupid;
	private int rfSpeed = 20000;//5 secs
	private static String rtText = "";

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
			
			textFieldR = new JTextField();
			textFieldR.setBounds(204, 55, 186, 24);
			registerTab.add(textFieldR);
			textFieldR.setColumns(10);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(204, 89, 186, 24);
			registerTab.add(passwordField);
			
			JButton btnRegister = new JButton("register");
			btnRegister.setBounds(204, 134, 93, 23);
			registerTab.add(btnRegister);
			btnRegister.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String username = textFieldR.getText();
					String password = new String(passwordField.getPassword());
					cc.register(username, password);
				}
			});
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
			
			textFieldL = new JTextField();
			textFieldL.setColumns(10);
			textFieldL.setBounds(204, 55, 186, 24);
			loginTab.add(textFieldL);
			
			passwordField_1 = new JPasswordField();
			passwordField_1.setBounds(204, 89, 186, 24);
			loginTab.add(passwordField_1);
			
			JButton btnLogin = new JButton("login");
			btnLogin.setBounds(204, 134, 93, 23);
			loginTab.add(btnLogin);
			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String username = textFieldL.getText();
					String password = new String(passwordField_1.getPassword());
					System.out.println(username);
					System.out.println(password);
					cc.login(username, password);
				}
			});
		}
		
		JPanel postTab = new JPanel();
		tabbedPane.addTab("post", null, postTab, null);
		postTab.setLayout(null);
		
		txtGroupid = new JTextField();
		txtGroupid.setText("GroupID");
		txtGroupid.setBounds(10, 217, 74, 22);
		postTab.add(txtGroupid);
		txtGroupid.setColumns(10);
		
		textFieldS = new JTextField();
		textFieldS.setBounds(89, 217, 318, 22);
		postTab.add(textFieldS);
		textFieldS.setColumns(10);
		
		JButton btnSend = new JButton("send");
		btnSend.setBounds(409, 217, 80, 23);
		postTab.add(btnSend);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String groupID = txtGroupid.getText();
				String message = textFieldS.getText();
				cc.preparePostMessage(groupID, message);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 10, 469, 197);
		postTab.add(scrollPane);
		
		textArea_1 = new JTextArea();
		scrollPane.setViewportView(textArea_1);
		textArea_1.setEditable(false);
		textArea_1.setWrapStyleWord(true);
		textArea_1.setLineWrap(true);
		
		Timer timer = new Timer(rfSpeed, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	System.out.println("retrieveddd");
	        	System.out.println(textFieldL.getText());
	        	System.out.println("class"+rtText);
	        	textArea_1.setText(rtText);
	        	textArea_1.repaint();
	        	System.out.println("area"+textArea_1.getText());
	        	cc.retrieve();
	        }
	    });
		timer.start();

		JPanel groupTab = new JPanel();
		tabbedPane.addTab("group", null, groupTab, null);
		groupTab.setLayout(null);
		{
			JLabel label = new JLabel("Create Group");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			label.setBounds(101, 40, 93, 24);
			groupTab.add(label);
			
			JLabel label_1 = new JLabel("Join Group");
			label_1.setHorizontalAlignment(SwingConstants.CENTER);
			label_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			label_1.setBounds(107, 119, 93, 24);
			groupTab.add(label_1);
			
			textFieldG1 = new JTextField();
			textFieldG1.setColumns(10);
			textFieldG1.setBounds(204, 41, 186, 24);
			groupTab.add(textFieldG1);
			
			textFieldG2 = new JTextField();
			textFieldG2.setColumns(10);
			textFieldG2.setBounds(204, 120, 186, 24);
			groupTab.add(textFieldG2);
			
			JButton btnCreateG = new JButton("create");
			btnCreateG.setBounds(204, 75, 93, 23);
			groupTab.add(btnCreateG);
			btnCreateG.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String groupName = textFieldG1.getText();
					cc.createGroup(groupName);
				}
			});
			
			JButton btnJoinG = new JButton("join");
			btnJoinG.setBounds(204, 154, 93, 23);
			groupTab.add(btnJoinG);
			btnJoinG.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String groupName = textFieldG2.getText();
					cc.joinGroup(groupName);
				}
			});
		}
		
		JPanel aboutTab = new JPanel();
		tabbedPane.addTab("about", null, aboutTab, null);
	}
	
	public void showMessage(String str) {
		JOptionPane.showMessageDialog(this, str);
	}
	
	public void setRetrieveText(final Set<Serializable> text) {
		/*
		System.out.println(textArea_1.getText());
	    Timer timer = new Timer(10, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	String str = text.toString();
	        	textArea_1.setText(str);
	        	textArea_1.repaint();
	        }
	    });
	    timer.start();
		System.out.println(text);*/
		rtText = text.toString();
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
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		cc.leave();
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
