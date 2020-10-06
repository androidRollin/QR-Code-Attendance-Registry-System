import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;import java.awt.Dimension;

import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JSeparator;

public class Application {

	protected JFrame frmAttendanceRegistry;
	private JLabel lblCAttendance;
	private JLabel lblManual;
	private JLabel lblreports; 
	private JLabel lblrgscls;
	private JLabel lblabout;
	private JSeparator separator;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	
	public Application() {
		initialize();

		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAttendanceRegistry = new JFrame();
		frmAttendanceRegistry.setBackground(SystemColor.menu);
		frmAttendanceRegistry.setTitle("Attendance Registry System ");
		Image icon1 = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		frmAttendanceRegistry.setIconImage(icon1);
		frmAttendanceRegistry.getContentPane().setBackground(UIManager.getColor("EditorPane.disabledBackground"));
		frmAttendanceRegistry.setBounds(100, 100, 864, 370);
		frmAttendanceRegistry.getContentPane().setLayout(null);
		frmAttendanceRegistry.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmAttendanceRegistry.setLocation(dim.width/2-frmAttendanceRegistry.getSize().width/2,
				(dim.height/2-frmAttendanceRegistry.getSize().height/2)+(-70));
		frmAttendanceRegistry.setVisible(true);
		
		lblCAttendance = new JLabel("");
		lblCAttendance.setBackground(Color.GRAY);
		lblCAttendance.setToolTipText("Scan Your Student's QR");
		Image img = new ImageIcon(this.getClass().getResource("/img/QR.png")).getImage();
		lblCAttendance.setIcon(new ImageIcon(img));
		lblCAttendance.setBounds(33, 17, 89, 101);
		frmAttendanceRegistry.getContentPane().add(lblCAttendance);
		
		JButton btnCAttendance = new JButton("Attendance");
		btnCAttendance.setForeground(SystemColor.controlLtHighlight);
		btnCAttendance.setBackground(SystemColor.textInactiveText);
		btnCAttendance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InAttendanceSelectSchedule qrScanner = new InAttendanceSelectSchedule();
				qrScanner.setVisible(true);
			}
		});
		btnCAttendance.setToolTipText("");
		btnCAttendance.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCAttendance.setBounds(19, 129, 118, 23);
		frmAttendanceRegistry.getContentPane().add(btnCAttendance);
		
		lblManual = new JLabel("");
		lblManual.setToolTipText("Check Attendance for special cases");
		Image img1 = new ImageIcon(this.getClass().getResource("/img/question.png")).getImage();
		lblManual.setIcon(new ImageIcon(img1));
		lblManual.setBounds(213, 32, 64, 75);
		frmAttendanceRegistry.getContentPane().add(lblManual);
		
		JButton btnManual = new JButton("Manual Check");
		btnManual.setToolTipText("");
		btnManual.setForeground(SystemColor.controlLtHighlight);
		btnManual.setBackground(SystemColor.textInactiveText);
		btnManual.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAttendanceRegistry.dispose();
				AttendanceForm1 mC = new AttendanceForm1();
				mC.setVisible(true);
			}
		});
		btnManual.setBounds(175, 129, 135, 23);
		frmAttendanceRegistry.getContentPane().add(btnManual);
		
		lblreports = new JLabel("");
		lblreports.setToolTipText("Generates reports");
		Image img2 = new ImageIcon(this.getClass().getResource("/img/file.png")).getImage();
		lblreports.setIcon(new ImageIcon(img2));
		lblreports.setBounds(45, 197, 84, 91);
		frmAttendanceRegistry.getContentPane().add(lblreports);
		
		JButton btnreports = new JButton("Reports");
		btnreports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAttendanceRegistry.dispose();
				JasperReports rep = new JasperReports();
				rep.setVisible(true);
			}
		});
		btnreports.setToolTipText("");
		btnreports.setForeground(SystemColor.controlLtHighlight);
		btnreports.setBackground(SystemColor.textInactiveText);
		btnreports.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnreports.setBounds(33, 289, 89, 23);
		frmAttendanceRegistry.getContentPane().add(btnreports);
		
		lblrgscls = new JLabel("");
		lblrgscls.setToolTipText("Click the Button to Register a Subject for a Section");
		Image img3 = new ImageIcon(this.getClass().getResource("/img/book.png")).getImage();
		lblrgscls.setIcon(new ImageIcon(img3));
		lblrgscls.setBounds(392, 197, 83, 83);
		frmAttendanceRegistry.getContentPane().add(lblrgscls);
		
		JButton btnrgrcls = new JButton("Subject");
		btnrgrcls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAttendanceRegistry.dispose();
				CourseForm registercourse = new CourseForm();
				registercourse.setVisible(true);
			}
		});
		btnrgrcls.setToolTipText("");
		btnrgrcls.setForeground(SystemColor.controlLtHighlight);
		btnrgrcls.setBackground(SystemColor.textInactiveText);
		btnrgrcls.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnrgrcls.setBounds(358, 289, 135, 23);
		frmAttendanceRegistry.getContentPane().add(btnrgrcls);
		lblabout = new JLabel("");
		lblabout.setToolTipText("About Us");
		Image img5 = new ImageIcon(this.getClass().getResource("/img/About.png")).getImage();
		lblabout.setIcon(new ImageIcon(img5));
		lblabout.setBounds(735, 35, 83, 83);
		frmAttendanceRegistry.getContentPane().add(lblabout);
		
		JButton btnabout = new JButton("About");
		btnabout.setToolTipText("");
		btnabout.setForeground(SystemColor.controlLtHighlight);
		btnabout.setBackground(SystemColor.textInactiveText);
		btnabout.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnabout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnabout.setBounds(735, 129, 89, 23);
		frmAttendanceRegistry.getContentPane().add(btnabout);
		
		separator = new JSeparator();
		separator.setForeground(SystemColor.controlShadow);
		separator.setBounds(0, 173, 900, 2);
		frmAttendanceRegistry.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(Color.GRAY);
		separator_1.setBounds(157, 0, 11, 340);
		frmAttendanceRegistry.getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setForeground(Color.GRAY);
		separator_2.setBounds(333, 0, 11, 340);
		frmAttendanceRegistry.getContentPane().add(separator_2);
		
		JLabel lblEnroll = new JLabel("");
		Image img6 = new ImageIcon(this.getClass().getResource("/img/Enroll.png")).getImage();
		lblEnroll.setIcon(new ImageIcon(img6));
		lblEnroll.setToolTipText("Enroll Students in the database");
		lblEnroll.setBounds(381, 32, 83, 83);
		frmAttendanceRegistry.getContentPane().add(lblEnroll);
		
		JLabel lblGenrateQR = new JLabel("");
		Image img7 = new ImageIcon(this.getClass().getResource("/GenQR.png")).getImage();
		lblGenrateQR.setIcon(new ImageIcon(img7));
		lblGenrateQR.setToolTipText("Generates QR students based on Student No.");
		lblGenrateQR.setBounds(735, 197, 83, 83);
		frmAttendanceRegistry.getContentPane().add(lblGenrateQR);
		
		JButton btnEnrollStudents = new JButton("Enroll Students");
		btnEnrollStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAttendanceRegistry.dispose();
				StudentForm st = new StudentForm();
				st.setVisible(true);
				//Excel enrollStud = new Excel();
				//int ans = enrollStud.registerStud();
				//if (ans ==0)
				//JOptionPane.showMessageDialog(null, "Students records are succesfully saved");	
			}
		});
		btnEnrollStudents.setToolTipText("");
		btnEnrollStudents.setForeground(Color.WHITE);
		btnEnrollStudents.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEnrollStudents.setBackground(SystemColor.textInactiveText);
		btnEnrollStudents.setBounds(354, 129, 150, 23);
		frmAttendanceRegistry.getContentPane().add(btnEnrollStudents);
		
		JButton btnMakeQr = new JButton("Make QR");
		btnMakeQr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[] options = {"Database","External *.csv file"};
					int choice = JOptionPane.showOptionDialog(frmAttendanceRegistry.getContentPane(),
							"Make QR Code using?", "QR CODE Maker",
							0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
					if (choice==0)
						new FetchStudentData().setVisible(true);
					else if(choice ==1)
						new GenerateFromCSV();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Python py is not located");
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					JOptionPane.showMessageDialog(null, "Python py is interrupted");
					e1.printStackTrace();
				}
			}
		});
		btnMakeQr.setToolTipText("");
		btnMakeQr.setForeground(Color.WHITE);
		btnMakeQr.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnMakeQr.setBackground(SystemColor.textInactiveText);
		btnMakeQr.setBounds(723, 289, 111, 23);
		frmAttendanceRegistry.getContentPane().add(btnMakeQr);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setForeground(Color.GRAY);
		separator_3.setBounds(522, 0, 11, 340);
		frmAttendanceRegistry.getContentPane().add(separator_3);
		
		JLabel lblSubj = new JLabel("");
		lblSubj.setToolTipText("Click the button to register a schedule");
		Image img8 = new ImageIcon(this.getClass().getResource("/img/Register.png")).getImage();
		lblSubj.setIcon(new ImageIcon(img8));
		lblSubj.setBounds(202, 185, 97, 103);
		frmAttendanceRegistry.getContentPane().add(lblSubj);
		
		JButton btnSubj = new JButton("Schedule");
		btnSubj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAttendanceRegistry.dispose();
				ScheduleRegistration schedRegister = new ScheduleRegistration();
				schedRegister.setVisible(true);
			}
		});
		btnSubj.setToolTipText("");
		btnSubj.setForeground(Color.WHITE);
		btnSubj.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSubj.setBackground(SystemColor.textInactiveText);
		btnSubj.setBounds(175, 289, 135, 23);
		frmAttendanceRegistry.getContentPane().add(btnSubj);
		
		JLabel lblAnalytics = new JLabel("");
		Image img9 = new ImageIcon(this.getClass().getResource("/img/Analysis.png")).getImage();
		lblAnalytics.setIcon(new ImageIcon(img9));
		lblAnalytics.setToolTipText("Data Analytics in Python");
		lblAnalytics.setBounds(571, 32, 83, 83);
		frmAttendanceRegistry.getContentPane().add(lblAnalytics);
		
		JButton btnDataAnalytics = new JButton("Data Analytics");
		btnDataAnalytics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAttendanceRegistry.dispose();
				DataAnalytics da = new DataAnalytics();
				da.setVisible(true);
			}
		});
		btnDataAnalytics.setToolTipText("");

		btnDataAnalytics.setForeground(Color.WHITE);
		btnDataAnalytics.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDataAnalytics.setBackground(SystemColor.textInactiveText);
		btnDataAnalytics.setBounds(543, 129, 135, 23);
		frmAttendanceRegistry.getContentPane().add(btnDataAnalytics);
		
		JButton btnMobile = new JButton("Mobile Scan");
		btnMobile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MobileSelection ms = new MobileSelection();
				ms.setVisible(true);
			}
		});
		btnMobile.setToolTipText("");
		btnMobile.setBackground(SystemColor.textInactiveText);
		btnMobile.setForeground(SystemColor.controlLtHighlight);
		btnMobile.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnMobile.setBounds(554, 289, 120, 23);
		frmAttendanceRegistry.getContentPane().add(btnMobile);
		
		JLabel lblMobileScan = new JLabel("");
		Image imgMobile = new ImageIcon(this.getClass().getResource("/img/mobilescan.png")).getImage();
		lblMobileScan.setIcon(new ImageIcon(imgMobile));
		lblMobileScan.setBounds(571, 212, 83, 68);
		frmAttendanceRegistry.getContentPane().add(lblMobileScan);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setForeground(Color.GRAY);
		separator_4.setBounds(702, 0, 11, 340);
		frmAttendanceRegistry.getContentPane().add(separator_4);
		


		
		
		frmAttendanceRegistry.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int x = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit the application ?",
						"Confirm !",JOptionPane.YES_NO_OPTION);
				
				if(x == JOptionPane.YES_OPTION)
				{
					frmAttendanceRegistry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				else
				{
					frmAttendanceRegistry.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});

	

	}
}
