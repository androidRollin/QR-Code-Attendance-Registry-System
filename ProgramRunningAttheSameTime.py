from threading import Thread

def one():
    import Table
def two():
    import PieGraph
def three():
    import ResultSettoPDF


Thread(target = one).start()
Thread(target = two).start()
Thread(target = three).start()
