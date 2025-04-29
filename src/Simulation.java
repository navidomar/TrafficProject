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
        double busStopInterval = 300.0/46.0;  //36 buses per stop, Average of 5 minutes across 46 stops
        double trainStationInterval = 2700.0/7.0; //4 trains per station, average of 45 minutes across 7 stations
        double metroInterval = 300.0; //5 minutes
        int busPassCount = 0;  //25 max
        int trainPassCount = 0; //1300 max
        int metroPassCount = 0; //2400 max
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
                    double metroRideTime = arrivalProcess.generateMetroRideTime();
                    Job job = new Job(nextStart, eBikeRideTime, busRideTime, trainRideTime, metroRideTime);
                    busStopQueue.enqueue(job);
                    busPassCount++;
                }
            }

            //Bus picks up to 15 passengers waiting in queue
            if (busStopInterval <= currentTime) {
                if (busPassCount <= 25) {
                    for (int i = 0; i < busPassCount; i++) {
                        Job job = busStopQueue.dequeue();
                        job.setBusStopWaitTime(currentTime);
                        trainStationQueue.enqueue(job);
                        trainPassCount++;
                    }
                    busStopInterval += 300.0/46.0;
                    busPassCount = 0;
                } else {
                    for (int i = 0; i < 25; i++) {
                        Job job = busStopQueue.dequeue();
                        job.setBusStopWaitTime(currentTime);
                        trainStationQueue.enqueue(job);
                        trainPassCount++;
                    }
                    busStopInterval += 300.0/46.0;
                    busPassCount -= 25;
                }
            }

            //Train picks up to 1300 passengers waiting in queue
            if (trainStationInterval <= currentTime) {
                if (trainPassCount <= 1300) {
                    for (int i = 0; i < trainPassCount; i++) {
                        Job job = trainStationQueue.dequeue();
                        job.setTrainStationWaitTime(currentTime);
                        metroQueue.enqueue(job);
                        metroPassCount++;
                    }
                    trainStationInterval += 2700.0/7.0;
                    trainPassCount = 0;
                } else {
                    for (int i = 0; i < 1300; i++) {
                        Job job = trainStationQueue.dequeue();
                        job.setTrainStationWaitTime(currentTime);
                        metroQueue.enqueue(job);
                        metroPassCount++;
                    }
                    trainStationInterval += 2700.0/7.0;
                    trainPassCount -= 1300;
                }
            }

            if (metroInterval <= currentTime) {
                if (metroPassCount <= 2400) {
                    for (int i = 0; i < metroPassCount; i++) {
                        Job job = metroQueue.dequeue();
                        job.setMetroWaitTime(currentTime);
                        reachedDestination.add(job);
                    }
                    metroInterval += 300.0;
                    metroPassCount = 0;
                } else {
                    for (int i = 0; i < 2400; i++) {
                        Job job = trainStationQueue.dequeue();
                        job.setMetroWaitTime(currentTime);
                        reachedDestination.add(job);
                    }
                    trainStationInterval += 300.0;
                    trainPassCount -= 2400;
                }
            }

            double nextEventTime = Math.min(busStopInterval, Math.min(trainStationInterval, metroInterval));
            if (startTimesTracker < jobStartTimes.size()) {
                nextStart = jobStartTimes.get(startTimesTracker);
                nextEventTime = Math.min(nextEventTime, nextStart);
            }
            currentTime = nextEventTime;
        }

        return reachedDestination;

    }

}
