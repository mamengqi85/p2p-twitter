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
import java.awt.Color;

public class GUIView extends JFrame implements Runnable, WindowListener{

	private ClientController cc;
	private JPanel contentPane;
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
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Nanum Pen Script", Font.BOLD, 17));
		tabbedPane.setBounds(10, 10, 494, 275);
		contentPane.add(tabbedPane);
		
				JPanel groupTab = new JPanel();
				groupTab.setBackground(Color.BLACK);
				tabbedPane.addTab("group", null, groupTab, null);
				groupTab.setLayout(null);
				{
					JLabel label = new JLabel("Create Group");
					label.setForeground(Color.WHITE);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setFont(new Font("Nanum Pen Script", Font.BOLD, 18));
					label.setBounds(101, 40, 93, 24);
					groupTab.add(label);
					
					JLabel label_1 = new JLabel("Join Group");
					label_1.setForeground(Color.WHITE);
					label_1.setHorizontalAlignment(SwingConstants.CENTER);
					label_1.setFont(new Font("Nanum Pen Script", Font.BOLD, 18));
					label_1.setBounds(107, 119, 93, 24);
					groupTab.add(label_1);
					
					textFieldG1 = new JTextField();
					textFieldG1.setForeground(Color.BLACK);
					textFieldG1.setFont(new Font("Nanum Pen Script", Font.BOLD, 18));
					textFieldG1.setColumns(10);
					textFieldG1.setBounds(204, 41, 186, 24);
					groupTab.add(textFieldG1);
					
					textFieldG2 = new JTextField();
					textFieldG2.setForeground(Color.BLACK);
					textFieldG2.setFont(new Font("Nanum Pen Script", Font.BOLD, 18));
					textFieldG2.setColumns(10);
					textFieldG2.setBounds(204, 120, 186, 24);
					groupTab.add(textFieldG2);
					
					JButton btnCreateG = new JButton("create");
					btnCreateG.setForeground(Color.BLACK);
					btnCreateG.setFont(new Font("Nanum Pen Script", Font.BOLD, 18));
					btnCreateG.setBounds(204, 75, 93, 23);
					groupTab.add(btnCreateG);
					btnCreateG.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String groupName = textFieldG1.getText();
							cc.createGroup(groupName);
						}
					});
					
					JButton btnJoinG = new JButton("join");
					btnJoinG.setForeground(Color.BLACK);
					btnJoinG.setFont(new Font("Nanum Pen Script", Font.BOLD, 18));
					btnJoinG.setBounds(204, 154, 93, 23);
					groupTab.add(btnJoinG);
					btnJoinG.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String groupName = textFieldG2.getText();
							cc.joinGroup(groupName);
						}
					});
				}
		
		JPanel postTab = new JPanel();
		postTab.setBackground(Color.BLACK);
		tabbedPane.addTab("post", null, postTab, null);
		postTab.setLayout(null);
		
		txtGroupid = new JTextField();
		txtGroupid.setBounds(85, 152, 74, 22);
		postTab.add(txtGroupid);
		txtGroupid.setColumns(10);
		
		textFieldS = new JTextField();
		textFieldS.setBounds(85, 186, 277, 22);
		postTab.add(textFieldS);
		textFieldS.setColumns(10);
		
		JButton btnSend = new JButton("send");
		btnSend.setFont(new Font("Nanum Pen Script", Font.BOLD, 18));
		btnSend.setBounds(374, 187, 80, 23);
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
		scrollPane.setBounds(10, 10, 439, 130);
		postTab.add(scrollPane);
		
		textArea_1 = new JTextArea();
		scrollPane.setViewportView(textArea_1);
		textArea_1.setEditable(false);
		textArea_1.setWrapStyleWord(true);
		textArea_1.setLineWrap(true);
		
		JLabel lblGroupid = new JLabel("GroupID");
		lblGroupid.setForeground(Color.WHITE);
		lblGroupid.setFont(new Font("Nanum Pen Script", Font.BOLD, 18));
		lblGroupid.setBounds(12, 155, 61, 16);
		postTab.add(lblGroupid);
		
		JLabel lblText = new JLabel("Text");
		lblText.setForeground(Color.WHITE);
		lblText.setFont(new Font("Nanum Pen Script", Font.BOLD, 18));
		lblText.setBounds(12, 189, 61, 16);
		postTab.add(lblText);
		
		Timer timer = new Timer(rfSpeed, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	System.out.println("retrieveddd");
	        	System.out.println("class"+rtText);
	        	textArea_1.setText(rtText);
	        	textArea_1.repaint();
	        	System.out.println("area"+textArea_1.getText());
	        	cc.retrieve();
	        }
	    });
		timer.start();
		
		JPanel aboutTab = new JPanel();
		tabbedPane.addTab("about", null, aboutTab, null);
		aboutTab.setLayout(null);
		
		JLabel lblTaoLiu = new JLabel("Tao Liu");
		lblTaoLiu.setBounds(72, 73, 46, 16);
		aboutTab.add(lblTaoLiu);
		
		JLabel lblNewLabel = new JLabel("ZhiTu Chen");
		lblNewLabel.setBounds(75, 101, 87, 16);
		aboutTab.add(lblNewLabel);
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
