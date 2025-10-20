package myPackage;

import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static int algorithmSelection() {
        int algorithmSelection;
        System.out.print("--------CPU Scheduling Algorithm---------\n"
                + "[1] Round Robin - Preemptive (Standard)\n"
                + "[2] Round Robin - Non-Preemtive\n"
                + "\nSelect Algorithm to use: ");
        algorithmSelection = sc.nextInt();
        while (algorithmSelection <= 0 || algorithmSelection >= 3) {
            System.out.print("Invalid input. Select 1 or 2:");
            algorithmSelection = sc.nextInt();
        }

        return algorithmSelection;
    }

    public static int inputs(ArrayList<RoundRobin> rRobin) {
        System.out.print("How many processes do you want? ");
        int processes = sc.nextInt();

        for (int i = 0; i < processes; i++) {
            System.out.println("\nProcess no. " + (i + 1));
            int processID = i + 1;
            System.out.print("Arrival Time: ");
            int arrivalTime = sc.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = sc.nextInt();

            rRobin.add(new RoundRobin(processID, arrivalTime, burstTime));
        }

        System.out.print("\nValue of Time Quantum: ");
        int quantum = sc.nextInt();
        while (quantum <= 0) {
            System.out.println("Invalid Input. Value of Time Quantum must not 0 and below. ");
            System.out.print("Value of Time Quantum: ");
            quantum = sc.nextInt();
        }

        System.out.println("\nProcess\t\tArrival Time\t\tBurst Time");
        for (RoundRobin cpu : rRobin) {
            System.out.println(cpu.printInput());
        }
        System.out.println("______________________________________"
                + "________________\n");
        return quantum;
    }

    public static void printOutput(ArrayList<RoundRobin> rRobin) {

        System.out.println("\nProcess\t\tArrival Time\t\tBurst Time\t\tCompletion Time\t\tTurnaround Time\t\tWaiting Time");
        for (RoundRobin cpu : rRobin) {
            System.out.println(cpu.printOutput());
        }
        System.out.print("_________________________________________________________________"
                + "_______________________________________________________________\n");

    }

    public static void performanceMetrics(double cpuUtilization, double aTT, double aWT) {
        System.out.println("\nPerformance Metrics");
        System.out.println("CPU Utilization Percentage: " + cpuUtilization + "%");
        System.out.println("Average Turnaround Time (ATT): " + aTT);
        System.out.println("Average Waiting Time (AWT): " + aWT);
    }

    public static void main(String[] args) {
        ArrayList<RoundRobin> rRobin = new ArrayList<>();
        int algorithmSelection = algorithmSelection();
        int quantum = inputs(rRobin);

        rRobin.sort(Comparator.comparingInt(RoundRobin::getArrivalTime));

        int completed = 0;
        int currentTime = 0;
        int cpuProcessesSize = rRobin.size();

        int totalIdleTime = 0;
        int totalBurstTime = 0;
        int turnAroundTimeTotal = 0;
        int waitingTimeTotal = 0;
        int index = 0;
        ArrayList<String> proc = new ArrayList<>();
        Queue<RoundRobin> queue = new LinkedList<>();
        int lastArrival = Collections.max(rRobin, Comparator.comparingInt(RoundRobin::getArrivalTime)).getArrivalTime();
        for (RoundRobin cpu : rRobin) {
            cpu.setTotalBurstTime(cpu.getBurstTime());
            totalBurstTime += cpu.getBurstTime();
        }

        if (!rRobin.isEmpty() && rRobin.get(0).getArrivalTime() > 0) {
            currentTime = rRobin.get(0).getArrivalTime();
        }

        switch (algorithmSelection) {
            case 1: // Round Robin - Preemptive
                System.out.println("Round Robin - Preemptive (Standard)");
                System.out.println("Gantt Chart:");

                while (completed < rRobin.size()) {
                    // Add all arrived processes
                    while (index < rRobin.size() && rRobin.get(index).getArrivalTime() <= currentTime) {
                        queue.add(rRobin.get(index++));
                    }

                    if (queue.isEmpty()) {
                        if (index < rRobin.size()) {
                            int next = rRobin.get(index).getArrivalTime();
                            System.out.print("[ IDLE | " + currentTime + "-" + next + " ]");
                            totalIdleTime += next - currentTime;
                            currentTime = next;
                        }
                        continue;
                    }

                    RoundRobin current = queue.poll();
                    System.out.print("[ P" + current.getProcessID() + " | " + currentTime + "-");
                    proc.add("P" + current.getProcessID());

                    int exec = Math.min(quantum, current.getTotalBurstTime());

                    boolean allArrived = currentTime >= lastArrival;

                    if (allArrived) {
                        current.setTotalBurstTime(current.getTotalBurstTime() - exec);
                        currentTime += exec;
                    } else {
                        currentTime++;
                        current.setTotalBurstTime(current.getTotalBurstTime() - 1);
                    }

                    System.out.print(currentTime + " ]");

                    // Add new arrivals while this process runs
                    while (index < rRobin.size() && rRobin.get(index).getArrivalTime() <= currentTime) {
                        queue.add(rRobin.get(index++));
                    }

                    if (current.getTotalBurstTime() > 0) {
                        queue.add(current);
                    } else {
                        current.setCompletionTime(currentTime);
                        current.setFinishedProcess(true);
                        completed++;
                    }
                }

                break;
            case 2: // Round Robin - Non-Preemptive
                System.out.println("Round Robin - Non-Preemptive");
                System.out.println("Gantt Chart:");

                while (completed < rRobin.size()) {
                    // Add all arrived processes
                    while (index < rRobin.size() && rRobin.get(index).getArrivalTime() <= currentTime) {
                        queue.add(rRobin.get(index++));
                    }

                    if (queue.isEmpty()) {
                        if (index < rRobin.size()) {
                            int next = rRobin.get(index).getArrivalTime();
                            System.out.print("[ IDLE | " + currentTime + "-" + next + " ]");
                            totalIdleTime += next - currentTime;
                            currentTime = next;
                        }
                        continue;
                    }

                    RoundRobin current = queue.poll();
                    System.out.print("[ P" + current.getProcessID() + " | " + currentTime + "-");
                    proc.add("P" + current.getProcessID());

                    int exec = Math.min(quantum, current.getTotalBurstTime());
                    current.setTotalBurstTime(current.getTotalBurstTime() - exec);
                    currentTime += exec;

                    System.out.print(currentTime + " ]");

                    // Add new arrivals while this process runs
                    while (index < rRobin.size() && rRobin.get(index).getArrivalTime() <= currentTime) {
                        queue.add(rRobin.get(index++));
                    }

                    if (current.getTotalBurstTime() > 0) {
                        queue.add(current);
                    } else {
                        current.setCompletionTime(currentTime);
                        current.setFinishedProcess(true);
                        completed++;
                    }
                }
                break;
        }

        for (RoundRobin cpu : rRobin) {
            cpu.setTurnAroundTime(cpu.getCompletionTime() - cpu.getArrivalTime());
            cpu.setWaitingTime(cpu.getTurnAroundTime() - cpu.getBurstTime());
            turnAroundTimeTotal += cpu.getTurnAroundTime();
            waitingTimeTotal += cpu.getWaitingTime();
        }

        System.out.print("\nProcess: ");
        for (String cpu : proc) {
            System.out.print(cpu + "  ");
        }

        System.out.println();

        rRobin.sort(Comparator.comparingInt(RoundRobin::getProcessID));

        int ganttChartTotalValue = totalBurstTime + totalIdleTime;

        double cpuUtilization = ((double) totalBurstTime / (double) ganttChartTotalValue) * 100;

        double aTT = (double) turnAroundTimeTotal / (double) rRobin.size();

        double aWT = (double) waitingTimeTotal / (double) rRobin.size();

        printOutput(rRobin);

        performanceMetrics(cpuUtilization, aTT, aWT);
    }
}
