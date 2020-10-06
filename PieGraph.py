from matplotlib import pyplot as plt, style
from ProjectAnalytics import getAccuracy,getprediction

def showPieGraph(accuracy):
    plt.style.use("Solarize_Light2")
    x = accuracy*100
    slices = [x, 100 - x]
    label = ["Accuracy", " "]
    explode = [0.1, 0]

    plt.pie(slices, labels=label, explode=explode, shadow=True,
            startangle=90, autopct=" %1.1f%%",
            wedgeprops={"edgecolor": "black"})
    result = getprediction()
    plt.title("Result = {} \n\n Model Accuracy Percentage".format(result), fontweight = "bold")
    plt.tight_layout()
    plt.show()

showPieGraph(getAccuracy())
