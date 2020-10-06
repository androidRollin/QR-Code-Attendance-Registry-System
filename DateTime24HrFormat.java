import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class DateTime24HrFormat {

	public static void main(String[] args) throws ParseException {
		Scanner inpt = new Scanner(System.in);
		int absentCondiTime = 0,gracePeriod = 0; 
		double totalHrs = 0.0;
	    String startTime = "10:00";
	    String endTime = "10:10";
	    
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.MINUTE,15);// 15 Minute Grace Period
		gracePeriod = 15;
		
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Conversion into a Format
	    Date classsched = sdf.parse(startTime); // Conversion of String time to Date
	    Date studentAT = sdf.parse(endTime); // Conversion of String time to Date
	    long elapsed = studentAT.getTime() - classsched.getTime(); //Subtraction to know 
	    int gapMinutes = (int) TimeUnit.MILLISECONDS.toMinutes(elapsed); // conversion of milliseconds to minutes
	    System.out.println("Gap minutes" + gapMinutes);
	    
		System.out.println("\nHow many hours class?"); //hours in relation to the absent time
		totalHrs = inpt.nextDouble(); //input value
		
		if (totalHrs >= 3) // if class is equal to 3 hrs
		{
			absentCondiTime = 60; //absent time if you are 60 minutes late
		}
		else if (totalHrs >= 2)
		{
			absentCondiTime = 40;
		}
		else if (totalHrs >= 1.50)
		{
			absentCondiTime = 30;
		}
		else if (totalHrs >= 1)
		{
			absentCondiTime = 20;
		}
		else
		{
			absentCondiTime = 20;
		}
		/***************Another Condition,Roline Gwaps***************/
        if(gapMinutes <= gracePeriod )
        {
        	System.out.print("Teacher: You are On-Time Present");
        	// Write Present on the database
        	
        }
        else if(gapMinutes > gracePeriod && gapMinutes < absentCondiTime)
        {
        	System.out.print("Teacher: You are Late");
        	// Write Late on the database
        }
        else {
			System.out.print("Teacher: You are Absent");
			// Write Absent on the database
	
        }
		System.out.println("\nYou are absent in the minute of " + Integer.toString(absentCondiTime));
	}

}
