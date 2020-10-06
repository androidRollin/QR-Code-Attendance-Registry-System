import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import net.proteanit.sql.DbUtils;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.BevelBorder;

public class AttendanceForm1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ButtonGroup bg = new ButtonGroup();
	private JTable table;
	private JTextField txtDate;
	private JTextField txtStudNo;
	private JComboBox<String> CBSchedule;
	private static AttendanceForm1 frame;
	//Database
	Connection connection = null;
	private JTextField txtSearch;
	
	public void fillComboBox() {
		try {
			String query = "select SCHEDULECODE from SCHEDULE";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) 
			{
				CBSchedule.addItem(rs.getString("SCHEDULECODE"));
			}
			pst.close();
			rs.close();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		
	}
	
	public void refreshTable()
	{
		try {
			String query = "select ST.STUDENTNAME as NAME, ST.STUDENTNO as STUDNO, AT.STATUS, ST.PYS as SECTION, AT.CODE "
					+ "from ATTENDANCE as AT right outer join STUDENT as ST on AT.STUDNO = ST.STUDENTNO "
					+ "ORDER BY ST.PYS ASC,ST.STUDENTNAME ASC, AT.ATTENDATE DESC";
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new AttendanceForm1();
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
	public AttendanceForm1() {
		//database
	    connection = db.dbConnector();	

		Image icon1 = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		setIconImage(icon1);
		setTitle("Manual Checking");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 962, 554);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2,
			(dim.height/2-this.getSize().height/2)+(-30));
		
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		CBSchedule = new JComboBox<String>();
		CBSchedule.setBounds(46, 162, 346, 31);
		contentPane.add(CBSchedule);
		
		JRadioButton rdbtnPresent = new JRadioButton("Present");
		rdbtnPresent.setForeground(new Color(0, 204, 0));
		rdbtnPresent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnPresent.setBounds(41, 331, 89, 23);
		contentPane.add(rdbtnPresent);
		rdbtnPresent.setSelected(true);
		
		JRadioButton rdbtnAbsent = new JRadioButton("Absent");
		rdbtnAbsent.setForeground(new Color(255, 0, 0));
		rdbtnAbsent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnAbsent.setBounds(41, 357, 89, 23);
		contentPane.add(rdbtnAbsent);
		
		JRadioButton rdbtnExcused = new JRadioButton("Excused");
		rdbtnExcused.setForeground(new Color(0, 0, 204));
		rdbtnExcused.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnExcused.setBounds(41, 383, 89, 23);
		contentPane.add(rdbtnExcused);
		
		JRadioButton rdbtnCutting = new JRadioButton("Cutting");
		rdbtnCutting.setForeground(new Color(255, 153, 51));
		rdbtnCutting.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnCutting.setBounds(157, 357, 89, 23);
		contentPane.add(rdbtnCutting);
		
		JRadioButton rdbtnLate = new JRadioButton("Late");
		rdbtnLate.setForeground(new Color(204, 204, 0));
		rdbtnLate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnLate.setBounds(157, 331, 89, 23);
		contentPane.add(rdbtnLate);
		
		bg.add(rdbtnPresent);
		bg.add(rdbtnAbsent) ;
		bg.add(rdbtnExcused);
		bg.add(rdbtnExcused);
		bg.add(rdbtnCutting);
		bg.add(rdbtnLate);
		
		JLabel lblMarkStudentAs = new JLabel("Remarks:");
		lblMarkStudentAs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMarkStudentAs.setBounds(41, 292, 121, 44);
		contentPane.add(lblMarkStudentAs);
		

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(443, 72, 475, 422);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Object keys = e.getSource();
				System.out.print(keys);
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int row  = table.getSelectedRow();
					String CODE_ = (table.getModel().getValueAt(row, 4).toString());
					String query = "select * from ATTENDANCE where CODE = '"+CODE_+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					
					while(rs.next())
					{
						CBSchedule.setSelectedItem(rs.getString("SCHEDCODE"));
						txtDate.setText(rs.getString("ATTENDATE"));
						txtStudNo.setText(rs.getString("STUDNO"));
						String status = (rs.getString("STATUS"));
						
						if(status.equals("P"))
						{
							rdbtnPresent.setSelected(true);
						}
						else if(status.equals("L"))
						{
							rdbtnLate.setSelected(true);
						}
						else if(status.equals("A"))
						{
							rdbtnAbsent.setSelected(true);
						}
						else if(status.equals("E"))
						{
							rdbtnExcused.setSelected(true);
						}
						else if(status.equals("C"))
						{
							rdbtnCutting.setSelected(true);
						}
						
					}
					
					rs.close();
					pst.close();
					
				}
				catch(Exception e1)
				{   
					int row  = table.getSelectedRow();
					String studNumber = (table.getModel().getValueAt(row, 1).toString());
					txtStudNo.setText(studNumber);
				//	e1.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblSchedule = new JLabel("Schedule");
		lblSchedule.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSchedule.setBounds(41, 137, 70, 14);
		contentPane.add(lblSchedule);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDate.setBounds(41, 204, 82, 14);
		contentPane.add(lblDate);
		
		txtDate = new JTextField();
		txtDate.setBounds(47, 223, 127, 20);
		contentPane.add(txtDate);
		txtDate.setColumns(10);
		
		InAttendanceSelectSchedule defaultDate = new InAttendanceSelectSchedule();
		txtDate.setText(defaultDate.getDateToday());
		
		JLabel lblStudentNumber = new JLabel("Student Number");
		lblStudentNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStudentNumber.setBounds(41, 254, 103, 14);
		contentPane.add(lblStudentNumber);
		
		txtStudNo = new JTextField();
		txtStudNo.setColumns(10);
		txtStudNo.setBounds(47, 279, 127, 20);
		contentPane.add(txtStudNo);
		txtStudNo.setText("2018-XXXX-MN-0");
		
		
		JButton btnAdd = new JButton("ADD");
		Image img = new ImageIcon(this.getClass().getResource("/img/add.png")).getImage();
		btnAdd.setIcon(new ImageIcon(img));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ans = JOptionPane.showConfirmDialog(null, "Do you want to save entered field values ? ","Confirm",JOptionPane.YES_NO_OPTION);
				if (ans == 0)
				{	
					try
					{
						String start="", pys="", pys1="",day="";
						String query = "select PYS,START,DAY from SCHEDULE where SCHEDULECODE = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, String.valueOf(CBSchedule.getSelectedItem()));
						ResultSet rs = pst.executeQuery();
						while(rs.next())
						{
							start = rs.getString("START");
							pys = rs.getString("PYS");
							day = rs.getString("DAY");
						}
						
						rs.close();
						pst.close();
						
						query = "select PYS from STUDENT where STUDENTNO = ?";
						pst = connection.prepareStatement(query);
						pst.setString(1,txtStudNo.getText());
						rs = pst.executeQuery();
						
						while(rs.next())
						{
							pys1 = rs.getString("PYS");
						}
						
						rs.close();
						pst.close();
						
						if(pys.equalsIgnoreCase(pys1))
						{
							
							try {
								query = "insert into ATTENDANCE (SCHEDCODE,ATTENDATE,STUDNO,STATUS,CODE)"
									+ " values (?,?,?,?,?)";
								pst = connection.prepareStatement(query);
								String studSchedCode,attendDate,studNum = "",status = null;
							
								attendDate = txtDate.getText();
								studNum = txtStudNo.getText();
								studSchedCode = String.valueOf(CBSchedule.getSelectedItem());
							
								if(rdbtnPresent.isSelected())
								{
									status = "P";
								}
								else if(rdbtnLate.isSelected())
								{
									status = "L";
								}
								else if(rdbtnAbsent.isSelected())
								{
									status = "A";
								}
								else if(rdbtnExcused.isSelected())
								{
									status = "E";
								}
								else if(rdbtnCutting.isSelected())
								{
									status = "C";
								}
								
								pst.setString(1,studSchedCode);
								pst.setString(2,attendDate);
								pst.setString(3,studNum);
								pst.setString(4,status);
								pst.setString(5,attendDate +" "+day+" "+start+" "+studNum);
								
								pst.execute();
								pst.close();
								refreshTable();
			                    JOptionPane.showMessageDialog(null, "Record Added....");
							}
							catch(Exception x)
							{
								JOptionPane.showMessageDialog(null,"Student Already Checked","Warning!",JOptionPane.WARNING_MESSAGE);
							}
	
					}
						else {
							JOptionPane.showMessageDialog(null, "Student not on the course schedule selected",
									"ERROR",JOptionPane.ERROR_MESSAGE);
							
						}
						
						
					}
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(null, "Student Number is not on the list");
						e1.printStackTrace();
					}
				}
			}
		});
		btnAdd.setBounds(47, 446, 89, 31);
		contentPane.add(btnAdd);
		
		//JButton btnEdit = new JButton("EDIT");
	//	btnEdit.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent e) {
	//		}
	//	});
	//	btnEdit.setBounds(140, 425, 89, 23);
	//	contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("DELETE");
		Image img2 = new ImageIcon(this.getClass().getResource("/img/Trashy.png")).getImage();
		btnDelete.setIcon(new ImageIcon(img2));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ans = JOptionPane.showConfirmDialog(null, "Do you want to delete this record?",
						"Confirm Deletion", JOptionPane.YES_NO_OPTION);
				if(ans ==0)
				{
					InAttendanceSelectSchedule dateNow = new InAttendanceSelectSchedule();
					String dayToday = dateNow.getDateTimeToday();
					int row  = table.getSelectedRow();
					String code = null,studno = null ,attendate = null ,status = null,schedcode = null;
					String CODE_ = (table.getModel().getValueAt(row, 4).toString());
		
					try {
						String query = "select * from ATTENDANCE where CODE = '"+CODE_+"' ";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						
						while(rs.next()) 
						{
							code = rs.getString("CODE");
							studno = rs.getString("STUDNO");
							attendate = rs.getString("ATTENDATE");
							status = rs.getString("STATUS");
							schedcode = rs.getString("SCHEDCODE");
						}
						pst.close();
						rs.close();
					}
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(null, "This Error");
						e1.printStackTrace();
					}
					try {
						String query = "insert into ZBINATTENDANCE (SCHEDCODE,ATTENDATE,STUDNO,STATUS,CODE,DATEDELETED)"
								+ " values (?,?,?,?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1,schedcode);
							pst.setString(2,attendate);
							pst.setString(3,studno);
							pst.setString(4,status);
							pst.setString(5,code);
							pst.setString(6,dayToday);
							
							pst.executeUpdate();
							pst.close();
					}
					catch(Exception y)
					{
						JOptionPane.showMessageDialog(null, "This Error");
						y.printStackTrace();
					}
					try{
						
					String query = "delete from ATTENDANCE where CODE = '"+CODE_+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
							pst.execute();
							pst.close();
					}
					catch(Exception y)
					{
						JOptionPane.showMessageDialog(null, "This Error");
						y.printStackTrace();
					}
						refreshTable();	
					    JOptionPane.showMessageDialog(null, "Attendance successfully deleted");
	

			}
			}
		});
		btnDelete.setBounds(142, 446, 127, 31);
		contentPane.add(btnDelete);
		
		JLabel lblShowOnlyBy = new JLabel("Search by Date:");
		lblShowOnlyBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblShowOnlyBy.setBounds(687, 19, 127, 23);
		contentPane.add(lblShowOnlyBy);
		
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try 
				{
					String query = "select ST.STUDENTNAME, ST.STUDENTNO, AT.STATUS, ST.PYS, AT.CODE "
							+ "from ATTENDANCE as AT right outer join STUDENT as ST on AT.STUDNO = ST.STUDENTNO"
							+ " where AT.ATTENDATE = ?";
				    PreparedStatement pst = connection.prepareStatement(query);
				    pst.setString(1, txtSearch.getText());
				    ResultSet rs = pst.executeQuery();
				    table.setModel(DbUtils.resultSetToTableModel(rs));
				    
				    rs.close();
					pst.close();
					
					if(txtSearch.getText().isEmpty())
					{
						refreshTable();
					}
				}
				catch(Exception e3)
				{
					JOptionPane.showMessageDialog(null, "Student has no record yet",
							"ERROR",JOptionPane.ERROR_MESSAGE);
					e3.printStackTrace();
				}
				
			}
		});
		txtSearch.setColumns(10);
		txtSearch.setBounds(791, 19, 127, 20);
		contentPane.add(txtSearch);
		
		JButton btnCancel = new JButton("MAIN");
		Image imgCnl = new ImageIcon(this.getClass().getResource("/img/home.png")).getImage();
		btnCancel.setIcon(new ImageIcon(imgCnl));
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					//Application.main(null);
					Application appFrame = new Application();
					try {
						connection.close();
					} catch (SQLException err) {
						// TODO Auto-generated catch block
						err.printStackTrace();
					}
				}
		});
		btnCancel.setBounds(23, 17, 121, 31);
		contentPane.add(btnCancel);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.BLACK);
		separator.setBounds(413, 11, 18, 495);
		contentPane.add(separator);
		
		JLabel lblAttendance = new JLabel("Attendance");
		lblAttendance.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAttendance.setBounds(443, 47, 121, 14);
		contentPane.add(lblAttendance);
		
		JLabel label = new JLabel("Forms");
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		label.setBounds(23, 82, 221, 23);
		contentPane.add(label);
		
		fillComboBox();
		refreshTable();
		
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
