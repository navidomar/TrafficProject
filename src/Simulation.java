import java.util.ArrayList;
import java.util.List;

/*
Specifications:
-Simulate the expected time experience for a person going through bus stop and train station
-Account for the

*/


public class Simulation {
    private double currentTime;
    private Queue<Job> trainStationQueue;
    private Queue<Job> busStopQueue;
    private ArrivalProcess arrivalProcess;
    private List<SingleServerQueue> reachedMetro;

    public Simulation(int numLines, double lambda) {
        this.currentTime = 0;
        this.trainStationQueue = new Queue<>();
        this.arrivalProcess = new ArrivalProcess(lambda);
        this.busStopQueue = new Queue<>();
        this.reachedMetro = new ArrayList<>();

        for (int i=0; i<numLines; i++) {
            reachedMetro.add(new SingleServerQueue());
        }

    }

    public void run(double sim_time) {
        double busStopInterval = 300.0;  //5 minutes
        double trainStationInterval = 3000.0; //1 hour
        int busPassCount = 0;  //15 max
        int trainPassCount = 0; //1300 max
        while (currentTime < sim_time) {
            double nextArrival = arrivalProcess.getNextArrivalTime();
            if (nextArrival <= currentTime) {
                arrivalProcess.generateNextArrival();
                Job job = new Job(currentTime);
                busStopQueue.enqueue(job);
                busPassCount++;
            }

            if (busStopInterval <= currentTime) {
                if (busPassCount <= 15) {
                    for (int i = 0; i < busPassCount; i++) {
                        trainStationQueue.enqueue(busStopQueue.dequeue());
                    }
                    busStopInterval += 300.0;
                    busPassCount = 0;
                }
                else {
                    for (int i = 0; i < 15; i++) {
                        trainPassCount += busPassCount;
                        trainStationQueue.enqueue(busStopQueue.dequeue());
                    }
                    busStopInterval += 300.0;
                    busPassCount = 0;
                }
            }

            if (trainStationInterval <= currentTime) {
                if (trainPassCount <= 1300) {
                    for (int i = 0; i < trainPassCount; i++) {
                        trainStationQueue.enqueue(busStopQueue.dequeue());
                    }
                    busStopInterval += 300.0;
                    busPassCount = 0;
                }
                }
            }



            for (SingleServerQueue line : reachedMetro) {
                if (line.getEndServiceTime() <= currentTime) {
                    line.complete(currentTime);
                    trainStationQueue.enqueue(new Job(currentTime));
                }
            }

            if (nextArrival <= busStopInterval) currentTime = nextArrival;
            else currentTime = busStopInterval;
        }
    }

    public Queue<Job> getTrainStationQueue() {
        return trainStationQueue;
    }

    private SingleServerQueue getShortestQueue() {
        SingleServerQueue shortestQueue = reachedMetro.get(0);
        for (SingleServerQueue line : reachedMetro) {
            if (line.getEndServiceTime() < shortestQueue.getEndServiceTime()) {
                shortestQueue = line;
            }
        }
        return shortestQueue;
    }
}
