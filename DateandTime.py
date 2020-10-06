from datetime import date, time
import datetime

day_of_week = ["Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun"]
#initialize the dates
now = datetime.datetime.now()
today = date.today()

#Date in the month,day year
LocalDate = today.strftime("%b-%d-%Y")
#Localtime Now
timenow = now.strftime("%I:%M %p").lstrip('0')
#Day of the Week
days = str(day_of_week[datetime.datetime.today().weekday()])
Date_phil = LocalDate + " " + timenow + " " + days
print(Date_phil)
