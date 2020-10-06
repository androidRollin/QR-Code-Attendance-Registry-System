import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.sql.*;

public class RegisterAccount extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private static RegisterAccount frame;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new RegisterAccount();
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
	public RegisterAccount() {
	    connection = db.dbConnector();	
		setTitle("Create An Account");
		Image icon1 = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		setIconImage(icon1);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 455, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2,
				(dim.height/2-this.getSize().height/2)+(-70));
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(109, 60, 86, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblCreateAPassword = new JLabel("Create a password");
		lblCreateAPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCreateAPassword.setBounds(109, 78, 116, 26);
		contentPane.add(lblCreateAPassword);
		
		JLabel lblReenterPassword = new JLabel("Re-enter password");
		lblReenterPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReenterPassword.setBounds(109, 103, 140, 26);
		contentPane.add(lblReenterPassword);
		
		textField = new JTextField();
		textField.setBounds(251, 54, 147, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(251, 108, 147, 20);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(251, 83, 147, 20);
		contentPane.add(passwordField_1);
		
		JButton btnCreateAnAccount = new JButton("Confirm");
		btnCreateAnAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(passwordField.getText().equals(passwordField_1.getText()) && !passwordField.getText().isEmpty())
				{
					try {
						String query = "insert into LOGIN (username,password) values(?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, textField.getText());
						pst.setString(2, passwordField_1.getText());
						pst.execute();
						JOptionPane.showMessageDialog(null, "Account Created Successfully");
						dispose();
						EntryFrame Lframe = new EntryFrame();
						Lframe.setVisible(true);
						pst.close();
					}
					catch(Exception err)
					{
						JOptionPane.showMessageDialog(null, "Username,is already taken please try another username");
					}
					
				}
				else
				{
					if(passwordField.getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "You have not yet entered a valid password");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Password doesn't match");
					}
				}
			}
		});
		btnCreateAnAccount.setBounds(163, 149, 86, 32);
		contentPane.add(btnCreateAnAccount);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				EntryFrame first = new EntryFrame();
				first.setVisible(true);
			}
		});
		btnCancel.setBounds(261, 149, 86, 32);
		contentPane.add(btnCancel);
		
		JLabel Image = new JLabel("");
		Image imgRegist = new ImageIcon(this.getClass().getResource("/img/Users.png")).getImage();
		Image.setIcon(new ImageIcon(imgRegist));
		Image.setBounds(28, 66, 77, 64);
		contentPane.add(Image);
		
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
