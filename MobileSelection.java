import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MobileSelection extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox cbSched;
	Connection connection = null;

	public void readCSV(String start, int graceP, int absentCondition) throws ParseException
	{
		InAttendanceSelectSchedule obj = new InAttendanceSelectSchedule();
		start = obj.twelveHRformat(start);
		String filename = "MobileData.csv";
		String studSchedCode = String.valueOf(cbSched.getSelectedItem());
		File file = new File(filename); //TODO: read about File
		String day="",pys="",pys1="";
		try 
		{
			String query = "select DAY,PYS from SCHEDULE where SCHEDULECODE = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, String.valueOf(cbSched.getSelectedItem()));
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
			day = rs.getString("DAY");
			pys = rs.getString("PYS");
			}
			rs.close();
			pst.close();

		}	
		catch(Exception x)
		{
			x.printStackTrace();
		}
		

		try {
			Scanner inputStream = new Scanner(file);
			String a,b,c;
			a = inputStream.next();// SKIPS THE COLUMN HEADER
			b= inputStream.next();// SKIPS THE NEXT COLUMN HEADER BECAUSE OF THE SPACES OF THE MOBILE
			c = inputStream.next();// SKIPS THE NEXT COLUMN HEADER BECAUSE OF THE SPACES OF THE MOBILE
			String pass = a + b + c;
			//System.out.println(a + b + c + ".");
			String strangers="",checkAlready="";
			while(inputStream.hasNext()) {
				String data = inputStream.next();
				String[] values = data.split(",");
				//System.out.print(values[0] + values[1]);
			    String status = obj.checkAttendance(values[2],start,graceP,absentCondition);
				//String status = checkAttendance(values[1],"7:30",15,60);
			    
			    try
			    {
				String query = "select PYS from STUDENT where STUDENTNO = ?";
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setString(1,values[0]);
				ResultSet rs = pst.executeQuery();
				
					while(rs.next())
					{
						pys1 = rs.getString("PYS");
					}
				
				rs.close();
				pst.close();
			    }
			    catch(Exception x)
			    {
					x.printStackTrace();
			    }
			    
				//System.out.println("Studno "+ values[0]+ " " +"AT:"+ status);
				
				if(pys.equalsIgnoreCase(pys1))
				{
				try {
					String query = "insert into ATTENDANCE (SCHEDCODE,ATTENDATE,STUDNO,STATUS,CODE)"
						+ " values (?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
				
					pst.setString(1,studSchedCode);
					pst.setString(2,values[1]);
					pst.setString(3,values[0]);
					pst.setString(4,status);
					pst.setString(5,values[1]+" "+day+" "+start+" "+values[0]);
					
					pst.executeUpdate();
					pst.close();

				}
				catch(Exception x)
				{
					try {
					String query = "select STUDENTNO from STUDENT where STUDENTNO ='"+values[0]+"' ";
				    PreparedStatement pst = connection.prepareStatement(query);
				    ResultSet rs = pst.executeQuery();
				
				    rs.next();
				    rs.getString("STUDENTNO");

				    rs.close();
					pst.close();
					checkAlready = checkAlready + values[0] + "\n";
					}
					catch(Exception ex)
					{
						System.out.println("Additional identity thiefs");
						strangers = strangers + values[0] + "\n";
					}


					//JOptionPane.showMessageDialog(null,"Student Number: " +values[0]+ " is already checked","Warning!",
						//	JOptionPane.ERROR_MESSAGE);
				}
				
				}
				else
				{
					strangers = strangers + values[0] + "\n";
			//		JOptionPane.showMessageDialog(null,"Student Number: " +values[0]+ "attended is not enrolled in this class",
				//			"Stranger Alert!",
					//		JOptionPane.ERROR_MESSAGE);
				}
			}
			JOptionPane.showMessageDialog(null,"Importing External Data Operation Completed",
					"Mobile Attendance",
					JOptionPane.INFORMATION_MESSAGE);
			if (!strangers.equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(null,"Intruder Alert!!\n" + strangers,
					"Safety Warning!!",
					JOptionPane.WARNING_MESSAGE);
			/**else if (!(pass.equalsIgnoreCase("NAME,ARRIVALDATE,ARRIVALTIME")))
				JOptionPane.showMessageDialog(null,"The *.csv file cannot be recorded. Please make sure it came from"
						+ " QR ATTENDANCE CONTROL Application or select a *.csv file" ,
						"Attendance Alert!",
						JOptionPane.ERROR_MESSAGE);**/
			else if(!checkAlready.equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(null,"Student Already Checked for this day!!\n" + checkAlready,
					"Attendance Alert!",
					JOptionPane.ERROR_MESSAGE);
			else 
				JOptionPane.showMessageDialog(null,"Mobile Attendance Successfully Recorded\n" + checkAlready,
						"Attendance",
						JOptionPane.INFORMATION_MESSAGE);

				
			inputStream.close();
		} 
		catch (FileNotFoundException e) 
		{
		}
		file.delete();

	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MobileSelection dialog = new MobileSelection();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MobileSelection() {
		connection = db.dbConnector();
		setTitle("Check Attendance");
		Image icon1 = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		setIconImage(icon1);
		setBounds(100, 100, 450, 212);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2,
				(dim.height/2-this.getSize().height/2)+(-110));
		
		InAttendanceSelectSchedule obj = new InAttendanceSelectSchedule();
		
		contentPanel.setLayout(null);
		{
			cbSched = new JComboBox();
			cbSched.setBounds(37, 63, 367, 29);
			contentPanel.add(cbSched);
		}
		obj.fillComboBox(cbSched);
		
		JLabel lblSelectScheduleTo = new JLabel("Select a Schedule to record Mobile QR Attendance Data");
		lblSelectScheduleTo.setBounds(37, 38, 319, 14);
		contentPanel.add(lblSelectScheduleTo);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							MobileFile csvClean = new MobileFile();
						} catch (IOException e2) {
							e2.printStackTrace();
						} catch (InterruptedException e2) {
							e2.printStackTrace();
						}
					    String selected = String.valueOf(cbSched.getSelectedItem());
						try {
							String start = obj.getStart(selected);
							String end = obj.getEnd(selected);
							int hrs = obj.getHrsClass(start,end);
							String day = obj.getDay(selected);
							int graceP = obj.getGP(selected);
							int absentCondition = obj.getAbsentCondition(hrs);
							readCSV(start,graceP,absentCondition);

						} catch (ParseException e1) {
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
	}
}
