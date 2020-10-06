import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;

public class StudentForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtStud;
	private JTextField txtName;
	private JTextField txtSection;
	private JTable table;
	private static StudentForm frame;
	//Database
	Connection connection = null;
	private JTextField txtSubj;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new StudentForm();
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
			String query = "select STUDENTNO as STUDNO,STUDENTNAME as NAME,PYS as SECTION,SUBJCODE as  SUBJECT_CODE from STUDENT order by PYS asc, "
					+ "STUDENTNAME asc, "
					+ "SUBJCODE asc";
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
	 * Create the frame.
	 */
	public StudentForm() {
	    connection = db.dbConnector();	
	    System.out.println("Connection established");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 845, 477);
		setTitle("Enroll Students");
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2,
			(dim.height/2-this.getSize().height/2)+(-30));
		Image icon1 = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		setIconImage(icon1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStudentNumber = new JLabel("Student Number");
		lblStudentNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStudentNumber.setBounds(32, 100, 126, 14);
		contentPane.add(lblStudentNumber);
		
		txtStud = new JTextField();
		txtStud.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtStud.setBounds(32, 125, 147, 20);
		contentPane.add(txtStud);
		txtStud.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(32, 158, 126, 14);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtName.setColumns(10);
		txtName.setBounds(32, 183, 214, 20);
		contentPane.add(txtName);
		
		JLabel lblSection = new JLabel("Section");
		lblSection.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSection.setBounds(32, 214, 126, 14);
		contentPane.add(lblSection);
		
		txtSection = new JTextField();
		txtSection.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSection.setColumns(10);
		txtSection.setBounds(32, 239, 69, 20);
		contentPane.add(txtSection);
		
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
					    String query = "insert into STUDENT (STUDENTNO,STUDENTNAME,PYS,SUBJCODE) "
					    		+ "values (?,?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						
						pst.setString(1,txtStud.getText());
						pst.setString(2,txtName.getText());
						pst.setString(3,txtSection.getText());
					    pst.setString(4,txtSubj.getText());
						
						pst.execute();
	
					    
					    pst.close();
	
					    
					    txtStud.setText("");
					    txtName.setText("");
					    txtSection.setText("");
					    txtSubj.setText("");
						refreshTable();
	                    JOptionPane.showMessageDialog(null, "Record Saved....");
	
					}
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(null, "Student Number is already enrolled, please try again","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
					}
			   }
			}
		});
		btnAdd.setBounds(32, 382, 89, 36);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("DELETE");
		Image imgDel = new ImageIcon(this.getClass().getResource("/img/Trashy.png")).getImage();
		btnDelete.setIcon(new ImageIcon(imgDel));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ans = JOptionPane.showConfirmDialog(null, "This will also delete the attendance record of the student.\n"
						+ "Do you want to delete this record?",
						"Confirm Deletion", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(ans ==0)
				{
					try {
						String student = txtStud.getText();
						ArrayList<String> attendCode = new ArrayList<String>(); 
					    String query = "select STUDENTNO,STUDENTNAME,PYS,SUBJCODE from STUDENT"
					    		+ " where STUDENTNO = ? ";
					    PreparedStatement pst = connection.prepareStatement(query);
					    pst.setString(1,student);
					    ResultSet rs = pst.executeQuery();
					    rs.next();
					    rs.close();
					    pst.close();
					    
						query = "select CODE from ATTENDANCE where STUDNO = '"+student+"' ";
						pst = connection.prepareStatement(query);
						rs = pst.executeQuery();
						
						while(rs.next()) 
						{
							attendCode.add(rs.getString("CODE"));
						}
						pst.close();
						rs.close();
						
						for (String i : attendCode)
						{
							try {
						    query = "insert into ZBINATTENDANCE (SCHEDCODE,ATTENDATE,STUDNO,STATUS,CODE)"
						    		+ "select SCHEDCODE,ATTENDATE,STUDNO,STATUS,CODE from ATTENDANCE"
						    		+ " where CODE = '"+i+"' ";
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
							for (String i : attendCode)
							{
							query = "update ZBINATTENDANCE set DATEDELETED = ? where CODE = '"+i+"' ";
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
						
						for (String i : attendCode)
						{
							try{
								query = "delete from ATTENDANCE where CODE = '"+i+"' ";
								pst = connection.prepareStatement(query);
								pst.execute();
								pst.close();
							}
							catch(Exception y)
							{
								y.printStackTrace();
							}
						}
						
					    query = "insert into ZBINSTUDENT (STUDENTNO,STUDENTNAME,PYS,SUBJCODE)"
					    		+ " select STUDENTNO,STUDENTNAME,PYS,SUBJCODE from STUDENT"
					    		+ " where STUDENTNO = ? ";
					    pst = connection.prepareStatement(query);
					    pst.setString(1,student);
						pst.executeUpdate();
						pst.close();
						
	             	    query = "delete from STUDENT where STUDENTNO = ? ";
					    pst = connection.prepareStatement(query);
				        pst.setString(1, student);
						pst.execute();
						pst.close();
						JOptionPane.showMessageDialog(null,"Successfully deleted");
						refreshTable();
					}
					catch(Exception y)
					{
					    JOptionPane.showMessageDialog(null, "Students doesn't exist",
					    		"ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
						y.printStackTrace();
					}
					refreshTable();
				}
			}
		});
		btnDelete.setBounds(143, 382, 117, 36);
		contentPane.add(btnDelete);
		
		JButton btnExcel = new JButton("");
		Image imgExcel = new ImageIcon(this.getClass().getResource("/img/excel.png")).getImage();
		btnExcel.setIcon(new ImageIcon(imgExcel));
		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Excel enrollStud = new Excel();
				int ans = enrollStud.registerStud();
				if (ans ==0)
				{
				refreshTable();
				JOptionPane.showMessageDialog(null, "Students records are succesfully saved");
				}
			}
		});
		btnExcel.setBounds(754, 27, 47, 36);
		contentPane.add(btnExcel);
		
		JButton btnHome = new JButton("HOME");
		Image imgH = new ImageIcon(this.getClass().getResource("/img/home.png")).getImage();
		btnHome.setIcon(new ImageIcon(imgH));
		btnHome.addActionListener(new ActionListener() {
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
		btnHome.setBounds(20, 19, 113, 36);
		contentPane.add(btnHome);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(319, 86, 492, 332);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int row  = table.getSelectedRow();
					String STUDNO_ = (table.getModel().getValueAt(row, 0).toString());
					String query = "select STUDENTNO,STUDENTNAME,PYS,SUBJCODE "
							+ "from STUDENT where STUDENTNO = '"+STUDNO_+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					
					rs.next();
					
					txtStud.setText(rs.getString("STUDENTNO"));
					txtName.setText(rs.getString("STUDENTNAME"));
					txtSection.setText(rs.getString("PYS"));
					txtSubj.setText(rs.getString("SUBJCODE"));
					
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
		
		JLabel lblImportFile = new JLabel("Import .xlsx file");
		lblImportFile.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblImportFile.setBounds(742, 58, 77, 23);
		contentPane.add(lblImportFile);
		
		JLabel lblSubject = new JLabel("Subject Code");
		lblSubject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSubject.setBounds(32, 270, 126, 14);
		contentPane.add(lblSubject);
		
		txtSubj = new JTextField();
		txtSubj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSubj.setColumns(10);
		txtSubj.setBounds(32, 295, 89, 20);
		contentPane.add(txtSubj);
		
		JLabel tabelLabel = new JLabel("List of All Students Enrolled");
		tabelLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		tabelLabel.setBounds(319, 56, 221, 23);
		contentPane.add(tabelLabel);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.BLACK);
		separator.setBounds(286, 11, 22, 431);
		contentPane.add(separator);
		
		JLabel lblForms = new JLabel("Forms");
		lblForms.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblForms.setBounds(20, 66, 221, 23);
		contentPane.add(lblForms);
		
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
