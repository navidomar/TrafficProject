import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private double currentTime;
    private Queue<Job> trainStationQueue;
    private Queue<Job> busStopQueue;
    private Queue<Job> metroQueue;
    private ArrivalProcess arrivalProcess;
    private List<Job> reachedDestination;

    public Simulation() {
        this.currentTime = 0;
        this.trainStationQueue = new Queue<>();
        this.arrivalProcess = new ArrivalProcess();
        this.busStopQueue = new Queue<>();
        this.metroQueue = new Queue<>();
        this.reachedDestination = new ArrayList<>();
    }

    public List<Job> run(double sim_time) {
        double busStopInterval = 300.0;  //5 minutes
        double trainStationInterval = 3000.0; //1 hour
        int busPassCount = 0;  //15 max
        int trainPassCount = 0; //1300 max
        int metroPassCount = 0; //Max to be determined
        int startTimesTracker = 0;
        ArrayList<Double> jobStartTimes = arrivalProcess.generateStartTimes();
        Double nextStart = jobStartTimes.get(startTimesTracker);

        while (currentTime < sim_time) {
            if (startTimesTracker < jobStartTimes.size()) {
                if (nextStart <= currentTime) {
                    startTimesTracker++;
                    double eBikeRideTime = arrivalProcess.generateEbikeRideTime();
                    double busRideTime = arrivalProcess.generateBusRideTime();
                    double trainRideTime = arrivalProcess.generateTrainRideTime();
                    Job job = new Job(currentTime, eBikeRideTime, busRideTime, trainRideTime);
                    busStopQueue.enqueue(job);
                    busPassCount++;
                }
            }

            //Bus picks up to 15 passengers waiting in queue
            if (busStopInterval <= currentTime) {
                if (busPassCount <= 15) {
                    for (int i = 0; i < busPassCount; i++) {
                        Job job = busStopQueue.dequeue();
                        job.setBusStopWaitTime(currentTime);
                        trainStationQueue.enqueue(job);
                        trainPassCount++;
                    }
                    busStopInterval += 300.0;
                    busPassCount = 0;
                } else {
                    for (int i = 0; i < 15; i++) {
                        Job job = busStopQueue.dequeue();
                        job.setBusStopWaitTime(currentTime);
                        trainStationQueue.enqueue(job);
                        trainPassCount++;
                    }
                    busStopInterval += 300.0;
                    busPassCount -= 15;
                }
            }

            //Train picks up to 1300 passengers waiting in queue
            /*if (trainStationInterval <= currentTime) {
                if (trainPassCount <= 1300) {
                    for (int i = 0; i < trainPassCount; i++) {
                        Job job = trainStationQueue.dequeue();
                        job.setTrainStationWaitTime(currentTime);
                        metroQueue.enqueue(job);
                        metroPassCount++;
                    }
                    trainStationInterval += 300.0;
                    trainPassCount = 0;
                } else {
                    for (int i = 0; i < 1300; i++) {
                        Job job = trainStationQueue.dequeue();
                        job.setTrainStationWaitTime(currentTime);
                        metroQueue.enqueue(job);
                        metroPassCount++;
                    }
                    trainStationInterval += 300.0;
                    trainPassCount -= 1300;
                }
            }*/

            if (trainStationInterval <= currentTime) {
                if (trainPassCount <= 1300) {
                    for (int i = 0; i < trainPassCount; i++) {
                        Job job = trainStationQueue.dequeue();
                        job.setTrainStationWaitTime(currentTime);
                        reachedDestination.add(job);
                    }
                    trainStationInterval += 300.0;
                    trainPassCount = 0;
                } else {
                    for (int i = 0; i < 1300; i++) {
                        metroPassCount += trainPassCount;
                        Job job = trainStationQueue.dequeue();
                        job.setTrainStationWaitTime(currentTime);
                        reachedDestination.add(job);
                    }
                    trainStationInterval += 300.0;
                    trainPassCount -= 1300;
                }
            }

            double nextEventTime = Math.min(busStopInterval, trainStationInterval);
            if (startTimesTracker < jobStartTimes.size()) {
                nextStart = jobStartTimes.get(startTimesTracker);
                nextEventTime = Math.min(nextEventTime, nextStart);
            }
            currentTime = nextEventTime;
        }

        return reachedDestination;

    }

}
