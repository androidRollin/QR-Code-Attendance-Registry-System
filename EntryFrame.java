import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.sql.*;

public class EntryFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtUser;
	Connection connection = null;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EntryFrame frame = new EntryFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EntryFrame() {
	    connection = db.dbConnector();	
	    
		setTitle("Login");
		Image icon1 = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		setIconImage(icon1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 398, 237);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2,
				(dim.height/2-this.getSize().height/2)+(-70));
		
		JLabel lblLogin = new JLabel("Username");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogin.setBounds(130, 61, 94, 14);
		contentPane.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(130, 89, 94, 14);
		contentPane.add(lblPassword);
		
		txtUser = new JTextField();
		txtUser.setBounds(211, 60, 145, 20);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordField.setBounds(211, 86, 145, 20);
		contentPane.add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		Image imgLogin1 = new ImageIcon(this.getClass().getResource("/img/LoginImg.png")).getImage();
		btnLogin.setIcon(new ImageIcon(imgLogin1));
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select * from LOGIN where username = ? and password = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1,txtUser.getText());
					pst.setString(2,passwordField.getText());
					
					ResultSet rs = pst.executeQuery();
					int count = 0;
					while(rs.next())
					{
						count++;
					}
					if(count == 1)
					{
						Application window = new Application();
						window.frmAttendanceRegistry.setVisible(true);
						dispose();
						try {
							connection.close();
						} catch (SQLException err) {
							// TODO Auto-generated catch block
							err.printStackTrace();
						}
					}
					else if ( count >1)
					{
						JOptionPane.showMessageDialog(null, "Duplicate Username and Password");
					}
					else 
					{
						if((txtUser.getText()).isBlank() && (passwordField.getText()).isBlank())
						{
							JOptionPane.showMessageDialog(null,"Please input a username and password");	
						}
						else if((txtUser.getText()).isBlank())
						{
							JOptionPane.showMessageDialog(null,"Please input a username");	
						}
						else if((passwordField.getText()).isBlank())
						{
							JOptionPane.showMessageDialog(null,"Please input a password");	
						}
						else
						{
						JOptionPane.showMessageDialog(null,"User and Password doesn't match, Please Try Again");
						}
					}
					rs.close();
					pst.close();
					
				}
				catch (Exception err)
				{
					JOptionPane.showMessageDialog(null, err);
				}
			}
		});
		btnLogin.setToolTipText("Login your Account");
		btnLogin.setBounds(245, 144, 111, 33);
		contentPane.add(btnLogin);
		
		JButton btnSignUp = new JButton("Sign Up");
		Image imgLogin2 = new ImageIcon(this.getClass().getResource("/img/pen.png")).getImage();
		btnSignUp.setIcon(new ImageIcon(imgLogin2));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String key = JOptionPane.showInputDialog(null, "Enter the security key");
				dispose();
				SecurityKey key = new SecurityKey();
				key.setVisible(true);
			}
		});
		btnSignUp.setToolTipText("Create an account");
		btnSignUp.setBounds(130, 144, 105, 33);
		contentPane.add(btnSignUp);
		
		
		JLabel ImageLogin = new JLabel("");
		Image imgLogin = new ImageIcon(this.getClass().getResource("/img/Login.png")).getImage();
		ImageLogin.setIcon(new ImageIcon(imgLogin));
		ImageLogin.setBounds(17, 44, 103, 96);
		contentPane.add(ImageLogin);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int x = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit the application ?",
						"Confirm !",JOptionPane.YES_NO_OPTION);
				
				if(x == JOptionPane.YES_OPTION)
				{
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				else
				{
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});

	}
}
