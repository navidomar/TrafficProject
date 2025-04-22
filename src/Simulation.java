import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private double currentTime;
    private Queue<Job> completedJobs;
    private ArrivalProcess arrivalProcess;
    private List<SingleServerQueue> checkoutLines;

    public Simulation(int numLines, double lambda) {
        this.currentTime = 0;
        this.completedJobs = new Queue<>();
        this.arrivalProcess = new ArrivalProcess(lambda);
        this.checkoutLines = new ArrayList<>();

        for (int i=0; i<numLines; i++) {
            checkoutLines.add(new SingleServerQueue());
        }

    }

    /*public void run(double sim_time) {
        while (currentTime < sim_time) {
            double nextArrival = arrivalProcess.getNextArrivalTime();
            if (nextArrival <= currentTime) {
                arrivalProcess.generateNextArrival();
                Job job = new Job(currentTime);
                SingleServerQueue shortestQueue = getShortestQueue();
                shortestQueue.add(job, currentTime);
            }

            for (SingleServerQueue line : checkoutLines) {
                if (line.getEndServiceTime() <= currentTime) {
                    line.complete(currentTime);
                    completedJobs.enqueue(new Job(currentTime));
                }
            }
            currentTime = nextArrival;
        }
    }*/

    public Queue<Job> getCompletedJobs() {
        return completedJobs;
    }

    private SingleServerQueue getShortestQueue() {
        SingleServerQueue shortestQueue = checkoutLines.get(0);
        for (SingleServerQueue line : checkoutLines) {
            if (line.getEndServiceTime() < shortestQueue.getEndServiceTime()) {
                shortestQueue = line;
            }
        }
        return shortestQueue;
    }
}
