package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame implements Runnable{

	public JFrame frame;
	private JTextField textUsername;
	private JTextField textPassword;
	private ClientController cc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Login window = new Login();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login(ClientController cc) {
		this.cc = cc;
		initialize();
	}

	
	public void ClearInput(){
		textPassword.setText("");
		textUsername.setText("");
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textUsername = new JTextField();
		textUsername.setBounds(194, 94, 134, 28);
		frame.getContentPane().add(textUsername);
		textUsername.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setBounds(194, 149, 134, 28);
		frame.getContentPane().add(textPassword);
		textPassword.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("username");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Nanum Pen Script", Font.BOLD, 20));
		lblNewLabel.setBounds(100, 98, 82, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Nanum Pen Script", Font.BOLD, 20));
		lblPassword.setBounds(100, 155, 82, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblPpTwitter = new JLabel("p2p Twitter  1.0");
		lblPpTwitter.setForeground(Color.WHITE);
		lblPpTwitter.setFont(new Font("Nanum Brush Script", Font.BOLD, 29));
		lblPpTwitter.setBounds(158, 29, 170, 28);
		frame.getContentPane().add(lblPpTwitter);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cc.login(textUsername.getText(), textPassword.getText());
				frame.dispose();
			}
		});
		btnLogin.setFont(new Font("Nanum Pen Script", Font.BOLD, 16));
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setBounds(262, 206, 66, 23);
		frame.getContentPane().add(btnLogin);
		
		JButton btnRegister = new JButton("register");
		btnRegister.setFont(new Font("Nanum Pen Script", Font.PLAIN, 16));
		btnRegister.setBounds(183, 203, 82, 29);
		frame.getContentPane().add(btnRegister);
	}

	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login(cc);
					window.frame.setVisible(true);
					window.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
