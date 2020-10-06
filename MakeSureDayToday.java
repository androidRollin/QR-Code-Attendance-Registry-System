import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class MakeSureDayToday {
	Connection connection = null;
	
	public MakeSureDayToday()
	{
		connection = db.dbConnector();	
	}
	
	public int getIfSameDay(JComboBox cbSched) {
		String araw="";
		Date date = new Date();
		DateFormat format2 = new SimpleDateFormat("EEE");
		String finalDay = format2.format(date);
		switch(finalDay) {
			case "Mon":
				//System.out.print("M");
				araw = "M";
				break;
			case "Tue":
				//System.out.print("T");
				araw = "T";
				break;
			case "Wed":
				//System.out.print("W");
				araw = "W";
				break;
			case "Thu":
				//System.out.print("TH");
				araw = "TH";
				break;
			case "Fri":
				//System.out.print("F");
				araw = "F";
				break;
			case "Sat":
				//System.out.print("S");
				araw = "S";
				break;
			case "Sun":
				//System.out.print("Sun");
				araw = "Sun";
				break;
			default:
				//System.out.print("None of the week");
		}
				
		InAttendanceSelectSchedule obj = new InAttendanceSelectSchedule();
		String selected = String.valueOf(cbSched.getSelectedItem());
		try {
			String day = obj.getDay(selected);
			if(day.equals(araw))
			{
				return 0;
			}
			else
			{
				String wrongDay = "";
				switch(day) {
					case "M":
						wrongDay = "MONDAY";
						break;
					case "T":
						wrongDay = "TUESDAY";
						break;
					case "W":
						wrongDay = "WEDNESDAY";
						break;
					case "TH":
						wrongDay = "THURSDAY";
						break;
					case "F":
						wrongDay = "FRIDAY";
						break;
					case "S":
						wrongDay = "SATURDAY";
						break;
					case "Sun":
						wrongDay = "SUNDAY";
						break;
					default:
						System.out.print("None of the week");
				}
				int confirm = JOptionPane.showConfirmDialog(null, "It is not "+ wrongDay + " today, Are you sure to Continue?" , 
						"Attendance Alert", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				//System.out.print(confirm);
				return confirm;
			}
				
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.print("It reaches the code in here");
		return 1;

	}

}
