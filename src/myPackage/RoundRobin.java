package myPackage;

class RoundRobin {

    private int processID;
    private int arrivalTime;
    private int burstTime;
    private int totalBurstTime;
    private int waitingTime;
    private int turnAroundTime;
    private int completionTime;
    private boolean finishedProcess;

    public RoundRobin(int processID, int arrivalTime, int burstTime) {
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.finishedProcess = false;
        setTotalBurstTime(burstTime);
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getTotalBurstTime() {
        return totalBurstTime;
    }

    public void setTotalBurstTime(int totalBurstTime) {
        this.totalBurstTime = totalBurstTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isFinishedProcess() {
        return finishedProcess;
    }

    public void setFinishedProcess(boolean finishedProcess) {
        this.finishedProcess = finishedProcess;
    }

    public String printInput() {
        return "______________________________________"
                + "________________\n"
                + "P" + getProcessID()
                + "\t\t" + getArrivalTime()
                + "\t\t\t" + getBurstTime();
    }


    public String printOutput() {
        return "_________________________________________________________________"
                + "_______________________________________________________________\n"
                + "P" + getProcessID() + "\t\t" + getArrivalTime()
                + "\t\t\t" + getBurstTime() + "\t\t\t" + getCompletionTime()
                + "\t\t\t" + getTurnAroundTime() + "\t\t\t" + getWaitingTime();
    }


}
