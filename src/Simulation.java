import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private double currentTime;
    private Queue<Commuter> trainStationQueue;
    private Queue<Commuter> busStopQueue;
    private Queue<Commuter> metroQueue;
    private ArrivalProcess arrivalProcess;
    private List<Commuter> reachedDestination;

    public Simulation() {
        this.currentTime = 0;
        this.trainStationQueue = new Queue<>();
        this.arrivalProcess = new ArrivalProcess();
        this.busStopQueue = new Queue<>();
        this.metroQueue = new Queue<>();
        this.reachedDestination = new ArrayList<>();
    }

    public List<Commuter> run(double sim_time) {
        double busStopInterval = 300.0/46.0;  //36 buses per stop, Average of 5 minutes across 46 stops
        double trainStationInterval = 2700.0/7.0; //4 trains per station, average of 45 minutes across 7 stations
        double metroInterval = 300.0; //5 minutes
        int busPassCount = 0;  //25 max
        int trainPassCount = 0; //1300 max
        int metroPassCount = 0; //2400 max
        int startTimesTracker = 0;
        ArrayList<Double> commuteStartTimes = arrivalProcess.generateStartTimes();
        Double nextStart = commuteStartTimes.get(startTimesTracker);

        while (currentTime < sim_time) {
            if (startTimesTracker < commuteStartTimes.size()) {
                if (nextStart <= currentTime) {
                    startTimesTracker++;
                    double eBikeRideTime = arrivalProcess.generateEbikeRideTime();
                    double busRideTime = arrivalProcess.generateBusRideTime();
                    double trainRideTime = arrivalProcess.generateTrainRideTime();
                    double metroRideTime = arrivalProcess.generateMetroRideTime();
                    Commuter commuter = new Commuter(nextStart, eBikeRideTime, busRideTime, trainRideTime, metroRideTime);
                    busStopQueue.enqueue(commuter);
                    busPassCount++;
                }
            }

            //Bus picks up to 15 passengers waiting in queue
            if (busStopInterval <= currentTime) {
                if (busPassCount <= 25) {
                    for (int i = 0; i < busPassCount; i++) {
                        Commuter commuter = busStopQueue.dequeue();
                        commuter.setBusStopWaitTime(currentTime);
                        trainStationQueue.enqueue(commuter);
                        trainPassCount++;
                    }
                    busStopInterval += 300.0/46.0;
                    busPassCount = 0;
                } else {
                    for (int i = 0; i < 25; i++) {
                        Commuter commuter = busStopQueue.dequeue();
                        commuter.setBusStopWaitTime(currentTime);
                        trainStationQueue.enqueue(commuter);
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
                        Commuter commuter = trainStationQueue.dequeue();
                        commuter.setTrainStationWaitTime(currentTime);
                        metroQueue.enqueue(commuter);
                        metroPassCount++;
                    }
                    trainStationInterval += 2700.0/7.0;
                    trainPassCount = 0;
                } else {
                    for (int i = 0; i < 1300; i++) {
                        Commuter commuter = trainStationQueue.dequeue();
                        commuter.setTrainStationWaitTime(currentTime);
                        metroQueue.enqueue(commuter);
                        metroPassCount++;
                    }
                    trainStationInterval += 2700.0/7.0;
                    trainPassCount -= 1300;
                }
            }

            if (metroInterval <= currentTime) {
                if (metroPassCount <= 2400) {
                    for (int i = 0; i < metroPassCount; i++) {
                        Commuter commuter = metroQueue.dequeue();
                        commuter.setMetroWaitTime(currentTime);
                        reachedDestination.add(commuter);
                    }
                    metroInterval += 300.0;
                    metroPassCount = 0;
                } else {
                    for (int i = 0; i < 2400; i++) {
                        Commuter commuter = trainStationQueue.dequeue();
                        commuter.setMetroWaitTime(currentTime);
                        reachedDestination.add(commuter);
                    }
                    trainStationInterval += 300.0;
                    trainPassCount -= 2400;
                }
            }

            double nextEventTime = Math.min(busStopInterval, Math.min(trainStationInterval, metroInterval));
            if (startTimesTracker < commuteStartTimes.size()) {
                nextStart = commuteStartTimes.get(startTimesTracker);
                nextEventTime = Math.min(nextEventTime, nextStart);
            }
            currentTime = nextEventTime;
        }

        return reachedDestination;

    }

}
