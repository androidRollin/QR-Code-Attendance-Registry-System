import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class DataAnalytics extends JFrame {

	private JPanel contentPane;
	private String fileLoc="";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataAnalytics frame = new DataAnalytics();
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
	public DataAnalytics() {
		setTitle("Data Analytics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		Image icon = new ImageIcon(this.getClass().getResource("/img/Reports.png")).getImage();
		this.setIconImage(icon);
		contentPane.setLayout(null);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2,
				(dim.height/2-this.getSize().height/2)+(-70));
		
		JComboBox AttendanceRate = new JComboBox();
		AttendanceRate.setModel(new DefaultComboBoxModel(new String[] {"(3  above Absences Per Semester)", "(2 Absences Per Semester)", "(0 to 1 Absences Per Semester) "}));
		AttendanceRate.setSelectedIndex(2);
		AttendanceRate.setBounds(47, 89, 205, 31);
		contentPane.add(AttendanceRate);
		
		JComboBox Strand = new JComboBox();
		Strand.setModel(new DefaultComboBoxModel(new String[] {"ABM  (Accounting Business Management)", "GAS  (General Academic Strand)", "HUMSS (Humanities and Social Sciences)", "ICT  (Information Communication Technology)", "STEM  (Science Technology Engineering Mathematics)"}));
		Strand.setBounds(47, 156, 283, 31);
		contentPane.add(Strand);
		
		JComboBox HonourList = new JComboBox();
		HonourList.setModel(new DefaultComboBoxModel(new String[] {"Deans Lister", "None", "President Lister"}));
		HonourList.setBounds(47, 223, 108, 31);
		contentPane.add(HonourList);
		
		JComboBox Performance = new JComboBox();
		Performance.setModel(new DefaultComboBoxModel(new String[] {"Commendable", "Meets Expectations", "Need Improvement", "Unsatisfactory"}));
		Performance.setBounds(47, 301, 129, 31);
		contentPane.add(Performance);
		
		JLabel lblAttendanceRate = new JLabel("Attendance Rate");
		lblAttendanceRate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAttendanceRate.setBounds(21, 64, 108, 14);
		contentPane.add(lblAttendanceRate);
		
		JLabel lblShsStrand = new JLabel("SHS Strand");
		lblShsStrand.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblShsStrand.setBounds(21, 131, 108, 14);
		contentPane.add(lblShsStrand);
		
		JLabel lblHonurs = new JLabel("Honors");
		lblHonurs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHonurs.setBounds(21, 198, 108, 14);
		contentPane.add(lblHonurs);
		
		JLabel lblPerformanceRating = new JLabel("Performance Rating");
		lblPerformanceRating.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPerformanceRating.setBounds(21, 265, 155, 25);
		contentPane.add(lblPerformanceRating);
		
		JLabel lblImportcsvDataset = new JLabel("");
		Image img5 = new ImageIcon(this.getClass().getResource("/img/csv.png")).getImage();
		lblImportcsvDataset.setIcon(new ImageIcon(img5));;
		lblImportcsvDataset.setBounds(255, 239, 54, 51);
		contentPane.add(lblImportcsvDataset);
		
		JButton btnOk = new JButton("Predict");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a=0, b=0, c=0, d=0;
				a = AttendanceRate.getSelectedIndex();
				a++;
				b = Strand.getSelectedIndex();
				c = HonourList.getSelectedIndex();
				d = Performance.getSelectedIndex();
			    String combination = String.valueOf(a) + "," + String.valueOf(b) +
			    		","+ String.valueOf(c) +
			    		"," + String.valueOf(d);
			  /**  if (fileLoc.isEmpty())
			    {
			    	JOptionPane.showMessageDialog(null,"Dataset wasn't imported, please try again","Dataset Error",JOptionPane.ERROR_MESSAGE);
			    }
			    else if(!(fileLoc.contains(".csv")))
			    {
			    	JOptionPane.showMessageDialog(null,"Dataset is not a csv file, please try again","Dataset Error",JOptionPane.ERROR_MESSAGE);
			    }
			    else
			    {**/
			    	try 
			    	{
			    		FileWriter csvWriter = new FileWriter("new.csv");
			    		csvWriter.append(fileLoc);
			    		csvWriter.append("\n");
			    		csvWriter.append(combination);
			    		csvWriter.flush();
			    		csvWriter.append("\n");
			    		RunGraphsTables abc = new RunGraphsTables();
			    	} catch (IOException e1) 
			    	{
			    		e1.printStackTrace();
			    	} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
			    System.out.println(fileLoc);
			    System.out.println(combination);
			    //}
			    

			}
		});
		btnOk.setBounds(87, 407, 89, 23);
		contentPane.add(btnOk);
		
		btnOk.setEnabled(false);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Application appFrame = new Application();
			}
		});
		btnCancel.setBounds(193, 407, 89, 23);
		contentPane.add(btnCancel);
		
		JButton btnImportcsvDataset = new JButton("Import *.csv dataset");
		btnImportcsvDataset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(new JFrame());
				fd.setVisible(true);
				File[] f = fd.getFiles();

			    if(f.length > 0)
				{
					fileLoc = fd.getFiles()[0].getAbsolutePath();
					File file = new File(fileLoc);
					System.out.print(file);
					Scanner inputStream;
					try {
						inputStream = new Scanner(file);
						String header = inputStream.next();// SKIPS THE COLUMN HEADER
						String headersec = inputStream.next();
						//System.out.println( "\n." + header + ".\n" + headersec);
						if (!(header.contains("﻿ATTENDANCE,STRAND,HONOUR,PERFORMANCE")) || 
								!(headersec.contains(headersec)))
						{
							btnOk.setEnabled(false);
							JOptionPane.showMessageDialog(null,"Dataset is not on specified format, please try again",
									"Dataset Error",JOptionPane.ERROR_MESSAGE);
						}
						
						else 
							btnOk.setEnabled(true);

					} 
					catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null,"Dataset is not a csv file, please try again",
								"Dataset Error",JOptionPane.ERROR_MESSAGE);}  
					catch(NoSuchElementException e2)
					  {
						  JOptionPane.showMessageDialog(null,"Dataset is not a csv file, please try again",
								  "Dataset Error",JOptionPane.ERROR_MESSAGE);
					  }




				}
				else
				{
					System.out.print("No File Selected");
				}
			    System.out.print(fileLoc);
			}
		});
		btnImportcsvDataset.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnImportcsvDataset.setBounds(214, 305, 131, 23);
		contentPane.add(btnImportcsvDataset);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(186, 223, 8, 136);
		contentPane.add(separator);
		
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
