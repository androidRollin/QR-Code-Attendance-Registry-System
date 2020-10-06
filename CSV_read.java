import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CSV_read {
	
	public static String checkAttendance(String studentArrival, String classStartTime,int gracePeriod, int absentCondiTime)
			throws ParseException
	{
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Conversion into a Format
	    Date classStart = sdf.parse(classStartTime); // Conversion of String time to Date
	    Date studentAT = sdf.parse(studentArrival); // Conversion of String time to Date
	    long elapsed = studentAT.getTime() - classStart.getTime(); //Subtraction to know 
	    int gapMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(elapsed); // conversion of milliseconds to minutes
	  //  System.out.println("Gap minutes" + gapMinutes);
	    
        if(gapMinutes <= gracePeriod )
        {
        	//System.out.print("Teacher: You are On-Time Present");
        	// Write Present on the database
        	return "P";
        	
        }
        else if(gapMinutes > gracePeriod && gapMinutes < absentCondiTime)
        {
        	//System.out.print("Teacher: You are Late");
        	// Write Late on the database
        	return "L";
        }
        else {
			//System.out.print("Teacher: You are Absent");
			// Write Absent on the database
			return "A";
        }
	}
	
	public CSV_read()
	{
		
	}
	public static void main(String[] args) throws ParseException {
		// .csv comma seperated values
		String filename = "cleanData.csv";
		File file = new File(filename); //TODO: read about File
		try {
			Scanner inputStream = new Scanner(file);
			inputStream.next();
			while(inputStream.hasNext()) {
				String data = inputStream.next();
				String[] values = data.split(",");
			 //checkAttendance("arrivaltime",start,graceP,absentCondition);
				String status = checkAttendance(values[1],"7:30",15,60);
				System.out.println("Studno "+ values[0]+ " " +"AT:"+ status);
			}
			inputStream.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

	}

}
