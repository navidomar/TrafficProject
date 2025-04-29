import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ArrivalProcess {
    private Random rand = new Random();
    private ArrayList<Double> startTimes = new ArrayList<>();

    //min = 0 sec (6:00 AM)
    //mode = 5400 sec (7:30 AM)
    //max = 10800 sec (9:00 AM)
    private RandomDistribution startTimeDistribution = new TriangularDistribution(rand, 0.0, 5400.0, 10800.0);

    private RandomDistribution ebikeRideDistribution = new NormalDistribution(600.0, 180.0); // 10 min, 3 min
    private RandomDistribution busRideDistribution = new NormalDistribution(720.0, 300.0); //12 min, 5 min
    private RandomDistribution trainRideDistribution = new NormalDistribution(900.0,180.0); //15 min, 3 min
    private RandomDistribution metroRideDistribution = new NormalDistribution(600.0, 180.0); //10 min, 3 min

    //Commuter start times
    public ArrayList<Double> generateStartTimes() {
        int numOfWorkers = 30000; //30,000 workers (estimated)
        for (int i = 0; i < numOfWorkers; i++) {
            startTimes.add(startTimeDistribution.run());
        }
        Collections.sort(startTimes);
        return startTimes;
    }

    //E-bike travel time to bus stop
    public double generateEbikeRideTime() {
        return ebikeRideDistribution.run();
    }

    //Bus travel time to train station
    public double generateBusRideTime() {
        return busRideDistribution.run();
    }

    //Train travel time to metro
    public double generateTrainRideTime() {
        return trainRideDistribution.run();
    }

    //Metro travel time to destination
    public double generateMetroRideTime() {
        return metroRideDistribution.run();
    }

}

