import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.*;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class FetchStudentData extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox comboBox;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
	public void fillComboBox() {
		try {
			String query = "select DISTINCT PYS from STUDENT";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) 
			{
				comboBox.addItem(rs.getString("PYS"));
				
			}
			pst.close();
			rs.close();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			FetchStudentData dialog = new FetchStudentData();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FetchStudentData() {
		connection = db.dbConnector();	
		
		Image icon1 = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		setIconImage(icon1);
		setTitle("Qr Code Maker");
		setBounds(100, 100, 400, 204);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2,
			(dim.height/2-this.getSize().height/2)+(-30));
		
		comboBox = new JComboBox();
		comboBox.setBounds(234, 49, 100, 33);
		contentPanel.add(comboBox);
		
		JLabel lblMakeQrCode = new JLabel("Make Batches of QR code for :");
		lblMakeQrCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMakeQrCode.setBounds(35, 56, 214, 14);
		contentPanel.add(lblMakeQrCode);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String query = "select STUDENTNO,STUDENTNAME from STUDENT where PYS = ? order by STUDENTNAME asc";
							String pys = String.valueOf(comboBox.getSelectedItem());
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, pys);
							ResultSet rs = pst.executeQuery();
							FileWriter csvWriter = new FileWriter("QRData.csv");
							csvWriter.append("StudentNo,Name,Section\n");
							//csvWriter.append(c)
							String number,name,csvline;

							while(rs.next()) 
							{
								number = rs.getString("STUDENTNO");
								name = rs.getString("STUDENTNAME");
								name = name.replace(","," ");
								csvline = number+","+name+","+pys+"\n";
								//System.out.print(number+","+name+","+pys+"\n");
								csvWriter.append(csvline);
							}
							csvWriter.flush();
							csvWriter.close();
							pst.close();
							rs.close();
						}
						catch(Exception e1)
						{
							e1.printStackTrace();
						}
						try {
							new GenerateFromDatabase();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		fillComboBox();
	}
}
