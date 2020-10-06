import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.BasicConfigurator;
import com.lowagie.text.pdf.codec.Base64.OutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import net.sf.jasperreports.view.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;

public class JasperReports extends JFrame {

	private JPanel contentPane;
	Connection connection = null;

	/**
	 * Launch the application.
	 */	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JasperReports frame = new JasperReports();
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
	public JasperReports() {
		setTitle("Reports");
		//database
	    //connection = db.dbConnector();
		Image icon1 = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		this.setIconImage(icon1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 354, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2,
				(dim.height/2-this.getSize().height/2)+(-70));
		
		JButton btnCancel = new JButton("MAIN");
		Image imgCnl = new ImageIcon(this.getClass().getResource("/img/home.png")).getImage();
		btnCancel.setIcon(new ImageIcon(imgCnl));
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					Application appFrame = new Application();
				}
		});
		btnCancel.setBounds(10, 11, 121, 31);
		contentPane.add(btnCancel);
		
		JButton btnCourses = new JButton("Student");
		btnCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
				    /***start--- connection = db.dbConnector();
					String reportPath  = "C:\\Users\\ROLINE JOHN AGUILAR\\eclipse-workspace\\QRCODE\\Attendance.jrxml";
					JasperReport jr = JasperCompileManager.compileReport(reportPath);
					JasperPrint jp = JasperFillManager.fillReport(jr,null,connection);
					JasperViewer.viewReport(jp);
					connection.close();
					***/
					//Image icon1 = new ImageIcon(this.getClass().getResource("/img/PUPLogo.png")).getImage();
					connection = db.dbConnector();
					String reportUrl = "/reports/Student.jasper"; //path of your report source.
					InputStream reportFile = null;
					reportFile = JasperReports.class.getResourceAsStream(reportUrl);

					Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

					JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
					JasperViewer Jviewer = new JasperViewer(print, false);
					Jviewer.setVisible(true);
					connection.close();
					
					//FileOutputStream os = new FileOutputStream(new File("C:\\Users\\ROLINE JOHN AGUILAR\\eclipse-workspace\\QRCODE\\src"));
					//JasperExportManager.exportReportToPdfStream(j, os);
				}
				catch(Exception er)
				{
					System.out.println(er);
				}

			}
		});
		btnCourses.setBounds(180, 148, 141, 23);
		contentPane.add(btnCourses);
		
		JButton btnAttendance = new JButton("Attendance");
		btnAttendance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
						connection = db.dbConnector();
						String reportUrl = "/reports/Attendance.jasper"; //path of your report source.
						InputStream reportFile = null;
						reportFile = JasperReports.class.getResourceAsStream(reportUrl);

						Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

						JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
						JasperViewer Jviewer = new JasperViewer(print, false);
						Jviewer.setVisible(true);
						connection.close();
					}
					catch(Exception er)
					{
						System.out.println(er);
					}
			}
		});
		btnAttendance.setBounds(180, 114, 141, 23);
		contentPane.add(btnAttendance);
		
		JButton btnCourses_1 = new JButton("Courses");
		btnCourses_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
				connection = db.dbConnector();
				String reportUrl = "/reports/Courses.jasper"; //path of your report source.
				InputStream reportFile = null;
				reportFile = JasperReports.class.getResourceAsStream(reportUrl);

				Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

				JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
				JasperViewer Jviewer = new JasperViewer(print, false);
				Jviewer.setVisible(true);
				connection.close();
			}
			catch(Exception er)
			{
				System.out.println(er);
			}
			}
		});
		btnCourses_1.setBounds(180, 183, 141, 23);
		contentPane.add(btnCourses_1);
		
		JButton btnDropout = new JButton("Drop-Out");
		btnDropout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					connection = db.dbConnector();
					String reportUrl = "/reports/DropOut.jasper"; //path of your report source.
					InputStream reportFile = null;
					reportFile = JasperReports.class.getResourceAsStream(reportUrl);

					Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

					JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
					JasperViewer Jviewer = new JasperViewer(print, false);
					Jviewer.setVisible(true);
					connection.close();
				}
				catch(Exception er)
				{
					System.out.println(er);
				}
			}
		});
		btnDropout.setBounds(31, 210, 107, 23);
		contentPane.add(btnDropout);
		
		JButton btnSchedule = new JButton("Schedule");
		btnSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connection = db.dbConnector();
					String reportUrl = "/reports/ListofSched.jasper"; //path of your report source.
					InputStream reportFile = null;
					reportFile = JasperReports.class.getResourceAsStream(reportUrl);

					Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

					JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
					JasperViewer Jviewer = new JasperViewer(print, false);
					Jviewer.setVisible(true);
					connection.close();
				}
				catch(Exception er)
				{
					System.out.println(er);
				}
			}
		});
		btnSchedule.setBounds(180, 217, 141, 23);
		contentPane.add(btnSchedule);
		
		JButton btnAbsentee = new JButton("Absentee");
		btnAbsentee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					connection = db.dbConnector();
					String reportUrl = "/reports/SummaryAbsentWhenCheckedinAttendance.jasper"; //path of your report source.
					InputStream reportFile = null;
					reportFile = JasperReports.class.getResourceAsStream(reportUrl);

					Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

					JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
					JasperViewer Jviewer = new JasperViewer(print, false);
					Jviewer.setVisible(true);
					connection.close();
				}
				catch(Exception er)
				{
					System.out.println(er);
				}
			}
		});
		btnAbsentee.setBounds(31, 176, 107, 23);
		contentPane.add(btnAbsentee);
		
		JButton btnSummaryAttendance = new JButton("Present");
		btnSummaryAttendance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try
			{
				connection = db.dbConnector();
				String reportUrl = "/reports/SummaryAttendance.jasper"; //path of your report source.
				InputStream reportFile = null;
				reportFile = JasperReports.class.getResourceAsStream(reportUrl);

				Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

				JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
				JasperViewer Jviewer = new JasperViewer(print, false);
				Jviewer.setVisible(true);
				connection.close();
			}
			catch(Exception er)
			{
				System.out.println(er);
			}
			}
		});
		btnSummaryAttendance.setBounds(31, 108, 107, 23);
		contentPane.add(btnSummaryAttendance);
		
		JButton btnCutting = new JButton("Cutting");
		btnCutting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					connection = db.dbConnector();
					String reportUrl = "/reports/SummaryCutting.jasper"; //path of your report source.
					InputStream reportFile = null;
					reportFile = JasperReports.class.getResourceAsStream(reportUrl);

					Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

					JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
					JasperViewer Jviewer = new JasperViewer(print, false);
					Jviewer.setVisible(true);
					connection.close();
				}
				catch(Exception er)
				{
					System.out.println(er);
				}
			}
		});
		btnCutting.setBounds(31, 278, 107, 23);
		contentPane.add(btnCutting);
		
		JButton btnLate = new JButton("Late");
		btnLate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					connection = db.dbConnector();
					String reportUrl = "/reports/SummaryLate.jasper"; //path of your report source.
					InputStream reportFile = null;
					reportFile = JasperReports.class.getResourceAsStream(reportUrl);

					Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

					JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
					JasperViewer Jviewer = new JasperViewer(print, false);
					Jviewer.setVisible(true);
					connection.close();
				}
				catch(Exception er)
				{
					System.out.println(er);
				}
			}
		});
		btnLate.setBounds(31, 142, 107, 23);
		contentPane.add(btnLate);
		
		JButton btnExcused = new JButton("Excused");
		btnExcused.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					connection = db.dbConnector();
					String reportUrl = "/reports/SummayExcused.jasper"; //path of your report source.
					InputStream reportFile = null;
					reportFile = JasperReports.class.getResourceAsStream(reportUrl);

					Map data = new HashMap(); //In case your report need predefined parameters you'll need to fill this Map

					JasperPrint print = JasperFillManager.fillReport(reportFile, null, connection);
					JasperViewer Jviewer = new JasperViewer(print, false);
					Jviewer.setVisible(true);
					connection.close();
				}
				catch(Exception er)
				{
					System.out.println(er);
				}
			}
		});
		btnExcused.setBounds(31, 244, 107, 23);
		contentPane.add(btnExcused);
		
		JLabel lblSummary = new JLabel("Summaries");
		lblSummary.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSummary.setBounds(31, 73, 89, 24);
		contentPane.add(lblSummary);
		
		JLabel lblReports = new JLabel("Reports");
		lblReports.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblReports.setBounds(180, 73, 89, 24);
		contentPane.add(lblReports);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(158, 53, 19, 303);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBounds(-23, 53, 419, 2);
		contentPane.add(separator_1);
		
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
