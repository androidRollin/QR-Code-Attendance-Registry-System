import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;

public class ScheduleRegistration extends JFrame {

	private JPanel contentPane;
	private JTextField txtRmNo;
	private JTextField txtGraceP;
	private JTable table;
	private JComboBox<String> CBchoice; 
	private JComboBox DOW;
	private JComboBox BoxST;
	private JComboBox BoxET;
	private static ScheduleRegistration frame;
	//Database
	Connection connection = null;
	private JTextField txtSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ScheduleRegistration();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void refreshTable()
	{
		try {
			String query = "select SCHEDULECODE,PYS,ROOMNO,DAY,START,END,GRACEPERIOD from SCHEDULE";
		    PreparedStatement pst = connection.prepareStatement(query);
	        ResultSet rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		    
		    pst.close();
		    rs.close();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void fillComboBox() {
		try {
			String query = "select PROGRAM from SUBJECT";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) 
			{
				CBchoice.addItem(rs.getString("PROGRAM"));
				
			}
			pst.close();
			rs.close();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		
	}

	/**
	 * Create the frame.
	 */
	public ScheduleRegistration() {
		
		connection = db.dbConnector();	
		Image icon1 = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		setIconImage(icon1);
		setTitle("Schedule Registration");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1283, 480);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setResizable(false);
		this.setLocation(dim.width/2-this.getSize().width/2,
			(dim.height/2-this.getSize().height/2)+(-30));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		CBchoice = new JComboBox();

		CBchoice.setBounds(44, 144, 254, 27);
		contentPane.add(CBchoice);
		
		JLabel lblRoomNo = new JLabel("Room No.");
		lblRoomNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRoomNo.setBounds(44, 182, 92, 14);
		contentPane.add(lblRoomNo);
		
		JLabel lblGraceperiod = new JLabel("GracePeriod");
		lblGraceperiod.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGraceperiod.setBounds(44, 245, 92, 14);
		contentPane.add(lblGraceperiod);
		
		JLabel lblDayOfThe = new JLabel("Day of The Week");
		lblDayOfThe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDayOfThe.setBounds(44, 207, 112, 27);
		contentPane.add(lblDayOfThe);
		
		JLabel lblStartTime = new JLabel("Start Time");
		lblStartTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStartTime.setBounds(44, 270, 112, 27);
		contentPane.add(lblStartTime);
		
		JLabel lblEndTime = new JLabel("End Time");
		lblEndTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEndTime.setBounds(44, 308, 112, 27);
		contentPane.add(lblEndTime);
		
		JButton btnAdd = new JButton("ADD");
		Image imgAdd = new ImageIcon(this.getClass().getResource("/img/add.png")).getImage();
		btnAdd.setIcon(new ImageIcon(imgAdd));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ans = JOptionPane.showConfirmDialog(null, "Do you want to save entered field values ? ","Confirm",JOptionPane.YES_NO_OPTION);
				if (ans == 0)
				{			
					try
					{   
						String query = "select CODE FROM SUBJECT where PROGRAM = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1,String.valueOf( CBchoice.getSelectedItem() ));
						ResultSet rs = pst.executeQuery();
						
						String code = "";
						
						while(rs.next())
						{
							code = rs.getString("CODE");
						}
						
						
						rs.close();
						pst.close();
						
					    query = "insert into SCHEDULE (SCHEDULECODE,ROOMNO,DAY,START,END,GRACEPERIOD,PYS) values ?,?,?,?,?,?,?";
						pst = connection.prepareStatement(query);
						String roomno="",day="",start="",end="",pys="";
						int gracePeriod = 0;
						
						roomno = txtRmNo.getText();
						day = String.valueOf(DOW.getSelectedItem());
						gracePeriod = Integer.valueOf(txtGraceP.getText());
						start = String.valueOf(BoxST.getSelectedItem());
						end = String.valueOf(BoxET.getSelectedItem());
						pys = String.valueOf(CBchoice.getSelectedItem());
						code = code + " " + pys + " "+ day + " "+ start + "-" + end;
						//System.out.println(code);
							
						pst.setString(1,code);
						pst.setString(2,roomno);
						pst.setString(3,day);
						pst.setString(4,start);
						pst.setString(5,end);
						pst.setInt(6,gracePeriod);
						pst.setString(7,pys);

						
						pst.execute();
				        pst.close();
				        
				        txtRmNo.setText("");
				        DOW.setSelectedIndex(-1);
				        txtGraceP.setText("");
				        BoxST.setSelectedIndex(-1);
				        BoxET.setSelectedIndex(-1);

				        
				        refreshTable();
                        JOptionPane.showMessageDialog(null, "Record Saved....");
					//	System.out.print(roomno + weekoftheday);						
					}	
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(null,"Schedule has already been registered!!\n",
								"Schedule Error!",
								JOptionPane.ERROR_MESSAGE);
						//e1.printStackTrace();
					}
				}
			}
		});
		btnAdd.setBounds(44, 379, 89, 33);
		contentPane.add(btnAdd);
		
	/**	JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEdit.setBounds(47, 428, 89, 23);
		contentPane.add(btnEdit);
		**/
		JButton btnDelete = new JButton("DELETE");
		Image imgDel = new ImageIcon(this.getClass().getResource("/img/Trashy.png")).getImage();
		btnDelete.setIcon(new ImageIcon(imgDel));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ans = JOptionPane.showConfirmDialog(null, "This will also delete the Attendance "
						+ "record of a Section(students) related to this.\n"
						+ "Do you want to delete this record?",
						"Confirm Deletion", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(ans ==0)
				{
					InAttendanceSelectSchedule dateNow = new InAttendanceSelectSchedule();
					String dayToday = dateNow.getDateTimeToday();
					int row  = table.getSelectedRow();
					String code = null,studno = null ,attendate = null ,status = null,schedcode = null;
					String SCHEDCODE_ = (table.getModel().getValueAt(row, 0).toString());
					try {
					    String query = "insert into ZBINATTENDANCE (SCHEDCODE,ATTENDATE,STUDNO,STATUS,CODE)"
					    		+ "select SCHEDCODE,ATTENDATE,STUDNO,STATUS,CODE from ATTENDANCE"
					    		+ " where SCHEDCODE = '"+SCHEDCODE_+"' ";
					    PreparedStatement pst = connection.prepareStatement(query);
					    
						pst.executeUpdate();
						pst.close();
						
					    query = "update ZBINATTENDANCE set DATEDELETED = ? where SCHEDCODE = '"+SCHEDCODE_+"' ";
					    pst = connection.prepareStatement(query);
					    pst.setString(1,dayToday);
					    
						pst.executeUpdate();
						pst.close();
						
						query = "delete from ATTENDANCE where SCHEDCODE = '"+SCHEDCODE_+"' ";
						pst = connection.prepareStatement(query);
						pst.execute();
						pst.close();
						
					    query = "insert into ZBINSCHEDULE (SCHEDULECODE,ROOMNO,DAY,START,END,GRACEPERIOD,PYS)"
					    		+ " select SCHEDULECODE,ROOMNO,DAY,START,END,GRACEPERIOD,PYS from SCHEDULE"
					    		+ " where SCHEDULE.SCHEDULECODE = '"+SCHEDCODE_+"' ";
					    pst = connection.prepareStatement(query);
					    
						pst.executeUpdate();
						pst.close();
						
					    query = "update ZBINSCHEDULE set DATEDELETED = ? where SCHEDULECODE = '"+SCHEDCODE_+"' ";
					    pst = connection.prepareStatement(query);
					    pst.setString(1,dayToday);
					    
						pst.executeUpdate();
						pst.close();
						
						query = "delete from SCHEDULE where SCHEDULECODE = '"+SCHEDCODE_+"' ";
						pst = connection.prepareStatement(query);
						pst.execute();
						pst.close();
						
						
	
						refreshTable();	
					    JOptionPane.showMessageDialog(null, "Schedule successfully deleted");
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
		btnDelete.setBounds(143, 379, 112, 33);
		contentPane.add(btnDelete);
		
		txtRmNo = new JTextField();
		txtRmNo.setText("E418");
		txtRmNo.setBounds(206, 182, 67, 20);
		contentPane.add(txtRmNo);
		txtRmNo.setColumns(10);
		
		DOW = new JComboBox();
		DOW.setFont(new Font("Tahoma", Font.PLAIN, 14));
		DOW.setModel(new DefaultComboBoxModel(new String[] {"M", "T", "W", "TH", "F", "S", "Sun"}));
		DOW.setBounds(207, 212, 53, 22);
		contentPane.add(DOW);
		
		txtGraceP = new JTextField();
		txtGraceP.setText("15");
		txtGraceP.setColumns(10);
		txtGraceP.setBounds(206, 244, 67, 20);
		contentPane.add(txtGraceP);
		
		BoxST = new JComboBox();
	    BoxST.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				int select = BoxST.getSelectedIndex();
				BoxET.setSelectedIndex(select+4);
			}
		});
		BoxST.setFont(new Font("Tahoma", Font.PLAIN, 14));
		BoxST.setModel(new DefaultComboBoxModel(new String[] {"1:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM "}));
		BoxST.setBounds(206, 275, 92, 22);
		contentPane.add(BoxST);
		
		BoxET = new JComboBox();
		BoxET.setModel(new DefaultComboBoxModel(new String[] {"2:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM"}));
		BoxET.setSelectedIndex(15);
		BoxET.setFont(new Font("Tahoma", Font.PLAIN, 14));
		BoxET.setBounds(206, 310, 92, 22);
		contentPane.add(BoxET);
		
		JLabel lblHrFormat = new JLabel("(12 hr format):");
		lblHrFormat.setBounds(117, 278, 88, 14);
		contentPane.add(lblHrFormat);
		
		JLabel lblHrFormat_1 = new JLabel("(12 hr format):");
		lblHrFormat_1.setBounds(117, 316, 100, 14);
		contentPane.add(lblHrFormat_1);
		
		JLabel GPlabellabel = new JLabel("(minutes)");
		GPlabellabel.setBounds(283, 247, 75, 14);
		contentPane.add(GPlabellabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(412, 102, 821, 310);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try 
				{
					int row = table.getSelectedRow();
					String CODE= (table.getModel().getValueAt(row, 0).toString());  
					String query = "select * from SCHEDULE where SCHEDULECODE ='"+CODE+"' ";
				    PreparedStatement pst = connection.prepareStatement(query);
				    ResultSet rs = pst.executeQuery();
				    
				    while(rs.next()) {
				        txtRmNo.setText(rs.getString("ROOMNO"));
				        DOW.setSelectedItem((Object)rs.getString("DAY"));
				        txtGraceP.setText(String.valueOf(rs.getInt("GRACEPERIOD")));
				        BoxST.setSelectedItem((Object)rs.getString("START"));
				        BoxET.setSelectedItem((Object)rs.getString("END"));
				        CBchoice.setSelectedItem((Object)rs.getString("PYS"));
				    }
				    rs.close();
					pst.close();
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblCourse = new JLabel("Section");
		lblCourse.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCourse.setBounds(44, 123, 92, 14);
		contentPane.add(lblCourse);
		
		JLabel lblListOfSchedule = new JLabel("List of Schedule (Registered)");
		lblListOfSchedule.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblListOfSchedule.setBounds(412, 77, 305, 27);
		contentPane.add(lblListOfSchedule);
		
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try 
				{
					String query = "select SCHEDULECODE,DAY,ROOMNO,GRACEPERIOD,START,END from SCHEDULE where DAY = ?";
				    PreparedStatement pst = connection.prepareStatement(query);
				    pst.setString(1, txtSearch.getText());
				    ResultSet rs = pst.executeQuery();
				    table.setModel(DbUtils.resultSetToTableModel(rs));
				    
				    rs.close();
					pst.close();
					
					if(txtSearch.getText().isEmpty())
					{
					query = "select SCHEDULECODE,DAY,ROOMNO,GRACEPERIOD,START,END from SCHEDULE";
				    pst = connection.prepareStatement(query);
				    rs = pst.executeQuery();
				    table.setModel(DbUtils.resultSetToTableModel(rs));
				    
				    rs.close();
					pst.close();
					}
				}
				catch(Exception e3)
				{
					e3.printStackTrace();
				}
				
			}
		});
		txtSearch.setBounds(1068, 32, 165, 27);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblSearch = new JLabel("Search Day");
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSearch.setBounds(966, 36, 92, 23);
		contentPane.add(lblSearch);
		
		JButton btnCancel = new JButton("MAIN");
		Image imgCnl = new ImageIcon(this.getClass().getResource("/img/home.png")).getImage();
		btnCancel.setIcon(new ImageIcon(imgCnl));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					dispose();
					Application appFrame = new Application();
					try {
						connection.close();
					} catch (SQLException err) {
						// TODO Auto-generated catch block
						err.printStackTrace();
					}
			}
		});
		btnCancel.setBounds(32, 24, 124, 35);
		contentPane.add(btnCancel);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.BLACK);
		separator.setBounds(371, 23, 53, 410);
		contentPane.add(separator);
		
		JLabel label = new JLabel("Forms");
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		label.setBounds(32, 79, 221, 23);
		contentPane.add(label);
		
		refreshTable();
		fillComboBox();
		
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
