import pandas as pd
from sklearn.naive_bayes import GaussianNB
from sklearn.preprocessing import LabelEncoder
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from Csvreading import getPath,getTest
#Load the CSV data file using pandas read_csv method
play_tennis = pd.read_csv(getPath())


#LabelEncoder() converts a categorical data into a number ranging from 0 to n-1
#where n is the number of classes in the variable.

number = LabelEncoder()

#3 classes of ATTENDANCE (1, 2, 3),
# 3 - 0 to 1 Absences for Semester
# 2 - 2 Absences for Semester
# 1 - 3 to 4 Absences for Semester
# these are represented as
# 0 - (3 to 4 Absences for Semester)  1- (2 Absences for Semester)  2- (0 to 1 Absences for Semester)
play_tennis['ATTENDANCE'] = number.fit_transform(play_tennis['ATTENDANCE'])

#3 classes of SHS Strand  (ABM,GAS,HUMSS,ICT,STEM), these are represented as
#0 - ABM 1 - GAS 2 - HUMSS 3 - ICT  4- STEM
play_tennis['STRAND'] = number.fit_transform(play_tennis['STRAND'])

#2 classes of Honour (0- DL, 1 - NONE, 2- PL) theses are represented as
#0 - DL, 1 - NONE, 2 - PL
play_tennis['HONOUR'] = number.fit_transform(play_tennis['HONOUR'])

#4 classes of Performance Rating (Commendable,Meets Expectation,Needs Improvement,Unsatisfactory), these are represented as
#0 - Commendable, 1- Meets Expectation, 2 Needs Improvement,3 Unsatisfactory)
play_tennis['PERFORMANCE RATING'] = number.fit_transform(play_tennis['PERFORMANCE RATING'])

#2 classes of Remarks (Fail, Pass), these are represented as
#0 - Fail 1- Pass
play_tennis['REMARKS'] = number.fit_transform(play_tennis['REMARKS'])

#Defining the features and the target variables
features = ["ATTENDANCE","STRAND","HONOUR","PERFORMANCE RATING"]
target = "REMARKS"

#Create a train, test split. We build the model using the train dataset
#and we will validate the model on the test dataset
features_train, features_test, target_train, target_test = train_test_split(play_tennis[features],
play_tennis[target], test_size = 0.70, random_state = 77)

#Displaying the split dataets
#print("\tTraining Features\n", features_train)
#print("\tTesting Features\n", features_test)
#print("\tTraining Target\n", target_train)
#print("\tTesting Target\n", target_test)
#Create a Gaussian Naive Bayes Model
model = GaussianNB()

#Fitting the training dataset to the model
model.fit(features_train, target_train)

#After fitting, we will make predictions using the testing dataset
pred = model.predict(features_test)

#measuring the accuracy of the model, using the actual data and the predicted results
accuracy = accuracy_score(target_test, pred)

#displaying the accuracy of the model
print("\nModel Accuracy = ", accuracy*100,"%")
#Now suppose we want to predict for the conditions:
#Outlook = Rain(Rain is represented as 1 in the Outlook class)
#Temprature = Mild(Mild is represented as 1 in the Outlook class)
#Humidity = High(High is r represented as 0 in the Humidity class
#Wind = Weak(Weak is represented as 1 in the Wind class)
#Should we pass(1) or fail (0)? According to our dataset
row = getTest()
#print(row)
answer = model.predict([row])
if answer == 1:
    ans = "Pass"
elif answer == 0:
    ans = "Fail"

def getAccuracy():
    return accuracy
def getfeatures_train():
    return features_train
def getfeatures_test():
    return features_test
def gettarget_train():
    return target_train
def gettarget_test():
    return target_test
def getprediction():
    return ans
