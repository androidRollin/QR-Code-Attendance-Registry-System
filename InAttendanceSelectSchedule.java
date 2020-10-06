import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class InAttendanceSelectSchedule extends JDialog {
	Connection connection = null;

	private final JPanel contentPanel = new JPanel();
	private JComboBox cbSched;
	
	public String getSelectedItemCB()
	{
		return String.valueOf(cbSched.getSelectedItem());
	}

	public void fillComboBox(JComboBox cbSched) {
		try {
			String query = "select SCHEDULECODE from SCHEDULE ORDER BY SCHEDULECODE ASC";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) 
			{
				cbSched.addItem(rs.getString("SCHEDULECODE"));
			}
			pst.close();
			rs.close();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}	
	}
	
	public String getDay(String pKey) throws ParseException {
		String day="";
		try 
		{
			String query = "select DAY from SCHEDULE where SCHEDULECODE ='"+pKey+"' ";
		    PreparedStatement pst = connection.prepareStatement(query);
		    ResultSet rs = pst.executeQuery();
		    
		    rs.next();
		    day = rs.getString("DAY");
		    
		    rs.close();
			pst.close();			
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		return day;
	}
	
	public String getStart(String pKey) throws ParseException {
		String start = " ";
		try 
		{
			String query = "select START from SCHEDULE where SCHEDULECODE ='"+pKey+"' ";
		    PreparedStatement pst = connection.prepareStatement(query);
		    ResultSet rs = pst.executeQuery();
		    
		    rs.next();
		    start = rs.getString("START");
		    
		    rs.close();
			pst.close();			
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("hh:mm aa");
		DateFormat outputFormat = new SimpleDateFormat("HH:mm");
		Date date = null;
		String conDate = null;
		date = df.parse(start);
		conDate = outputFormat.format(date);
		return conDate;
	}
	
	public String getEnd(String pKey) throws ParseException {
		String end = " ";
		try 
		{
			String query = "select END from SCHEDULE where SCHEDULECODE ='"+pKey+"' ";
		    PreparedStatement pst = connection.prepareStatement(query);
		    ResultSet rs = pst.executeQuery();
		    
		    rs.next();
		    end = rs.getString("END");
		    
		    rs.close();
			pst.close();			
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("hh:mm aa");
		DateFormat outputFormat = new SimpleDateFormat("HH:mm");
		Date date = null;
		String conDate = null;
		date = df.parse(end);
		conDate = outputFormat.format(date);
		return conDate;
	}
	

	
	public int getGP(String pKey) throws ParseException {
		int graceP=0;
		try 
		{
			String query = "select GRACEPERIOD from SCHEDULE where SCHEDULECODE ='"+pKey+"' ";
		    PreparedStatement pst = connection.prepareStatement(query);
		    ResultSet rs = pst.executeQuery();
		    
		    rs.next();
		    graceP = rs.getInt("GRACEPERIOD");
		    
		    rs.close();
			pst.close();			
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		return graceP;
	}
	
	public int getHrsClass(String startTime, String endTime) throws ParseException
	{
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Conversion into a Format
	    Date classsched = sdf.parse(startTime); // Conversion of String time to Date
	    Date studentAT = sdf.parse(endTime); // Conversion of String time to Date
	    long elapsed = studentAT.getTime() - classsched.getTime(); //Subtraction to know 
	    int gapHrs = (int) TimeUnit.MILLISECONDS.toHours(elapsed); // conversion of milliseconds to minutes
	    return gapHrs;
	}
	/**
	 * Launch the application.
	 */
	public int getAbsentCondition(int totalHrs)
	{
		int absentCondiTime = 0;
		if (totalHrs >= 3) // if class is equal to 3 hrs
		{
			absentCondiTime = 60; //absent time if you are 60 minutes late
		}
		else if (totalHrs >= 2)
		{
			absentCondiTime = 40;
		}
		else if (totalHrs >= 1.50)
		{
			absentCondiTime = 30;
		}
		else if (totalHrs >= 1)
		{
			absentCondiTime = 20;
		}
		else
		{
			absentCondiTime = 20;
		}
		return absentCondiTime;
		/***************Another Condition,Roline Gwaps***************/
	}
	
	public String checkAttendance(String studentArrival, String classStartTime,int gracePeriod, int absentCondiTime)
			throws ParseException
	{
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Conversion into a Format
	    Date classStart = sdf.parse(classStartTime); // Conversion of String time to Date
	    Date studentAT = sdf.parse(studentArrival); // Conversion of String time to Date
	    long elapsed = studentAT.getTime() - classStart.getTime(); //Subtraction to know 
	    int gapMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(elapsed); // conversion of milliseconds to minutes
	  //  System.out.println("Gap minutes" + gapMinutes);
	    
        if(gapMinutes <= gracePeriod )
        {
        	//System.out.print("Teacher: You are On-Time Present");
        	// Write Present on the database
        	return "P";
        	
        }
        else if(gapMinutes > gracePeriod && gapMinutes < absentCondiTime)
        {
        	//System.out.print("Teacher: You are Late");
        	// Write Late on the database
        	return "L";
        }
        else {
			//System.out.print("Teacher: You are Absent");
			// Write Absent on the database
			return "A";
        }
	}
	
	public String getDateToday()
	{
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String date = df.format(now);
		return date;
	}
	
	public String getDateTimeToday()
	{
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
		LocalDateTime now = LocalDateTime.now();
		String date = df.format(now);
		return date;
	}
	
	public String twelveHRformat(String twentyfrFormat) throws ParseException
	{
		DateFormat df = new SimpleDateFormat ("HH:mm");//24 hr format
		DateFormat outputDf = new SimpleDateFormat("h:mm aa");//12 hr format 
		
		Date date = df.parse(twentyfrFormat);
		String output = outputDf.format(date);
		return output;
		
	}
	
	public void readCSV(String start, int graceP, int absentCondition) throws ParseException
	{
		start = twelveHRformat(start);
		String filename = "cleanData.csv";
		String studSchedCode = String.valueOf(cbSched.getSelectedItem());
		String attendDate = getDateToday();
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
			inputStream.next();// SKIPS THE COLUMN HEADER
			String strangers="",checkAlready="";
			while(inputStream.hasNext()) {
				String data = inputStream.next();
				String[] values = data.split(",");
			    String status = checkAttendance(values[1],start,graceP,absentCondition);
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
			    
				System.out.println("Studno "+ values[0]+ " " +"AT:"+ status);
				
				if(pys.equalsIgnoreCase(pys1))
				{
				try {
					String query = "insert into ATTENDANCE (SCHEDCODE,ATTENDATE,STUDNO,STATUS,CODE)"
						+ " values (?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
				
					pst.setString(1,studSchedCode);
					pst.setString(2,attendDate);
					pst.setString(3,values[0]);
					pst.setString(4,status);
					pst.setString(5,attendDate+" "+day+" "+start+" "+values[0]);
					
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
			if (!checkAlready.equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(null,"Student Already Checked for this day!!\n" + checkAlready,
					"Attendance Alert!",
					JOptionPane.ERROR_MESSAGE);
			if (!strangers.equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(null,"Intruder Alert!!\n" + strangers,
					"Safety Warning!!",
					JOptionPane.WARNING_MESSAGE);
				
			inputStream.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			InAttendanceSelectSchedule dialog = new InAttendanceSelectSchedule();
			dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InAttendanceSelectSchedule() {
		connection = db.dbConnector();	
		setTitle("Check Attendance");
		setBounds(100, 100, 450, 220);
		Image icon1 = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		setIconImage(icon1);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2,
			(dim.height/2-this.getSize().height/2)+(-70));
		
		cbSched = new JComboBox();
		cbSched.setBounds(41, 69, 350, 33);
		contentPanel.add(cbSched);
		
		JLabel lblPickASchedule = new JLabel("Pick a Schedule to Check Attendance:");
		lblPickASchedule.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPickASchedule.setBounds(41, 39, 275, 14);
		contentPanel.add(lblPickASchedule);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//JOptionPane.showMessageDialog(null, "Please Wait, Setting up Camera may take time");
						MakeSureDayToday a = new MakeSureDayToday();
						int ansToContinue = a.getIfSameDay(cbSched);
						if (ansToContinue == 0)
						{
							RealTimeDetector rtd = new RealTimeDetector();
							dispose();
						    try 
							{
								String selected = String.valueOf(cbSched.getSelectedItem());
								new QRCamera();
								try {
									String start = getStart(selected);
									String end = getEnd(selected);
									int hrs = getHrsClass(start,end);
									String day = getDay(selected);
									int graceP = getGP(selected);
									int absentCondition = getAbsentCondition(hrs);
									readCSV(start,graceP,absentCondition);
									File f = new File("CheckStudet.csv");
									f.delete();
	
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
							} 
							catch (IOException | InterruptedException e1) 
							{
								e1.printStackTrace();
							}
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
		
		
		fillComboBox(cbSched);
	}
}
