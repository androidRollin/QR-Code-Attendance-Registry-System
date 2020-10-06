import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RealTimeDetector {
	
	Connection connection = null;
	public RealTimeDetector() {
		
		connection = db.dbConnector();
		
		try
		{   String query = "select PYS from SCHEDULE where SCHEDULECODE = ?";
			InAttendanceSelectSchedule obj = new InAttendanceSelectSchedule();
			String schedCode = obj.getSelectedItemCB();
			System.out.print(schedCode);
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, schedCode);
			ResultSet rs = pst.executeQuery();
			rs.next();
			String pys = rs.getString("PYS");
			pst.close();
			rs.close();
			
			query = "select STUDENTNO from STUDENT where PYS = ?";
			pst = connection.prepareStatement(query);
			pst.setString(1, pys);
			rs = pst.executeQuery();
			FileWriter csvWriter = new FileWriter("CheckStudent.csv");
			csvWriter.append("StudentNo"+"\n");

			String number,csvline;
	
			while(rs.next()) 
			{
				number = rs.getString("STUDENTNO");
				//System.out.println(number);
				csvline = number+"\n";
				//System.out.print(number+","+name+","+schedCode+"\n");
				csvWriter.append(csvline);
			}
			csvWriter.flush();
			csvWriter.close();
			pst.close();
			rs.close();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	
	}

}
