import java.io.File;  
import java.io.FileInputStream;  
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.sql.*;
import javax.swing.*;
import java.awt.FileDialog;
import java.io.File;
import javax.swing.*;

public class Excel  
{  
	static Connection connection = null;
	static String cellStudNo= null;
	static String cellLname = null;
	static String cellFname = null;
	static String cellMname = null;
	static String cellSection= null;
	static String cellSubjCode = null;
	static String Name = null;
	
	public int registerStud() {
			String fileLoc = null;
			FileDialog fd = new FileDialog(new JFrame());
			fd.setVisible(true);
			File[] f = fd.getFiles();
			if(f.length > 0)
			{
				fileLoc = fd.getFiles()[0].getAbsolutePath();
			}
			else
			{
				System.out.print("No File Selected");
				return 2;
			}
			try {
				File file = new File(fileLoc);   //creating a new file instance  
				FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
				//creating Workbook instance that refers to .xlsx file  
				XSSFWorkbook wb = new XSSFWorkbook(fis);   
				XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  	
				Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
				Row row;
				Cell cell0,cell1,cell2,cell3,cell4,cell5;
				for(int rowIndex =1; rowIndex <= sheet.getLastRowNum(); rowIndex++)
				{
					row = sheet.getRow(rowIndex);
					if(row != null)
					{
					//STUDNO
						cell0 = row.getCell(0);
					if(cell0!=null)
					{
						cellStudNo = cell0.getStringCellValue();
				//		System.out.println(cellStudNo);
					}
					else
					{
						cellStudNo = "NULL";
					}
					
					//LASTNAME
					cell1 = row.getCell(1);
					if(cell1!=null)
					{
						cellLname = cell1.getStringCellValue();
				//		System.out.println(cellLname);
					}
					else
					{
						cellLname = "";
					}
					
					//FIRSTNAME
					cell2 = row.getCell(2);
					if(cell1!=null)
					{
						cellFname = cell2.getStringCellValue();
			//		System.out.println(cellFname);
					}
					else
					{
						cellFname = "";
					}
					
					//MIDDLENAME
					cell3 = row.getCell(3);
					if(cell3!=null)
					{
						cellMname = cell3.getStringCellValue();
			//			System.out.println(cellMname);
					}
					else
					{
						cellMname = "";
			//			System.out.println(cellMname);
					}
					
					//SECTION
					cell4 = row.getCell(4);
					if(cell4 != null)
					{
						cellSection = cell4.getStringCellValue();
				//		System.out.println(cellSection);
					}
					else
					{
						cellSection = "NULL";
					}
					cell5 = row.getCell(5);
					if(cell5 != null)
					{
						cellSubjCode = cell5.getStringCellValue();
					}
					else
					{
						cellSubjCode = "NULL";
					}
					
					
					
				}
				try
				{
					connection = db.dbConnector();
					Name = cellLname+","+ cellFname +","+ cellMname ;
					String query = "insert into STUDENT (STUDENTNO,STUDENTNAME,PYS,SUBJCODE) values (?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1,cellStudNo);
					pst.setString(2,Name);
					pst.setString(3,cellSection);
					pst.setString(4,cellSubjCode);
				
					pst.execute();
					pst.close();	
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Students are already enrolled","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
					return 1;
				}
				
			}//for loop
				
				return 0;
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Error: File Type is not supported","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
				return 1;
			}

	}
	
	}  