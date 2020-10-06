import javax.swing.*;

import org.apache.log4j.BasicConfigurator;

import java.io.InputStream;
import java.sql.*;
	public class db {
		Connection con = null;
		public static Connection dbConnector()  {
			try {
				//Class.forName("jdbc:ucanaaccess.jdbc.Driver");
				//C://Users//ROLINE JOHN AGUILAR//eclipse-workspace//QRCODE
				//String url = "jdbc:ucanaccess://C://Users//ROLINE JOHN AGUILAR//eclipse-workspace//QRCODE//QRD.accdb";
				//String url = "jdbc:ucanaccess://C:\\Users\\ROLINE JOHN AGUILAR\\eclipse-workspace\\QRCODE\\QRD.accdb";
				
				//String url = "jdbc:ucanaccess://QRD.accdb";
				//Connection con = DriverManager.getConnection(url);
				
				//JOptionPane.showMessageDialog(null, "Connection to Database is Successful");
				//Statement st = con.createStatement();
				//ResultSet rs = st.executeQuery("select * from SUBJECT");
				//while(rs.next())
				//{
				//	System.out.print(rs.getString(1) +" " + rs.getString(2) +" "+ rs.getString(3) + " "+  rs.getString(4));	
				//	System.out.print(rs.getString(5) +" " + rs.getString(6) +" "+ rs.getString(7) +" "+ rs.getString(8)+"\n");
				//}
				//String url = "jdbc:ucanaccess:/database/QRDatabase.accdb";
				BasicConfigurator.configure();
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				String url1 = "jdbc:ucanaccess://C:/Users/ROLINE JOHN AGUILAR/eclipse-workspace/QRCODE/src/database/QRDatabase.accdb";
				Connection con = DriverManager.getConnection(url1);
				return con;
			}
			catch(Exception e)
			{
				System.out.println(e);
				JOptionPane.showMessageDialog(null, e);
				return null;

				//return null;
			}
			
			
	/**	
	 * 			Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from SUBJECT");
	 * while(rs.next())
			{
				System.out.print(rs.getString(1) +" " + rs.getString(2) +" "+ rs.getString(3) + " "+  rs.getString(4));	
				System.out.print(rs.getString(5) +" " + rs.getString(6) +" "+ rs.getString(7) +" "+ rs.getString(8)+"\n");
			}
	**/		
		}

}
