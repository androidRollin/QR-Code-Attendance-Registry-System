import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.SwingConstants;

public class CourseForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtCode;
	private JTextField txtName;
	private static CourseForm frame;
	private JTable table;
	private JComboBox<?> cbUnits;
	private JComboBox<?> cbProgram;
	private JComboBox<?> cbYear;
	private JComboBox<?> cbSection;
	private JComboBox<Object> CBchoice;
	private JTextField txtSearch;
	
	//Database
	Connection connection = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame= new CourseForm();
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

	
	public void refreshTable()
	{
		try {
			String query = "select PROGRAM,CODE,TITLE,UNITS from SUBJECT";
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
	
	public CourseForm() {
	    connection = db.dbConnector();	
	    Image iconCF = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		setIconImage(iconCF);
		setTitle("Course Registration");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 1183, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2,
			(dim.height/2-this.getSize().height/2)+(-30));
		
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(UIManager.getColor("TableHeader.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblcrscode = new JLabel("Course Code");
		lblcrscode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblcrscode.setBounds(53, 164, 91, 14);
		contentPane.add(lblcrscode);
		
		txtCode = new JTextField();
		txtCode.setBounds(154, 163, 100, 20);
		contentPane.add(txtCode);
		txtCode.setColumns(10);
		
		JLabel lblcrstitle = new JLabel("Course Title");
		lblcrstitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblcrstitle.setBounds(53, 200, 91, 14);
		contentPane.add(lblcrstitle);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(154, 194, 242, 20);
		contentPane.add(txtName);
		
		JLabel label = new JLabel("Units");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(53, 225, 57, 20);
		contentPane.add(label);
		

		
		JLabel lblSection = new JLabel("Program");
		lblSection.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSection.setBounds(53, 264, 57, 20);
		contentPane.add(lblSection);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblYear.setBounds(53, 298, 106, 20);
		contentPane.add(lblYear);
		
		cbUnits = new JComboBox();
		cbUnits.setModel(new DefaultComboBoxModel(new String[] {"3", "2"}));
		cbUnits.setBounds(154, 225, 32, 22);
		contentPane.add(cbUnits);
		
		cbProgram = new JComboBox();
		cbProgram.setModel(new DefaultComboBoxModel(new String[] {"BSIT", "BSCS"}));
		cbProgram.setBounds(154, 265, 74, 22);
		contentPane.add(cbProgram);
		
		cbYear = new JComboBox();
		cbYear.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbYear.setBounds(154, 299, 43, 22);
		contentPane.add(cbYear);
		
		JLabel lblSection_1 = new JLabel("Section");
		lblSection_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSection_1.setBounds(53, 329, 62, 20);
		contentPane.add(lblSection_1);
		
		cbSection = new JComboBox();
		cbSection.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "1N", "2N", "PETITION"}));
		cbSection.setBounds(154, 329, 100, 22);
		contentPane.add(cbSection);
		
		JButton btnSave = new JButton("ADD");
		Image imgadd = new ImageIcon(this.getClass().getResource("/img/add.png")).getImage();
		btnSave.setIcon(new ImageIcon(imgadd));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ans = JOptionPane.showConfirmDialog(null, "Do you want to save entered field values ? ","Confirm",JOptionPane.YES_NO_OPTION);
				if (ans == 0)
				{			
					try
					{
						//String query = "select COURSECODE,COURSENAME,PROGRAM,COURSEYEAR,COURSESECTION,PROF from SUBJECT";
					    String query = "insert into SUBJECT (PROGRAM,CODE,TITLE,UNITS) "
					    		+ "values (?,?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						String program,year,section,PYS;
						String code,title,units;
						
						program = String.valueOf(cbProgram.getSelectedItem());
	                    year = String.valueOf(cbYear.getSelectedItem());
	                    section = String.valueOf(cbSection.getSelectedItem());
						PYS = program + " " + year +"-"+ section;
						
						code = txtCode.getText();
						title = txtName.getText();
						units = String.valueOf(cbUnits.getSelectedItem());

						
						//System.out.print(code + " " + name + " " + description + " " + program + " " + year + " " +section + " " + prof + " " + codepys + " " +PYS);
						
						pst.setString(1,PYS);
						pst.setString(2,code);
						pst.setString(3,title);
						pst.setString(4,units);
						
						pst.execute();

					    
					    pst.close();

					    
						txtCode.setText("");
						txtName.setText("");
						CBchoice.addItem((Object)PYS);
						refreshTable();
                        JOptionPane.showMessageDialog(null, "Record Saved....");

					}
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(null,"Course already Registered!!",
								"Attendance Registry System", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}


				}

			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setBounds(53, 393, 91, 31);
		contentPane.add(btnSave);
		
		
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
					err.printStackTrace();
				}
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel.setBounds(24, 31, 100, 31);
		contentPane.add(btnCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(501, 93, 630, 325);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try 
				{
					int row = table.getSelectedRow();
					String sep = "";
					String[] progDiv,sectionDiv;
					String PROGRAM_ = (table.getModel().getValueAt(row, 0).toString());  
					CBchoice.setSelectedItem((Object)(PROGRAM_));
					String query = "select * from SUBJECT where PROGRAM ='"+PROGRAM_+"' ";
				    PreparedStatement pst = connection.prepareStatement(query);
				    ResultSet rs = pst.executeQuery();
				    
				    while(rs.next()) {
				        txtCode.setText(rs.getString("CODE"));
				    	txtName.setText(rs.getString("TITLE"));
				    	cbUnits.setSelectedItem((Object)String.valueOf(rs.getString("UNITS")));
				    	sep = rs.getString("PROGRAM");
				    	progDiv = sep.split(" ");
				    	sectionDiv = progDiv[1].split("-");
				    	cbProgram.setSelectedItem((Object)(progDiv));
				        cbYear.setSelectedItem((Object)(sectionDiv[0]));
				        cbSection.setSelectedItem((Object)(sectionDiv[1]));
				    	
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
		
		JLabel lblListOfSubjects = new JLabel("List of Courses (Registered)");
		lblListOfSubjects.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblListOfSubjects.setBounds(501, 63, 278, 24);
		contentPane.add(lblListOfSubjects);
		
//I CHOOSE TO REMOVE THIS BECAUSE OF COMPLICATIONS THAT THE PRIMARY KEY IS DERIVED FROM CONCATENATION
// AND I CANNOT EDIT THE WHOLE DATA WITH THE PRIMARY  KEY BEEING HIDDEN
/**JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{	
					String choice = String.valueOf(CBchoice.getSelectedItem());
					String code ="",name="", units = "",program="",year="",section="",PYS="";					
					code = txtCode.getText();
					name = txtName.getText();
					units = String.valueOf(cbUnits.getSelectedItem());
					program = String.valueOf(cbProgram.getSelectedItem());
                    year = String.valueOf(cbYear.getSelectedItem());
                    section = String.valueOf(cbSection.getSelectedItem());
					PYS = program + " " + year +"-"+ section;
					
					String query = "Update SUBJECT set CODE= '"+code+"', TITLE ='"+name+"', UNITS = '"+units+"' "
							+ ", PROGRAM = '"+PYS+"'  "
							+ "where PROGRAM ='"+choice+"' ";
					PreparedStatement pst = connection.prepareStatement(query);

					pst.execute();
					pst.close();
					
					query = "Update SCHEDULE set PYS = '"+PYS+"' where PYS ='"+choice+"' ";
					
					pst = connection.prepareStatement(query);

					pst.execute();
					pst.close();

					txtCode.setText("");
					txtName.setText("");
					
					if (choice != PYS)
						CBchoice.addItem(PYS);
						CBchoice.removeItem(CBchoice.getSelectedItem());
					
					refreshTable();
					
                    JOptionPane.showMessageDialog(null, "Record Updated....");

				}
				catch(Exception e2)
				{
					e2.printStackTrace();
				}
				

				
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnEdit.setBounds(296, 122, 100, 31);
		contentPane.add(btnEdit);
**/		
		JButton btnDelete = new JButton("DELETE");
		Image imgDel = new ImageIcon(this.getClass().getResource("/img/Trashy.png")).getImage();
		btnDelete.setIcon(new ImageIcon(imgDel));
		btnDelete.addActionListener(new ActionListener() {
			String scheduleC = "";
			ArrayList<String> schedC = new ArrayList<String>(); 
			public void actionPerformed(ActionEvent e) {
				int ans = JOptionPane.showConfirmDialog(null, "This will also delete the "
						+ "Schedule and Attendance record related to this."
						+ "\nDo you want to delete this record?",
						"Confirm Deletion", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(ans == 0 )
				{
					try {
						String choice = String.valueOf(CBchoice.getSelectedItem());
						String query = "select SCHEDULECODE from SCHEDULE where PYS = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, choice);
						ResultSet rs  = pst.executeQuery();
						while(rs.next())
						{
							scheduleC = rs.getString("SCHEDULECODE");
							schedC.add(scheduleC);
						}
						pst.close();
						rs.close();
						for (String i : schedC)
						{
							try {
						    query = "insert into ZBINATTENDANCE (SCHEDCODE,ATTENDATE,STUDNO,STATUS,CODE)"
						    		+ "select SCHEDCODE,ATTENDATE,STUDNO,STATUS,CODE from ATTENDANCE"
						    		+ " where SCHEDCODE = '"+i+"' ";
						    pst = connection.prepareStatement(query);
						    
							pst.executeUpdate();
							pst.close();
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
							}
						}
						try 
						{
							InAttendanceSelectSchedule dateNow = new InAttendanceSelectSchedule();
							String dayToday = dateNow.getDateTimeToday();
							for (String i : schedC)
							{
							query = "update ZBINATTENDANCE set DATEDELETED = ? where SCHEDCODE = '"+i+"' ";
							pst = connection.prepareStatement(query);
							pst.setString(1,dayToday);
					    
							pst.executeUpdate();
							pst.close();
							}
						}
						catch(Exception ex)
							{
							ex.printStackTrace();
							}
						for (String i : schedC)
						{
							try{
								query = "delete from ATTENDANCE where SCHEDCODE = '"+i+"' ";
								pst = connection.prepareStatement(query);
								pst.execute();
								pst.close();
							}
							catch(Exception y)
							{
								y.printStackTrace();
							}
						}
						for (String i : schedC)
						{
							try {
							    query = "insert into ZBINSCHEDULE (SCHEDULECODE,ROOMNO,DAY,START,END,GRACEPERIOD,PYS)"
							    		+ "select SCHEDULECODE,ROOMNO,DAY,START,END,GRACEPERIOD,PYS from SCHEDULE"
							    		+ " where SCHEDULECODE= '"+i+"' ";
							    pst = connection.prepareStatement(query);
							    
								pst.executeUpdate();
								pst.close();
								}
								catch(Exception ex)
								{
									ex.printStackTrace();
								}
							    InAttendanceSelectSchedule dateNow = new InAttendanceSelectSchedule();
						        String dayToday = dateNow.getDateTimeToday();
								query = "update ZBINSCHEDULE set DATEDELETED = ? where SCHEDULECODE = '"+i+"' ";
								pst = connection.prepareStatement(query);
								pst.setString(1,dayToday);
						    	
								pst.executeUpdate();
								pst.close();
						
						}
						for (String i : schedC)
						{
							query = "delete from SCHEDULE where SCHEDULECODE = '"+i+"' ";
							pst = connection.prepareStatement(query);
							pst.execute();
							pst.close();
						}
						schedC.clear();	
						
						try {
						    query = "insert into ZBINSUBJECT (CODE,TITLE,UNITS,PROGRAM)"
						    		+ "select CODE,TITLE,UNITS,PROGRAM from SUBJECT"
						    		+ " where PROGRAM = '"+choice+"' ";
						    pst = connection.prepareStatement(query);
						    
							pst.executeUpdate();
							pst.close();
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
						try {
							query = "delete from SUBJECT where PROGRAM = '"+choice+"' ";
						    pst = connection.prepareStatement(query);
							pst.executeUpdate();
							pst.close();
							CBchoice.removeItem((Object)choice);
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
						
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				   
					refreshTable();
					JOptionPane.showMessageDialog(null,"Successfully deleted");
				}
			}
			
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDelete.setBounds(164, 393, 106, 31);
		contentPane.add(btnDelete);
		
		CBchoice = new JComboBox<Object>();
		CBchoice.setVisible(false);
		CBchoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					String query = "select * from SUBJECT where PROGRAM = ? ";
				    PreparedStatement pst = connection.prepareStatement(query);
				    pst.setString(1, String.valueOf(CBchoice.getSelectedItem()) );
				    ResultSet rs = pst.executeQuery();
				    
				    while(rs.next()) {
				        txtCode.setText(rs.getString("CODE"));
				    	txtName.setText(rs.getString("TITLE"));
				    	//System.out.println(rs.getString("CODE"));
				    	String program = rs.getString("PROGRAM");
				    	String crsyrsection[] = program.split(" ");
				    	String crs = crsyrsection[0];
				    	String yrsection = crsyrsection[1];
				    	String YS[] = yrsection.split("-");
				    	String year =YS[0];
				    	String section = YS[1];
				    	int units = rs.getInt("UNITS");
				    	cbUnits.setSelectedItem((Object)String.valueOf(units));
				    	cbProgram.setSelectedItem((Object)(crs));
				    	cbYear.setSelectedItem((Object)(year));
				    	cbSection.setSelectedItem((Object)(section));
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
		CBchoice.setBounds(46, 73, 325, 31);
		contentPane.add(CBchoice);
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try 
				{
					String query = "select PROGRAM,CODE,TITLE,UNITS from SUBJECT where CODE = ?";
				    PreparedStatement pst = connection.prepareStatement(query);
				    pst.setString(1, txtSearch.getText());
				    ResultSet rs = pst.executeQuery();
				    table.setModel(DbUtils.resultSetToTableModel(rs));
				    
				    rs.close();
					pst.close();
					
					if(txtSearch.getText().isEmpty())
					{
					query = "select PROGRAM,CODE,TITLE,UNITS from SUBJECT";
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
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSearch.setBounds(987, 34, 144, 20);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblSearchCourseCode = new JLabel("Search Course Code");
		lblSearchCourseCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchCourseCode.setBounds(846, 37, 144, 14);
		contentPane.add(lblSearchCourseCode);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(446, 30, 177, 408);
		contentPane.add(separator);
		
		JLabel label_1 = new JLabel("Forms");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_1.setBounds(24, 111, 221, 23);
		contentPane.add(label_1);
		
	/**	JLabel sectReg = new JLabel("Section Registered");
		sectReg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sectReg.setBounds(53, 93, 133, 31);
		contentPane.add(sectReg);
		**/
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
